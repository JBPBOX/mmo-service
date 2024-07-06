package com.pvpbox.player.service.security.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseCookie
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.web.util.WebUtils
import java.security.Key
import java.util.*

@Service
class JWTService {
    @Value("\${player.service.app.jwtSecret}")
    private val SECRET: String = "B4r5NDEUQzvA9LFqM2FcmLzV2z63Elg3MLj19C5YtSVPlKYKvge9Qh4jDC6tzRxG"

    @Value("\${player.service.app.expiration}")
    private val expiration: Int = 30

    @Value("\${player.service.app.cookieName}")
    private val jwtCookie: String = "MapAPIToken"

    fun getJwtFromCookies(request: HttpServletRequest?): String? {
        val cookie = WebUtils.getCookie(request!!, jwtCookie)
        return cookie?.value
    }

    fun generateCookie(userPrincipal: UserDetails): ResponseCookie {
        val jwt: String = generateToken(userPrincipal.username)
        val cookie: ResponseCookie = ResponseCookie.from(jwtCookie, jwt).path("/").sameSite("Lax").maxAge(24*60*60).httpOnly(true).build()
        return cookie
    }

    fun getCleanJwtCookie(): ResponseCookie {
        val cookie = ResponseCookie.from(jwtCookie, null.toString()).path("/").sameSite("Lax").build()
        return cookie
    }

    fun generateToken(username: String): String {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * expiration))
            .signWith(getSignKey(), SignatureAlgorithm.HS256)
            .compact()
    }

    fun validateToken(token: String): String {
        return Jwts.parserBuilder()
            .setSigningKey(getSignKey())
            .build()
            .parseClaimsJws(token)
            .body
            .subject
    }

    private fun getSignKey(): Key {
        val key = Decoders.BASE64.decode(SECRET)
        return Keys.hmacShaKeyFor(key)
    }
}