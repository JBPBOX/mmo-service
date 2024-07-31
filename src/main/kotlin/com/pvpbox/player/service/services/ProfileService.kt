package com.pvpbox.player.service.services

import com.pvpbox.player.service.models.profile.Profile
import com.pvpbox.player.service.models.profile.ProfileDTO
import com.pvpbox.player.service.repositories.PlayerRepository
import com.pvpbox.player.service.repositories.ProfileRepository
import org.springframework.stereotype.Service
import java.util.Optional
import kotlin.jvm.optionals.getOrElse
import kotlin.jvm.optionals.getOrNull
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Private

@Service
class ProfileService(
    private val profileRepository: ProfileRepository,
    private val playerService: PlayerService,
    private val rankService: RankService
) {
    fun getProfiles(): MutableIterable<Profile> {
        return profileRepository.findAll()
    }

    fun getProfile(id: Long) : Optional<Profile> {
        return profileRepository.findById(id)
    }

    fun createProfile(profileDTO: ProfileDTO) : Profile {
        val player = playerService.getPlayer(profileDTO.playerId).getOrNull() ?: throw RuntimeException("Player not found")
        val baseRank = rankService.getRank(1L).getOrNull() ?: throw RuntimeException("Something went wrong")

        val profile: Profile = Profile(
            profileName = profileDTO.profileName,
            player = player,
            rank = baseRank
            )

        return profileRepository.save(profile)
    }

    fun updateProfile(profile: Profile) : Profile {
        return profileRepository.save(profile)
    }

    fun deleteProfile(id: Long) {
        profileRepository.deleteById(id)
    }
}