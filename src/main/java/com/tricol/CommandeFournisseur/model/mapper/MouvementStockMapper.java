package com.tricol.CommandeFournisseur.model.mapper;

import com.tricol.CommandeFournisseur.model.dto.MouvementStockDto;
import com.tricol.CommandeFournisseur.model.entities.MouvementStock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MouvementStockMapper {

    @Mapping(target = "produitId", source = "produit.id")
    @Mapping(target = "fournisseurId", source = "fournisseur.id")
    @Mapping(target = "commandeId", source = "commande.id")
    MouvementStockDto toDto(MouvementStock mouvementStock);

    MouvementStock toEntity(MouvementStockDto dto);
}
