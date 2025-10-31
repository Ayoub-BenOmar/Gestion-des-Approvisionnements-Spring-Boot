package com.tricol.CommandeFournisseur.controller;

import com.tricol.CommandeFournisseur.entities.Fournisseur;
import com.tricol.CommandeFournisseur.service.FournisseurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id){
        Optional<Fournisseur> fournisseur = service.findById(id);
        if (fournisseur.isPresent()){
            return ResponseEntity.ok(fournisseur.get());
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fournisseur wih the id: " + id + "not found.");
        }
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Integer id, @RequestBody Fournisseur fournisseur){
        Optional<Fournisseur> found = service.findById(id);
        if (found.isPresent()){
            Fournisseur exist = found.get();
            exist.setSociete(fournisseur.getSociete());
            exist.setAdresse(fournisseur.getAdresse());
            exist.setContact(fournisseur.getContact());
            exist.setEmail(fournisseur.getEmail());
            exist.setTelephone(fournisseur.getTelephone());
            exist.setVille(fournisseur.getVille());
            exist.setICE(fournisseur.getICE());

            service.update(exist);
            return "Fournisseur updated.";
        }else {
            return "Fournisseur not found";
        }
    }
}
