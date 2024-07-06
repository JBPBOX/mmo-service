package com.pvpbox.player.service.services

import com.pvpbox.player.service.models.player.Player
import com.pvpbox.player.service.repositories.PlayerRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class PlayerService(private val playerRepository: PlayerRepository) {

    fun getPlayers(): MutableIterable<Player> {
        return playerRepository.findAll()
    }

    fun getPlayer(playerId: Long): Optional<Player> {
        return playerRepository.findById(playerId)
    }

    fun getPlayer(username: String): Optional<Player> {
        return playerRepository.findPlayerByUsername(username)
    }

    fun savePlayer(player: Player): Player {
        return playerRepository.save(player)
    }

    fun deletePlayer(playerId: Long) {
        playerRepository.deleteById(playerId)
    }
}