package com.pvpbox.player.service.models.attribute

import jakarta.persistence.*

@Entity
@Table(name = "attribute")
data class Attribute(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_attribute")
    var attributeId: Long? = null,
    var level: Int,
    var name: String
)
