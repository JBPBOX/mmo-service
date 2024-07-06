package com.pvpbox.player.service.models.player

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import lombok.Data
import java.util.UUID

@Data
data class SignUpRequest (
    @NotBlank
    @Size(max = 16)
    val username: String,
    @NotBlank
    val password: String,
    @NotBlank
    val uuid: UUID,
)