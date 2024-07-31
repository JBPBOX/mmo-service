package com.pvpbox.player.service.models.attribute

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class AttributeDTO(
    @NotBlank
    @Size(max = 100)
    var name: String,
    @NotBlank
    var level: Int
)
