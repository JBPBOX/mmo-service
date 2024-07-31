package com.pvpbox.player.service.models.profiletorank

import jakarta.validation.constraints.NotBlank

data class ProfileToAttributeDTO(
    @NotBlank
    var profileId: Long,
    @NotBlank
    var attributeId: Long,
    @NotBlank
    var level: Int
)
