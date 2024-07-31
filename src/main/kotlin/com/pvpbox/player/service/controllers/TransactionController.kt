package com.pvpbox.player.service.controllers

import com.pvpbox.player.service.models.transaction.Transaction
import com.pvpbox.player.service.models.transaction.TransactionDTO
import com.pvpbox.player.service.services.TransactionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kotlin.jvm.optionals.getOrNull

@RestController
@RequestMapping("/api/transations")
class TransactionController (
    private val transactionService: TransactionService
) {

    @GetMapping
    fun getBankAccounts(): ResponseEntity<MutableIterable<Transaction>> {
        val transactions: MutableIterable<Transaction> = transactionService.getTransactions()

        if (transactions.any()) return ResponseEntity(transactions, HttpStatus.OK)

        return ResponseEntity(transactions, HttpStatus.NO_CONTENT)
    }

    @GetMapping("/{id}")
    fun getBankAccount(@PathVariable id: Long) : ResponseEntity<Transaction> {
        val transaction: Transaction = transactionService.getTransation(id).getOrNull() ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        return ResponseEntity(transaction, HttpStatus.OK)
    }

    @GetMapping("/{giverId}/{receiverId}")
    fun getBankAccount(@PathVariable giverId: Long, @PathVariable receiverId: Long) : ResponseEntity<MutableIterable<Transaction>> {
        val transactions: MutableIterable<Transaction> = transactionService.getTransactions(giverId, receiverId)

        if (transactions.any()) return ResponseEntity(transactions, HttpStatus.OK)

        return ResponseEntity(transactions, HttpStatus.NO_CONTENT)
    }

    @PostMapping
    fun createTransaction(@RequestBody transactionDTO: TransactionDTO): ResponseEntity<Any> {
        try {

            val transaction = transactionService.createTransation(transactionDTO)
            return ResponseEntity(transaction, HttpStatus.OK)

        } catch (e: Exception) {
            return ResponseEntity(e, HttpStatus.BAD_REQUEST)
        }
    }
}