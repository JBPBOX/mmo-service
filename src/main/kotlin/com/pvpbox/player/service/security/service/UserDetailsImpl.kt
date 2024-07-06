package com.pvpbox.player.service.security.service

import com.pvpbox.player.service.models.Role
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

data class UserDetailsImpl(
    var playerId: Long?,
    var innerUsername: String,
    var uuid: UUID,
    var innerPassword: String,
    var role: Role
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return Collections.singletonList(SimpleGrantedAuthority("ROLE_" + role.toString()));
    }

    override fun getPassword(): String {
        return innerPassword
    }

    override fun getUsername(): String {
        return innerUsername
    }
}
