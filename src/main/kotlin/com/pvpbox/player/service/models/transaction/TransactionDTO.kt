package com.pvpbox.player.service.models.transaction

import jakarta.validation.constraints.NotBlank

data class TransactionDTO (
    @NotBlank
    var amount: Double,
    @NotBlank
    var giverId: Long,
    @NotBlank
    var receiverId: Long
)