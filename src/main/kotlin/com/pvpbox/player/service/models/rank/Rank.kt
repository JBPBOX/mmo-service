package com.pvpbox.player.service.models.rank

import jakarta.persistence.*

@Entity
@Table(name = "rank")
data class Rank (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rank")
    var rankId: Long? = null,
    var level: Int,
    var name: String,
    @Column(name = "minimum_player_xp_level")
    var minimumPlayerXpLevel: Int
)