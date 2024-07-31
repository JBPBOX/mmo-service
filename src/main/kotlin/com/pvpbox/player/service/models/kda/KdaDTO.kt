package com.pvpbox.player.service.models.kda

import jakarta.validation.constraints.NotBlank

data class KdaDTO(
    @NotBlank
    var profileId: Long,
    var kill: Int = 0,
    var death: Int = 0,
    var assist: Int = 0
)
