package com.pvpbox.player.service.repositories

import com.pvpbox.player.service.models.attribute.Attribute
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface AttributeRepository : CrudRepository<Attribute, Long> {
    fun findByLevel(level: Int): Optional<Attribute>
}