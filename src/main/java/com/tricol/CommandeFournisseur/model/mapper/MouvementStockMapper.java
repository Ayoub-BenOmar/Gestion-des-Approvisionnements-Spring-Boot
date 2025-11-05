package com.tricol.CommandeFournisseur.model.mapper;

import com.tricol.CommandeFournisseur.model.dto.MouvementStockDto;
import com.tricol.CommandeFournisseur.model.entities.MouvementStock;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MouvementStockMapper {
    MouvementStock toEntity(MouvementStockDto dto);
    MouvementStockDto toDto(MouvementStock mouvementStock);
}
