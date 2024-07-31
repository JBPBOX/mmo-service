package com.pvpbox.player.service.repositories

import com.pvpbox.player.service.models.equipement.Equipement
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface EquipementRepository : CrudRepository<Equipement, Long> {
    fun findByProfileId(profileId: Long): MutableIterable<Equipement>
}