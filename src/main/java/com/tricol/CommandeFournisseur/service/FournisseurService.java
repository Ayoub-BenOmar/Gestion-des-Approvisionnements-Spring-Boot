package com.tricol.CommandeFournisseur.service;

import com.tricol.CommandeFournisseur.model.dto.FournisseurDto;
import com.tricol.CommandeFournisseur.model.dto.ProduitDto;
import com.tricol.CommandeFournisseur.model.entities.Fournisseur;
import com.tricol.CommandeFournisseur.model.entities.Produit;
import com.tricol.CommandeFournisseur.model.mapper.FournisseurMapper;
import com.tricol.CommandeFournisseur.repository.FournisseurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FournisseurService {
    private final FournisseurRepository repository;
    private final FournisseurMapper fournisseurMapper;

    public List<FournisseurDto> getAll() {
        return repository.findAll().stream().map(fournisseurMapper::toDto).toList();
    }

    public Page<FournisseurDto> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Fournisseur> fournisseurs = repository.findAll(pageable);
        return fournisseurs.map(fournisseurMapper::toDto);
    }

    public FournisseurDto save(FournisseurDto dto) {
        Fournisseur fournisseur = fournisseurMapper.toEntity(dto);
        fournisseur = repository.save(fournisseur);
        return fournisseurMapper.toDto(fournisseur);
    }

    public Optional<FournisseurDto> findById(Integer id){
        return repository.findById(id).map(fournisseurMapper::toDto);
    }

    public FournisseurDto update(FournisseurDto dto){
        Fournisseur fournisseur = fournisseurMapper.toEntity(dto);
        fournisseur = repository.save(fournisseur);
        return fournisseurMapper.toDto(fournisseur);
    }

    public void delete(Integer id){
        repository.findById(id).ifPresent(repository::delete);
    }
}
