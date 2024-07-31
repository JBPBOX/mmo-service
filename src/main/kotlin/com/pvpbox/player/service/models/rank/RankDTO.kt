package com.pvpbox.player.service.models.rank

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RankDTO(
    @NotBlank
    @Size(max = 100)
    var name: String,
    @NotBlank
    var level: Int,
    @NotBlank
    var minimumPlayerXpLevel: Int
)
