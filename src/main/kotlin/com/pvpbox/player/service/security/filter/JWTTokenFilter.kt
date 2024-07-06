package com.pvpbox.player.service.security.filter

import com.pvpbox.player.service.security.service.JWTService
import com.pvpbox.player.service.security.service.UserDetailsServiceImpl
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JWTTokenFilter(private val jwtService: JWTService, private val userDetailsServiceImpl: UserDetailsServiceImpl) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = jwtService.getJwtFromCookies(request)

        if (token.isNullOrBlank()) {
            filterChain.doFilter(request, response)
            return
        }

        try {
            val username = jwtService.validateToken(token)
            if (username != null) {
                val userDetails: UserDetails = userDetailsServiceImpl.loadUserByUsername(username)
                val upToken = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                SecurityContextHolder.getContext().authentication = upToken
            }
        } catch (e: Exception) {
            logger.error("Failed to authenticate user", e.cause)
        }

        filterChain.doFilter(request, response)
    }
}