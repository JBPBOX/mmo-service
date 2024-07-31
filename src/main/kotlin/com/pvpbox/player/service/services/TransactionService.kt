package com.pvpbox.player.service.services

import com.pvpbox.player.service.models.transaction.Transaction
import com.pvpbox.player.service.models.transaction.TransactionDTO
import com.pvpbox.player.service.repositories.TransactionRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class TransactionService(
    private val transactionRepository: TransactionRepository,
    private val bankAccountService: BankAccountService
) {
    fun getTransactions(): MutableIterable<Transaction> {
        return transactionRepository.findAll()
    }

    fun getTransactions(giverId: Long, receiverId: Long): MutableIterable<Transaction> {
        return transactionRepository.findByGiverBankAccountIdAndReceiverBankAccountId(giverId, receiverId)
    }

    fun getTransation(id: Long) : Optional<Transaction> {
        return transactionRepository.findById(id)
    }

    fun createTransation(transactionDTO: TransactionDTO): Transaction {
        val giverBankAccount = bankAccountService.getBankAccount(transactionDTO.giverId)
            .orElseThrow { RuntimeException("Bank account not found") }

        val receiverBankAccount = bankAccountService.getBankAccount(transactionDTO.receiverId)
            .orElseThrow { RuntimeException("Bank account not found") }

        if (giverBankAccount.amount < transactionDTO.amount) throw RuntimeException("Bank account not found")

        giverBankAccount.amount -= transactionDTO.amount
        receiverBankAccount.amount += transactionDTO.amount

        bankAccountService.updateBankAccount(giverBankAccount)
        bankAccountService.updateBankAccount(receiverBankAccount)

        val transaction = Transaction(
            amount = transactionDTO.amount,
            giverBankAccount =  giverBankAccount,
            receiverBankAccount = receiverBankAccount,
            giverBankAccountId = transactionDTO.giverId,
            receiverBankAccountId = transactionDTO.receiverId
        )

        return transactionRepository.save(transaction)
    }
}