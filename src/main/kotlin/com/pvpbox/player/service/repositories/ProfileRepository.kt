package com.pvpbox.player.service.repositories

import com.pvpbox.player.service.models.profile.Profile
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProfileRepository : CrudRepository<Profile, Long> {
}