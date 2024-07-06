package com.pvpbox.player.service.models.player

import com.pvpbox.player.service.models.Role
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.util.*

@Entity
@Table(name = "player")
data class Player(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_player")
    var playerId: Long? = null,
    @NotBlank
    @Size(max = 16)
    var username: String,
    var uuid: UUID,
    @NotBlank
    var password: String,
    var role: Role = Role.USER
) {
    constructor(username: String, uuid: UUID, password: String) : this (
        playerId = null,
        username = username,
        uuid = uuid,
        password = password,
        role = Role.USER
    )
}