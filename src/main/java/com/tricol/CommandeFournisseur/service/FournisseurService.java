package com.tricol.CommandeFournisseur.service;

import com.tricol.CommandeFournisseur.model.dto.FournisseurDto;
import com.tricol.CommandeFournisseur.model.entities.Fournisseur;
import com.tricol.CommandeFournisseur.model.mapper.FournisseurMapper;
import com.tricol.CommandeFournisseur.repository.FournisseurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FournisseurService {
    private final FournisseurRepository repository;
    private final FournisseurMapper fournisseurMapper;

    public List<FournisseurDto> getAll() {
        return repository.findAll().stream().map(fournisseurMapper::toDto).collect(Collectors.toList());
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
