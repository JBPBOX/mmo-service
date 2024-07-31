package com.pvpbox.player.service.controllers

import com.pvpbox.player.service.models.profiletorank.ProfileToAttribute
import com.pvpbox.player.service.models.profiletorank.ProfileToAttributeDTO
import com.pvpbox.player.service.models.profiletorank.ProfileToAttributeId
import com.pvpbox.player.service.services.ProfileToAttributeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.jvm.optionals.getOrNull

@RestController
@RequestMapping("/api/profilestoattributes")
class ProfileToAttributeController(
    private val profileToAttributeService: ProfileToAttributeService
) {
    @GetMapping
    fun getProfilesToAttributes() : ResponseEntity<MutableIterable<ProfileToAttribute>> {
        val profilesToAttrbutes = profileToAttributeService.getProfilesToAttributes()

        if (!profilesToAttrbutes.any()) return ResponseEntity(profilesToAttrbutes, HttpStatus.NO_CONTENT)
        return ResponseEntity(profilesToAttrbutes, HttpStatus.OK)
    }

    @GetMapping("/{profileId}/{attributeId}")
    fun getProfileToAttribute(@PathVariable profileId: Long, @PathVariable attributeId: Long) : ResponseEntity<ProfileToAttribute> {
        val profileToAttribute = profileToAttributeService.getProfileToAttribute(profileId, attributeId).getOrNull() ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        return ResponseEntity(profileToAttribute, HttpStatus.OK)
    }

    @PostMapping()
    fun createProfileToAttribute(@RequestBody profileToAttributeDTO: ProfileToAttributeDTO) : ResponseEntity<Any> {
        try {
            val profileToAttribute = profileToAttributeService.saveProfileToAttribute(profileToAttributeDTO)
            return ResponseEntity(profileToAttribute, HttpStatus.OK)
        } catch (e: Exception) {
            return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping
    fun updateProfileToAttribute(@RequestBody profileToAttributeDTO: ProfileToAttributeDTO) : ResponseEntity<Any> {
        try {
            val profileToAttribute = profileToAttributeService.saveProfileToAttribute(profileToAttributeDTO)
            return ResponseEntity(profileToAttribute, HttpStatus.OK)
        } catch (e: Exception) {
            return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }
}