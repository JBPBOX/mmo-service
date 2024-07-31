package com.pvpbox.player.service.repositories

import com.pvpbox.player.service.models.rank.Rank
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RankRepository : CrudRepository<Rank, Long> {
    fun findByLevel(level: Int): Optional<Rank>
}