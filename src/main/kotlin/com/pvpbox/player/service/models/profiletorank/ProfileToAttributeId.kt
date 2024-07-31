package com.pvpbox.player.service.models.profiletorank

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class ProfileToAttributeId(
    @Column(name = "profile_id")
    val idProfile: Long,
    @Column(name = "attribute_id")
    val idAttribute: Long
): Serializable
