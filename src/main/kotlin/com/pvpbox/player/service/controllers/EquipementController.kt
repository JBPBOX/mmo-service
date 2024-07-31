package com.pvpbox.player.service.controllers

import com.pvpbox.player.service.models.equipement.Equipement
import com.pvpbox.player.service.models.equipement.EquipementDTO
import com.pvpbox.player.service.services.EquipementService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kotlin.jvm.optionals.getOrNull

@RestController
@RequestMapping("/api/equipements")
class EquipementController(
    private val equipementService: EquipementService
) {
    @GetMapping
    fun getEquipements(): ResponseEntity<MutableIterable<Equipement>> {
        val equipements: MutableIterable<Equipement> = equipementService.getEquipements()

        if (equipements.any()) return ResponseEntity(equipements, HttpStatus.OK)

        return ResponseEntity(equipements, HttpStatus.NO_CONTENT)
    }

    @GetMapping("/{id}")
    fun getEquipement(@PathVariable id: Long) : ResponseEntity<Equipement> {
        val equipement: Equipement = equipementService.getEquipement(id).getOrNull() ?: return ResponseEntity(
            HttpStatus.NOT_FOUND)
        return ResponseEntity(equipement, HttpStatus.OK)
    }

    @GetMapping("/profile/{id}")
    fun getProfileEquipements(@PathVariable id: Long) : ResponseEntity<MutableIterable<Equipement>> {
        val equipements = equipementService.getEquipements(id)

        if (equipements.any()) return ResponseEntity(equipements, HttpStatus.OK)

        return ResponseEntity(equipements, HttpStatus.OK)
    }

    @PostMapping("/{id}")
    fun createBankAccount(@PathVariable equipementDTO: EquipementDTO) : ResponseEntity<Equipement> {
        try {
            val equipement = equipementService.createEquipement(equipementDTO)
            return ResponseEntity(equipement, HttpStatus.OK)
        } catch (e: Exception) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/profile/{id}")
    fun updateEquipement(@RequestBody equipementDTO: EquipementDTO, @PathVariable id: Long) : ResponseEntity<Any> {
        val gettedEquipement = equipementService.getEquipement(id).getOrNull() ?: return ResponseEntity("Equipement not found", HttpStatus.NOT_FOUND)

        gettedEquipement.equipementSlot = equipementDTO.equipementSlot
        gettedEquipement.item = equipementDTO.item

        val updatedEquipement = equipementService.updateEquipement(gettedEquipement)

        return ResponseEntity(updatedEquipement, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteEquipement(@PathVariable id: Long) : ResponseEntity<Equipement> {
        val equipement = equipementService.getEquipement(id).getOrNull() ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        equipementService.deleteEquipement(id)

        return ResponseEntity(equipement, HttpStatus.OK)
    }
}