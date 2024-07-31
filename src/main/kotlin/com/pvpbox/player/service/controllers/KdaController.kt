package com.pvpbox.player.service.controllers

import com.pvpbox.player.service.models.kda.Kda
import com.pvpbox.player.service.models.kda.KdaDTO
import com.pvpbox.player.service.models.kda.UpdateKdaDTO
import com.pvpbox.player.service.services.KdaService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kotlin.jvm.optionals.getOrNull

@RestController
@RequestMapping("/api/kdas")
class KdaController(
    private val kdaService: KdaService
) {
    @GetMapping()
    fun getKdas(): ResponseEntity<MutableIterable<Kda>> {
        val kdas = kdaService.getKdas()

        if (kdas.any()) return ResponseEntity(kdas, HttpStatus.OK)

        return ResponseEntity(kdas, HttpStatus.NO_CONTENT)
    }

    @GetMapping("/{id}")
    fun getKda(@PathVariable id: Long) : ResponseEntity<Kda> {
        val kda: Kda = kdaService.getKda(id).getOrNull() ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        return ResponseEntity(kda, HttpStatus.OK)
    }

    @PostMapping
    fun createKda(@RequestBody kdaDTO: KdaDTO) : ResponseEntity<Kda> {
        try {
            val kda = kdaService.createKda(kdaDTO)
            return ResponseEntity(kda, HttpStatus.OK)
        } catch (e: Exception) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/{id}")
    fun updatekda(@RequestBody updateKdaDTO: UpdateKdaDTO, @PathVariable id: Long) : ResponseEntity<Kda> {
        val gettedKda = kdaService.getKda(id).getOrNull() ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        gettedKda.kill = updateKdaDTO.kill
        gettedKda.death = updateKdaDTO.death
        gettedKda.assist = updateKdaDTO.assist


        val updatedKda = kdaService.updateKda(gettedKda)
        return ResponseEntity(updatedKda, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteKda(@PathVariable id: Long) : ResponseEntity<Kda> {
        val kda = kdaService.getKda(id).getOrNull() ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        kdaService.deleteKda(id)

        return ResponseEntity(kda, HttpStatus.OK)
    }
}