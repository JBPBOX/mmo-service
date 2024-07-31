package com.pvpbox.player.service.models.profile

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UpdateProfileDTO (
    @NotBlank
    @Size(max = 100)
    var profileName: String,
    var playerId: Long,
    var level: Int,
    var progression: Int,
    var rankId: Int,
)