package com.tricol.CommandeFournisseur.model.mapper;

import com.tricol.CommandeFournisseur.model.dto.MouvementStockDto;
import com.tricol.CommandeFournisseur.model.entities.MouvementStock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MouvementStockMapper {

    @Mapping(target = "produitId", expression = "java(mouvementStock.getProduit() != null ? mouvementStock.getProduit().getId() : null)")
    @Mapping(target = "fournisseurId", expression = "java(mouvementStock.getFournisseur() != null ? mouvementStock.getFournisseur().getId() : null)")
    @Mapping(target = "commandeId", expression = "java(mouvementStock.getCommande() != null ? mouvementStock.getCommande().getId() : null)")
    MouvementStockDto toDto(MouvementStock mouvementStock);

    // mapping inverse si besoin
    MouvementStock toEntity(MouvementStockDto dto);
}
