package com.tricol.CommandeFournisseur.service;

import com.tricol.CommandeFournisseur.model.dto.FournisseurDto;
import com.tricol.CommandeFournisseur.model.entities.Fournisseur;
import com.tricol.CommandeFournisseur.model.mapper.FournisseurMapper;
import com.tricol.CommandeFournisseur.repository.FournisseurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    public Page<FournisseurDto> getAll(Pageable pageable) {
        var page = repository.findAll(pageable);
        List<FournisseurDto> dtos = page.getContent().stream().map(fournisseurMapper::toDto).toList();
        return new PageImpl<>(dtos, pageable, page.getTotalElements());
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
