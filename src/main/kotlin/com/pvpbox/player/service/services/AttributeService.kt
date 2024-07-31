package com.pvpbox.player.service.services

import com.pvpbox.player.service.models.attribute.Attribute
import com.pvpbox.player.service.models.attribute.AttributeDTO
import com.pvpbox.player.service.repositories.AttributeRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class AttributeService(private val attributeRepository: AttributeRepository) {
    fun getAttributes() : MutableIterable<Attribute> {
        return attributeRepository.findAll()
    }

    fun getAttribute(id: Long) : Optional<Attribute> {
        return attributeRepository.findById(id)
    }

    fun getAttribute(level: Int): Optional<Attribute> {
        return attributeRepository.findByLevel(level)
    }

    fun createAttribute(attributeDTO: AttributeDTO) : Attribute {
        if (getAttribute(attributeDTO.level).isPresent) throw RuntimeException("Attribute already exists")

        val attribute = Attribute(
            name = attributeDTO.name,
            level = attributeDTO.level
        )

        return attributeRepository.save(attribute)
    }

    fun updateAttribute(attribute: Attribute): Attribute {
        return attributeRepository.save(attribute)
    }

    fun deleteAttribute(id: Long) {
        attributeRepository.deleteById(id)
    }
}