package com.pvpbox.player.service.controllers

import com.pvpbox.player.service.models.rank.Rank
import com.pvpbox.player.service.models.rank.RankDTO
import com.pvpbox.player.service.services.RankService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kotlin.jvm.optionals.getOrNull

@RestController
@RequestMapping("/api/ranks")
class RankController(
    private val rankService: RankService
) {
    @GetMapping()
    fun getRanks(): ResponseEntity<MutableIterable<Rank>> {
        val ranks = rankService.getRanks()

        if (ranks.any()) return ResponseEntity(ranks, HttpStatus.OK)

        return ResponseEntity(ranks, HttpStatus.NO_CONTENT)
    }

    @GetMapping("/{id}")
    fun getRank(@PathVariable id: Long) : ResponseEntity<Rank> {
        val rank: Rank = rankService.getRank(id).getOrNull() ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        return ResponseEntity(rank, HttpStatus.OK)
    }

    @PostMapping
    fun createRank(@RequestBody rankDTO: RankDTO) : ResponseEntity<Rank> {
        try {
            val rank = rankService.createRank(rankDTO)
            return ResponseEntity(rank, HttpStatus.OK)
        } catch (e: Exception) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/{id}")
    fun updateAttribute(@RequestBody rankDTO: RankDTO, @PathVariable id: Long) : ResponseEntity<Any> {
        val gettedRank = rankService.getRank(id).getOrNull() ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        val rank = rankService.getRank(rankDTO.level)

        if (rank.isPresent && rank.get().rankId != gettedRank.rankId) return ResponseEntity("Rank already exist", HttpStatus.BAD_REQUEST)

        gettedRank.name = rankDTO.name
        gettedRank.level = rankDTO.level
        gettedRank.minimumPlayerXpLevel = rankDTO.minimumPlayerXpLevel


        val updatedRank = rankService.updateRank(gettedRank)
        return ResponseEntity(updatedRank, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteRank(@PathVariable id: Long) : ResponseEntity<Rank> {
        val rank = rankService.getRank(id).getOrNull() ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        rankService.deleteRank(id)

        return ResponseEntity(rank, HttpStatus.OK)
    }
}