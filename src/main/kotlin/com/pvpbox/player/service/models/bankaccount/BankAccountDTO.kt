package com.pvpbox.player.service.models.bankaccount

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class BankAccountDTO(
    @NotBlank
    var profileId: Long
)
