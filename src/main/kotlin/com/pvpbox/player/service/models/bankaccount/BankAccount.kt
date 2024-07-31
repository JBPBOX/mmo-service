package com.pvpbox.player.service.models.bankaccount

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.pvpbox.player.service.models.profile.Profile
import jakarta.persistence.*

@Entity
@Table(name = "bank_account")
data class BankAccount(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bank_account")
    var bankAccountId: Long? = null,
    var amount: Double = 0.0,
    @Column(name = "is_active")
    var isActive: Boolean = true,

    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false)
    @JsonIgnoreProperties("bankAccounts")
    var profile: Profile
)
