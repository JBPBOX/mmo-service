package com.pvpbox.player.service.DataPop

import com.pvpbox.player.service.models.Role
import com.pvpbox.player.service.models.player.Player
import com.pvpbox.player.service.models.rank.Rank
import com.pvpbox.player.service.models.rank.RankDTO
import com.pvpbox.player.service.services.PlayerService
import com.pvpbox.player.service.services.RankService
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.util.*

@Component
class DataPOP(
    private val playerService: PlayerService,
    private val passwordEncoder: PasswordEncoder,
    private val rankService: RankService
): CommandLineRunner {
    override fun run(vararg args: String?) {
        val user = Player(1, "user", UUID.fromString("e0c94d12-0b4d-4f1b-912c-bf0d308d6466"), passwordEncoder.encode("user"))
        val admin = Player(3, "admin", UUID.fromString("e0c94d12-0b4d-4f1b-912c-bf0d308d6466"), passwordEncoder.encode("admin"), Role.ADMIN)
        val rank = RankDTO("player", 0, 0, )

        playerService.savePlayer(user)
        playerService.savePlayer(admin)

        rankService.createRank(rank)
    }
}