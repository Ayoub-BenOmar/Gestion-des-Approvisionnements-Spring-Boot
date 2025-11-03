package com.tricol.CommandeFournisseur.model.mapper;

import com.tricol.CommandeFournisseur.model.dto.FournisseurDto;
import com.tricol.CommandeFournisseur.model.entities.Fournisseur;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FournisseurMapper {
    Fournisseur toEntity(FournisseurDto dto);
    FournisseurDto toDto(Fournisseur fournisseur);
}
