package com.pvpbox.player.service.security.service

import com.pvpbox.player.service.models.player.Player
import com.pvpbox.player.service.repositories.PlayerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl() : UserDetailsService {
    @Autowired
    lateinit var playerRepository: PlayerRepository

    override fun loadUserByUsername(username: String): UserDetailsImpl {
        val player: Player = playerRepository.findPlayerByUsername(username)
            .orElseThrow { UsernameNotFoundException("User Not Found with username: $username") }

        return UserDetailsImpl(player.playerId, player.username, player.uuid, player.password, player.role)
    }
}