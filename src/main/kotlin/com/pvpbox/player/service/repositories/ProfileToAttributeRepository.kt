package com.pvpbox.player.service.repositories

import com.pvpbox.player.service.models.profiletorank.ProfileToAttribute
import com.pvpbox.player.service.models.profiletorank.ProfileToAttributeId
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProfileToAttributeRepository : CrudRepository<ProfileToAttribute, ProfileToAttributeId> {
}