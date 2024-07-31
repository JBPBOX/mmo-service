package com.pvpbox.player.service.models.profile

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class ProfileDTO (
    @NotBlank
    @Size(max = 100)
    var profileName: String,
    var playerId: Long
)