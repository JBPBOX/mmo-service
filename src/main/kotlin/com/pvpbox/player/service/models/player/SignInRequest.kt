package com.pvpbox.player.service.models.player

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class SignInRequest (
    @NotBlank
    @Size(max = 16)
    val username: String,
    @NotBlank
    val password: String
)