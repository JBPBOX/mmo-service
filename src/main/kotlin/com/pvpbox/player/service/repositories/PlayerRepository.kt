package com.pvpbox.player.service.repositories

import com.pvpbox.player.service.models.player.Player
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface PlayerRepository : CrudRepository<Player, Long> {
    fun findPlayerByUsername(username: String): Optional<Player>
}