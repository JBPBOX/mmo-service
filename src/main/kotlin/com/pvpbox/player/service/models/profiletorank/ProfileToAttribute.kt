package com.pvpbox.player.service.models.profiletorank

import com.pvpbox.player.service.models.attribute.Attribute
import com.pvpbox.player.service.models.profile.Profile
import jakarta.persistence.*

@Entity
@Table(name = "profile_to_attribute")
data class ProfileToAttribute(
    @EmbeddedId
    val profileToAttributeId: ProfileToAttributeId,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable=false, updatable=false)
    var profile: Profile,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attribute_id", insertable=false, updatable=false)
    var attribute: Attribute,

    var level: Int
)
