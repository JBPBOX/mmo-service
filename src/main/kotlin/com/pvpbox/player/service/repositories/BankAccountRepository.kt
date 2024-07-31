package com.pvpbox.player.service.repositories

import com.pvpbox.player.service.models.bankaccount.BankAccount
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BankAccountRepository : CrudRepository<BankAccount, Long> {
}