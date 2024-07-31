package com.pvpbox.player.service.models.transaction

import com.pvpbox.player.service.models.bankaccount.BankAccount
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import java.time.Instant

@Entity
@Table(name = "transaction")
data class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaction")
    var idTransaction: Long? = null,

    @NotBlank
    var amount: Double,

    @Column(name = "transaction_date")
    var transactionDate: Instant = Instant.now(),

    @Column(name = "giver_bank_account", nullable = false)
    var giverBankAccountId: Long,

    @Column(name = "receiver_bank_account", nullable = false)
    var receiverBankAccountId: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "giver_bank_account", insertable = false, updatable = false)
    val giverBankAccount: BankAccount? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_bank_account", insertable = false, updatable = false)
    val receiverBankAccount: BankAccount? = null
)

