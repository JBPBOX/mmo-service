package com.pvpbox.player.service.services

import com.pvpbox.player.service.models.equipement.Equipement
import com.pvpbox.player.service.models.equipement.EquipementDTO
import com.pvpbox.player.service.repositories.EquipementRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class EquipementService(
    private val equipementRepository: EquipementRepository,
    private val profileService: ProfileService
) {
    fun getEquipements() : MutableIterable<Equipement> {
        return equipementRepository.findAll()
    }

    fun getEquipement(id: Long) : Optional<Equipement> {
        return equipementRepository.findById(id)
    }

    fun getEquipements(profileId: Long) : MutableIterable<Equipement> {
        return equipementRepository.findByProfileId(profileId)
    }

    fun createEquipement(equipementDTO: EquipementDTO) : Equipement {
        val profile = profileService.getProfile(equipementDTO.profileId)
            .orElseThrow { RuntimeException("Profile not found") }

        val equipement = Equipement(
            profile = profile,
            profileId = equipementDTO.profileId,
            equipementSlot = equipementDTO.equipementSlot,
            item = equipementDTO.item
            )

        return equipementRepository.save(equipement)
    }

    fun updateEquipement(equipement: Equipement) : Equipement {
        return equipementRepository.save(equipement)
    }

    fun deleteEquipement(id: Long) {
        equipementRepository.deleteById(id)
    }
}