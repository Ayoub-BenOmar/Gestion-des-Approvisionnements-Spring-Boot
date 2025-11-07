package com.tricol.CommandeFournisseur.controller;

import com.tricol.CommandeFournisseur.model.dto.ProduitDto;
import com.tricol.CommandeFournisseur.service.ProduitService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/produits")
@RequiredArgsConstructor
public class ProduitController {
    private final ProduitService service;

    @GetMapping
    public ResponseEntity<Page<ProduitDto>> getAll(@RequestParam int page, @RequestParam int size){
        return ResponseEntity.ok(service.getAll(page, size));
    }

    @PostMapping
    public ResponseEntity<ProduitDto> save(@RequestBody ProduitDto dto){
        ProduitDto saved = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id){
        Optional<ProduitDto> produitDto = service.getById(id);
        if (produitDto.isPresent()){
            return ResponseEntity.ok(produitDto.get());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with the id : " + id + " not found");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody ProduitDto dto){
        Optional<ProduitDto> found = service.getById(id);
        if (found.isPresent()){
            ProduitDto exist = found.get();
            exist.setNom(dto.getNom());
            exist.setCategorie(dto.getCategorie());
            exist.setDescription(dto.getDescription());
            exist.setPrixUnitaire(dto.getPrixUnitaire());
            exist.setStock(dto.getStock());

            ProduitDto updated = service.update(exist);
            return ResponseEntity.ok(updated);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id){
        Optional<ProduitDto> produitDto = service.getById(id);
        if (produitDto.isPresent()){
            service.delete(id);
            return ResponseEntity.ok("Product deleted.");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }
}
