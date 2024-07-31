package com.pvpbox.player.service.repositories

import com.pvpbox.player.service.models.transaction.Transaction
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository : CrudRepository<Transaction, Long> {
    //fun findByGiverBankAccountAndReceiverBankAccount(giverBankAccountId: Long, receiverBankAccountId: Long): MutableIterable<Transaction>
    @Query("SELECT t FROM Transaction t WHERE t.giverBankAccountId = :giverBankAccountId AND t.receiverBankAccountId = :receiverBankAccountId")
    fun findByGiverBankAccountIdAndReceiverBankAccountId(@Param("giverBankAccountId") giverBankAccountId: Long, @Param("receiverBankAccountId") receiverBankAccountId: Long): MutableIterable<Transaction>
}