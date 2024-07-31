package com.pvpbox.player.service.models.equipement

import com.pvpbox.player.service.models.profile.Profile
import jakarta.persistence.*

@Entity
@Table
data class Equipement(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Equipement")
    var equipementId: Long? = null,
    @Column(name = "equipement_slot")
    var equipementSlot: Int,
    var item: String,
    @Column(name = "profile_id", insertable = false, updatable = false)
    var profileId: Long,

    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false)
    var profile: Profile
)
