package com.pvpbox.player.service.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/test")
class TestController {
    @GetMapping("/permitall")
    fun permitall(): ResponseEntity<String> {
        return ResponseEntity("Message de réussité", HttpStatus.OK)
    }

    @GetMapping("/admin")
    fun admin(): ResponseEntity<String> {
        return ResponseEntity("Message de réussité: Admin", HttpStatus.OK)
    }

    @GetMapping("/mod")
    fun mod(): ResponseEntity<String> {
        return ResponseEntity("Message de réussité: Mod", HttpStatus.OK)
    }
}