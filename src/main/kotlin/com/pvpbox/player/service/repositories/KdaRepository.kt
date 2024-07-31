package com.pvpbox.player.service.repositories

import com.pvpbox.player.service.models.kda.Kda
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface KdaRepository : CrudRepository<Kda, Long> {
}