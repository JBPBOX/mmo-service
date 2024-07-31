package com.pvpbox.player.service.models.kda

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.pvpbox.player.service.models.profile.Profile
import jakarta.persistence.*

@Entity
@Table(name = "kda")
data class Kda (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_kda")
    var kdaId: Long? = null,
    var kill: Int = 0,
    var death: Int = 0,
    var assist: Int = 0,

    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false)
    @JsonIgnoreProperties("kdas")
    var profile: Profile
)