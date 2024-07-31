package com.pvpbox.player.service.services

import com.pvpbox.player.service.models.bankaccount.BankAccount
import com.pvpbox.player.service.repositories.BankAccountRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class BankAccountService(
    private val bankAccountRepository: BankAccountRepository,
    private val profileService: ProfileService
) {
    fun getBankAccounts(): MutableIterable<BankAccount> {
        return bankAccountRepository.findAll()
    }

    fun getBankAccount(id: Long): Optional<BankAccount> {
        return bankAccountRepository.findById(id)
    }

    fun createBankAccount(profileId: Long) : BankAccount {
        val profile = profileService.getProfile(profileId)
            .orElseThrow { RuntimeException("Profile not found") }

        val bankAccount = BankAccount(profile = profile)

        return bankAccountRepository.save(bankAccount)
    }

    fun updateBankAccount(bankAccount: BankAccount): BankAccount {
        return bankAccountRepository.save(bankAccount)
    }

    fun deleteBankAccount(id: Long) {
        bankAccountRepository.deleteById(id)
    }
}