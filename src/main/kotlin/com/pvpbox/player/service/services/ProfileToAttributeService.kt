package com.pvpbox.player.service.services

import com.pvpbox.player.service.models.profiletorank.ProfileToAttribute
import com.pvpbox.player.service.models.profiletorank.ProfileToAttributeDTO
import com.pvpbox.player.service.models.profiletorank.ProfileToAttributeId
import com.pvpbox.player.service.repositories.ProfileToAttributeRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class ProfileToAttributeService(
    private val profileToAttributeRepository: ProfileToAttributeRepository,
    private val profileService: ProfileService,
    private val attributeService: AttributeService
) {
    fun getProfilesToAttributes() : MutableIterable<ProfileToAttribute> {
        return profileToAttributeRepository.findAll()
    }

    fun getProfileToAttribute(profileId: Long, attributeId: Long) : Optional<ProfileToAttribute> {
        val profileToAttributeId = ProfileToAttributeId(
            profileId,
            attributeId
        )

        return profileToAttributeRepository.findById(profileToAttributeId)
    }

    fun saveProfileToAttribute(profileToAttributeDTO: ProfileToAttributeDTO) : ProfileToAttribute {
        val profile = profileService.getProfile(profileToAttributeDTO.profileId)
            .orElseThrow { RuntimeException("Profile not found") }

        val attribute = attributeService.getAttribute(profileToAttributeDTO.attributeId)
            .orElseThrow { RuntimeException("Profile not found") }

        val profileToAttributeId = ProfileToAttributeId(profileToAttributeDTO.profileId, profileToAttributeDTO.attributeId)

        val profileToAttribute = ProfileToAttribute(
            profileToAttributeId = profileToAttributeId,
            profile = profile,
            attribute = attribute,
            level = profileToAttributeDTO.level
        )

        return profileToAttributeRepository.save(profileToAttribute)
    }

    fun deleteProfileToAttribute(profileId: Long, attributeId: Long) {
        val profileToAttributeId = ProfileToAttributeId(profileId, attributeId)

        profileToAttributeRepository.deleteById(profileToAttributeId)
    }
}