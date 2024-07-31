package com.pvpbox.player.service.models.profile

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.pvpbox.player.service.models.bankaccount.BankAccount
import com.pvpbox.player.service.models.kda.Kda
import com.pvpbox.player.service.models.player.Player
import com.pvpbox.player.service.models.rank.Rank
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.Instant

@Entity
@Table(name = "profile")
data class Profile(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_profile")
    var profileId: Long? = null,

    @Column(name = "profile_name")
    @NotBlank
    @Size(max = 100)
    var profileName: String = "",

    @Column(name = "creation_date")
    var creationDate: Instant = Instant.now(),

    var level: Int = 0,
    var progression: Int = 0,

    @Column(name = "is_active")
    var isActive: Boolean = true,

    @ManyToOne
    @JoinColumn(name = "rank_id")
    var rank: Rank? = null,

    @ManyToOne
    @JoinColumn(name = "player_id")
    @JsonIgnoreProperties("profiles")
    var player: Player? = null,

    @OneToMany(mappedBy = "profile", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonIgnoreProperties("profile")
    var bankAccounts: List<BankAccount> = mutableListOf(),

    @OneToMany(mappedBy = "profile", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonIgnoreProperties("profile")
    var kdas: List<Kda> = mutableListOf()
)
