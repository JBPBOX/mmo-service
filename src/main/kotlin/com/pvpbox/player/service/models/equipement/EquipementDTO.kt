package com.pvpbox.player.service.models.equipement

import com.pvpbox.player.service.models.profile.Profile

data class EquipementDTO(
    var profileId: Long,
    var equipementSlot: Int,
    var item: String
)
