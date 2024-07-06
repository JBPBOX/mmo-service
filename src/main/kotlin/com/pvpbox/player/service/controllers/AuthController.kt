package com.pvpbox.player.service.controllers

import com.pvpbox.player.service.models.player.Player
import com.pvpbox.player.service.models.player.SignInRequest
import com.pvpbox.player.service.models.player.SignUpRequest
import com.pvpbox.player.service.security.service.JWTService
import com.pvpbox.player.service.services.PlayerService
import jakarta.validation.Valid
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val playerService: PlayerService,
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JWTService,
    private val encoder: PasswordEncoder
) {
    @PostMapping("/signup")
    fun signUp(@Valid @RequestBody signUpRequest: SignUpRequest) : ResponseEntity<Any> {
        if (playerService.getPlayer(signUpRequest.username).isPresent) return ResponseEntity("User already exists", HttpStatus.BAD_REQUEST)

        val player: Player = Player(signUpRequest.username, signUpRequest.uuid, encoder.encode(signUpRequest.password))

        val createdPlayer = playerService.savePlayer(player)

        return ResponseEntity(createdPlayer, HttpStatus.OK)
    }

    @PostMapping("/signin")
    fun signIn(@Valid @RequestBody signInRequest: SignInRequest) : ResponseEntity<Any> {
        println("Test")
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(signInRequest.username, signInRequest.password)
        )
        println("Test2")
        if (!authentication.isAuthenticated) return ResponseEntity("Somethings went wrong", HttpStatus.INTERNAL_SERVER_ERROR)
        println("Test3")
        val userDetails: UserDetails = authentication.principal as UserDetails
        println("Test4")
        val jwtCookie: ResponseCookie = jwtService.generateCookie(userDetails)
        println("Test5")
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
            .body(userDetails);
    }

    @PostMapping("/signout")
    fun signOut(): ResponseEntity<Any> {
        val cookie: ResponseCookie = jwtService.getCleanJwtCookie()
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body<Any>("You've been signed out!")
    }
}