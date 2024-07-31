package com.pvpbox.player.service.services

import com.pvpbox.player.service.models.rank.Rank
import com.pvpbox.player.service.models.rank.RankDTO
import com.pvpbox.player.service.repositories.RankRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class RankService (
    private val rankRepository: RankRepository
) {
    fun getRanks() : MutableIterable<Rank> {
        return rankRepository.findAll()
    }

    fun getRank(id: Long) : Optional<Rank> {
        return rankRepository.findById(id)
    }

    fun getRank(level: Int) : Optional<Rank> {
        return rankRepository.findByLevel(level)
    }

    fun createRank(rankDTO: RankDTO) : Rank {
        val rank = Rank(
            name = rankDTO.name,
            level = rankDTO.level,
            minimumPlayerXpLevel = rankDTO.minimumPlayerXpLevel
        )

        return rankRepository.save(rank)
    }

    fun updateRank(rank: Rank) : Rank {
        return rankRepository.save(rank)
    }

    fun deleteRank(id: Long) {
        return rankRepository.deleteById(id)
    }
}