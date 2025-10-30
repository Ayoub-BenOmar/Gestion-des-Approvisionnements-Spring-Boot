package com.tricol.CommandeFournisseur.controller;

import com.tricol.CommandeFournisseur.entities.Fournisseur;
import com.tricol.CommandeFournisseur.service.FournisseurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/fournisseurs")
public class FournisseurController {

    private final FournisseurService service;

    public FournisseurController(FournisseurService service) {
        this.service = service;
    }

    @GetMapping
    public List<Fournisseur> getAll() {
        return service.getAll();
    }

    @PostMapping
    public ResponseEntity<Fournisseur> create(@RequestBody Fournisseur fournisseur) {
        Fournisseur saved = service.save(fournisseur);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
