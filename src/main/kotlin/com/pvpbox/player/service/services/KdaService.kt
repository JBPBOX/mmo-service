package com.pvpbox.player.service.services

import com.pvpbox.player.service.models.kda.Kda
import com.pvpbox.player.service.models.kda.KdaDTO
import com.pvpbox.player.service.repositories.KdaRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class KdaService (
    private val kdaRepository: KdaRepository,
    private val profileService: ProfileService
) {
    fun getKdas(): MutableIterable<Kda> {
        return kdaRepository.findAll()
    }

    fun getKda(id: Long): Optional<Kda> {
        return kdaRepository.findById(id)
    }

    fun createKda(kdaDTO: KdaDTO): Kda {
        val profile = profileService.getProfile(kdaDTO.profileId)
            .orElseThrow { RuntimeException("Profile not found") }

        val kda = Kda(
            kill = kdaDTO.kill,
            death = kdaDTO.death,
            assist = kdaDTO.assist,
            profile = profile
        )

        return kdaRepository.save(kda)
    }

    fun updateKda(kda: Kda) : Kda {
        return kdaRepository.save(kda)
    }

    fun deleteKda(id: Long) {
        kdaRepository.deleteById(id)
    }
}