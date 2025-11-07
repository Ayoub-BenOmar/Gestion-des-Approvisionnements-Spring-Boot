package com.tricol.CommandeFournisseur.service;

import com.tricol.CommandeFournisseur.model.dto.ProduitDto;
import com.tricol.CommandeFournisseur.model.entities.Produit;
import com.tricol.CommandeFournisseur.model.mapper.ProduitMapper;
import com.tricol.CommandeFournisseur.repository.ProduitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProduitService {
    private final ProduitRepository repository;
    private final ProduitMapper produitMapper;
    private final MouvementStockService mouvementStockService;

    public ProduitDto save(ProduitDto dto){
        Produit produit = produitMapper.toEntity(dto);
        produit = updateCump(produit);
        Produit saved = repository.save(produit);

        mouvementStockService.createMouvementEntree(saved);
        return produitMapper.toDto(saved);
    }

    public List<ProduitDto> getAll(){
        return repository.findAll().stream().map(produitMapper::toDto).toList();
    }

    public Page<ProduitDto> getAll(int page,int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Produit> produits = repository.findAll(pageable);
        return produits.map(produitMapper::toDto);
    }

    public Optional<ProduitDto> getById(Integer id){
        return repository.findById(id).map(produitMapper::toDto);
    }

    public ProduitDto update(ProduitDto dto){
        Produit produit = produitMapper.toEntity(dto);
        produit = repository.save(produit);
        return produitMapper.toDto(produit);
    }

    public void delete(Integer id){
        repository.findById(id).ifPresent(repository::delete);
    }

    public Page<Produit> getProduitsPaged(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return repository.findAll(pageable);
    }

    public Produit updateCump(Produit produit){
        Optional<Produit> existing = repository.findByNom(produit.getNom());
        if (existing.isPresent()){
            Produit existingP = existing.get();
            double stock = existingP.getStock();
            double cump = existingP.getCump();

            double quantiteAjoutee = produit.getStock();
            double prix = produit.getPrixUnitaire();

            double nouveauCump = ((stock * cump) + (quantiteAjoutee * prix)) / (stock + quantiteAjoutee);

            existingP.setCump(nouveauCump);
            existingP.setStock(stock + quantiteAjoutee);
            existingP.setPrixUnitaire(prix);

            return existingP;
        }else{
            produit.setCump(produit.getPrixUnitaire());
            return produit;
        }
    }
}
