package com.pvpbox.player.service.controllers

import com.pvpbox.player.service.models.profile.Profile
import com.pvpbox.player.service.models.profile.ProfileDTO
import com.pvpbox.player.service.models.profile.UpdateProfileDTO
import com.pvpbox.player.service.services.PlayerService
import com.pvpbox.player.service.services.ProfileService
import com.pvpbox.player.service.services.RankService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.jvm.optionals.getOrNull

@RestController
@RequestMapping("/api/profiles")
class ProfileController(
    private val profileService: ProfileService,
    private val playerService: PlayerService,
    private val rankService: RankService
) {

    @GetMapping
    fun getProfiles(): ResponseEntity<MutableIterable<Profile>> {
        val profiles: MutableIterable<Profile> = profileService.getProfiles()

        if (profiles.any()) return ResponseEntity(profiles, HttpStatus.OK)

        return ResponseEntity(profiles, HttpStatus.NO_CONTENT)
    }

    @GetMapping("/{id}")
    fun getProfile(@PathVariable id: Long) : ResponseEntity<Profile> {
        val profile: Profile = profileService.getProfile(id).getOrNull() ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        return ResponseEntity(profile, HttpStatus.OK)
    }

    @PostMapping
    fun createProfile(@RequestBody profileDTO: ProfileDTO) : ResponseEntity<Any> {
        try {
            val profile = profileService.createProfile(profileDTO)
            return ResponseEntity(profile, HttpStatus.OK)
        } catch (e: Exception) {
            return ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/{id}")
    fun updateProfile(@RequestBody updateProfileDTO: UpdateProfileDTO, @PathVariable id: Long) : ResponseEntity<Profile> {
        val gettedProfile = profileService.getProfile(id).getOrNull() ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        val authentication = SecurityContextHolder.getContext().authentication
        val isAdmin = authentication.authorities.any { it.authority == "ROLE_ADMIN" }

        gettedProfile.profileName = updateProfileDTO.profileName

        if (isAdmin) {
            val player = playerService.getPlayer(updateProfileDTO.playerId).getOrNull() ?: return ResponseEntity(HttpStatus.NOT_FOUND)
            val rank = rankService.getRank(updateProfileDTO.rankId).getOrNull() ?: return ResponseEntity(HttpStatus.NOT_FOUND)
            gettedProfile.player = player
            gettedProfile.rank = rank
            gettedProfile.level = updateProfileDTO.level
            gettedProfile.progression = updateProfileDTO.progression
        }

        val updatedProfile = profileService.updateProfile(gettedProfile)
        return ResponseEntity(updatedProfile, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteProfile(@PathVariable id: Long) : ResponseEntity<Profile> {
        val profile = profileService.getProfile(id).getOrNull() ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        profileService.deleteProfile(id)

        return ResponseEntity(profile, HttpStatus.OK)
    }
}