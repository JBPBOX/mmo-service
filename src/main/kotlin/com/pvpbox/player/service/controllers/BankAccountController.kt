package com.pvpbox.player.service.controllers

import com.pvpbox.player.service.models.bankaccount.BankAccount
import com.pvpbox.player.service.services.BankAccountService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kotlin.jvm.optionals.getOrNull

@RestController
@RequestMapping("/api/bankaccount")
class BankAccountController (private val bankAccountService: BankAccountService) {
    @GetMapping
    fun getBankAccounts(): ResponseEntity<MutableIterable<BankAccount>> {
        val bankAccounts: MutableIterable<BankAccount> = bankAccountService.getBankAccounts()

        if (bankAccounts.any()) return ResponseEntity(bankAccounts, HttpStatus.OK)

        return ResponseEntity(bankAccounts, HttpStatus.NO_CONTENT)
    }

    @GetMapping("/{id}")
    fun getBankAccount(@PathVariable id: Long) : ResponseEntity<BankAccount> {
        val bankAccount: BankAccount = bankAccountService.getBankAccount(id).getOrNull() ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        return ResponseEntity(bankAccount, HttpStatus.OK)
    }

    @PostMapping("/{id}")
    fun createBankAccount(@PathVariable id: Long) : ResponseEntity<BankAccount> {
        try {
            val bankAccount = bankAccountService.createBankAccount(id)
            return ResponseEntity(bankAccount, HttpStatus.OK)
        } catch (e: Exception) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/add/{id}")
    fun addMoney(@RequestBody amount: Double, @PathVariable id: Long) : ResponseEntity<Any> {
        val gettedBankAccount = bankAccountService.getBankAccount(id).getOrNull() ?: return ResponseEntity("Bank account not found", HttpStatus.NOT_FOUND)

        if (amount < 0.1) return ResponseEntity("A minimum of 0.1 is required", HttpStatus.BAD_REQUEST)

        gettedBankAccount.amount += amount

        val updatedBankAccount = bankAccountService.updateBankAccount(gettedBankAccount)

        return ResponseEntity("Your sold is now ${updatedBankAccount.amount} coins", HttpStatus.OK)
    }

    @PutMapping("/remove/{id}")
    fun removeMoney(@RequestBody amount: Double, @PathVariable id: Long) : ResponseEntity<Any> {
        val gettedBankAccount = bankAccountService.getBankAccount(id).getOrNull() ?: return ResponseEntity("Bank account not found", HttpStatus.NOT_FOUND)

        if (amount < 0.1) return ResponseEntity("A minimum of 0.1 is required", HttpStatus.BAD_REQUEST)
        if (gettedBankAccount.amount < amount) return ResponseEntity("insufficient sold", HttpStatus.BAD_REQUEST)

        gettedBankAccount.amount -= amount

        val updatedBankAccount = bankAccountService.updateBankAccount(gettedBankAccount)

        return ResponseEntity("Your sold is now ${updatedBankAccount.amount} coins", HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteBankAccount(@PathVariable id: Long) : ResponseEntity<BankAccount> {
        val bankAccount = bankAccountService.getBankAccount(id).getOrNull() ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        bankAccountService.deleteBankAccount(id)

        return ResponseEntity(bankAccount, HttpStatus.OK)
    }
}