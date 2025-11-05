package com.tricol.CommandeFournisseur.controller;

import com.tricol.CommandeFournisseur.model.dto.CommandeFournisseurDto;
import com.tricol.CommandeFournisseur.model.enums.StatutCommande;
import com.tricol.CommandeFournisseur.service.CommandeFournisseurService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/commande")
@RequiredArgsConstructor
public class CommandeFournisseurController {

    private final CommandeFournisseurService service;

    @GetMapping
    public ResponseEntity<List<CommandeFournisseurDto>> getAll() {
        List<CommandeFournisseurDto> commandes = service.getAll();
        return ResponseEntity.ok(commandes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getByID(@PathVariable int id) {
        Optional<CommandeFournisseurDto> commande = service.findById(id);
        if (commande.isPresent()) {
            return ResponseEntity.ok(commande.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Commande not found");
        }
    }

    @PostMapping
    public ResponseEntity<CommandeFournisseurDto> save(@RequestBody CommandeFournisseurDto dto) {
        try {
            CommandeFournisseurDto commande = service.save(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(commande);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable int id, @RequestParam StatutCommande statut) {
        String result = service.updateStatus(id, statut);
        if (result.contains("not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        String result = service.delete(id);
        if (result.contains("not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
        return ResponseEntity.ok(result);
    }
}
