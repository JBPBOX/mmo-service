package com.pvpbox.player.service.controllers

import com.pvpbox.player.service.models.attribute.Attribute
import com.pvpbox.player.service.models.attribute.AttributeDTO
import com.pvpbox.player.service.services.AttributeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kotlin.jvm.optionals.getOrNull

@RestController
@RequestMapping("/api/attributes")
class AttributeController(
    private val attributeService: AttributeService
) {
    @GetMapping()
    fun getAttributes(): ResponseEntity<MutableIterable<Attribute>> {
        val attributes = attributeService.getAttributes()

        if (attributes.any()) return ResponseEntity(attributes, HttpStatus.OK)

        return ResponseEntity(attributes, HttpStatus.NO_CONTENT)
    }

    @GetMapping("/{id}")
    fun getAttribute(@PathVariable id: Long) : ResponseEntity<Attribute> {
        val attribute: Attribute = attributeService.getAttribute(id).getOrNull() ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        return ResponseEntity(attribute, HttpStatus.OK)
    }

    @PostMapping
    fun createAttribute(@RequestBody attributeDTO: AttributeDTO) : ResponseEntity<Attribute> {
        try {
            val attribute = attributeService.createAttribute(attributeDTO)
            return ResponseEntity(attribute, HttpStatus.OK)
        } catch (e: Exception) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/{id}")
    fun updateAttribute(@RequestBody attributeDTO: AttributeDTO, @PathVariable id: Long) : ResponseEntity<Any> {
        val gettedAttribute = attributeService.getAttribute(id).getOrNull() ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        val attribute = attributeService.getAttribute(attributeDTO.level)

        if (attribute.isPresent && attribute.get().attributeId != gettedAttribute.attributeId) return ResponseEntity("Attribute already exist", HttpStatus.BAD_REQUEST)

        gettedAttribute.name = attributeDTO.name
        gettedAttribute.level = attributeDTO.level


        val updatedAttribute = attributeService.updateAttribute(gettedAttribute)
        return ResponseEntity(updatedAttribute, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteAttribute(@PathVariable id: Long) : ResponseEntity<Attribute> {
        val attribute = attributeService.getAttribute(id).getOrNull() ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        attributeService.deleteAttribute(id)

        return ResponseEntity(attribute, HttpStatus.OK)
    }
}