package com.tricol.CommandeFournisseur.service;

import com.tricol.CommandeFournisseur.model.dto.ProduitDto;
import com.tricol.CommandeFournisseur.model.entities.Produit;
import com.tricol.CommandeFournisseur.model.mapper.ProduitMapper;
import com.tricol.CommandeFournisseur.repository.ProduitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProduitService {
    private final ProduitRepository repository;
    private final ProduitMapper produitMapper;

    public ProduitDto save(ProduitDto dto){
        Produit produit = produitMapper.toEntity(dto);
        produit = repository.save(produit);
        return produitMapper.toDto(produit);
    }

    public List<ProduitDto> getAll(){
        return repository.findAll().stream().map(produitMapper::toDto).toList();
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
}
