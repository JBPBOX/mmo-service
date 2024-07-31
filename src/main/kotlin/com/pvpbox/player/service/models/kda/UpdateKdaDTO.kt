package com.pvpbox.player.service.models.kda

import jakarta.validation.constraints.NotBlank

data class UpdateKdaDTO(
    @NotBlank
    var kill: Int = 0,
    @NotBlank
    var death: Int = 0,
    @NotBlank
    var assist: Int = 0
)
