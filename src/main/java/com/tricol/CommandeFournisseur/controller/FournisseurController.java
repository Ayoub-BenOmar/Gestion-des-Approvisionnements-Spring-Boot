package com.tricol.CommandeFournisseur.controller;

import com.tricol.CommandeFournisseur.model.dto.FournisseurDto;
import com.tricol.CommandeFournisseur.model.dto.PagedResponse;
import com.tricol.CommandeFournisseur.model.dto.ProduitDto;
import com.tricol.CommandeFournisseur.service.FournisseurService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/fournisseurs")
@RequiredArgsConstructor
public class FournisseurController {

    private final FournisseurService service;

    @GetMapping
    public ResponseEntity<Page<FournisseurDto>> getAll(@RequestParam int page, @RequestParam int size){
        return ResponseEntity.ok(service.getAll(page, size));
    }

    @PostMapping
    public ResponseEntity<FournisseurDto> create(@Valid @RequestBody FournisseurDto dto) {
        FournisseurDto saved = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id){
        Optional<FournisseurDto> dto = service.findById(id);
        if (dto.isPresent()){
            return ResponseEntity.ok(dto.get());
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fournisseur wih the id: " + id + "not found.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody FournisseurDto dto){
        Optional<FournisseurDto> found = service.findById(id);
        if (found.isPresent()){
            FournisseurDto exist = found.get();
            exist.setSociete(dto.getSociete());
            exist.setAdresse(dto.getAdresse());
            exist.setContact(dto.getContact());
            exist.setEmail(dto.getEmail());
            exist.setTelephone(dto.getTelephone());
            exist.setVille(dto.getVille());
            exist.setICE(dto.getICE());

            FournisseurDto updated = service.update(exist);
            return ResponseEntity.ok(updated);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fournisseur with ID " + id + " not found.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id){
        Optional<FournisseurDto> found = service.findById(id);
        if (found.isPresent()){
            service.delete(id);
            return ResponseEntity.ok("Fournisseur deleted.");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fournisseur with ID " + id + " not found.");
        }
    }
}
