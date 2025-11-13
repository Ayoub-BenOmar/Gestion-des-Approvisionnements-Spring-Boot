package com.tricol.CommandeFournisseur.model.mapper;

import com.tricol.CommandeFournisseur.model.dto.CommandeFournisseurDto;
import com.tricol.CommandeFournisseur.model.dto.ProduitCommandeDto;
import com.tricol.CommandeFournisseur.model.entities.CommandeFournisseur;
import com.tricol.CommandeFournisseur.model.entities.CommandeFournisseurProduit;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CommandeFournisseurMapper {

    @Mapping(target = "fournisseurId", source = "fournisseur.id")
    @Mapping(target = "produits", source = "commandeProduits")
    CommandeFournisseurDto toDto(CommandeFournisseur commandeFournisseur);

    @Mapping(target = "fournisseur", ignore = true)
    @Mapping(target = "commandeProduits", ignore = true)
    CommandeFournisseur toEntity(CommandeFournisseurDto dto);

    List<CommandeFournisseurDto> toDtoList(List<CommandeFournisseur> commandes);

    default List<ProduitCommandeDto> mapCommandeProduitsToProduitDtos(List<CommandeFournisseurProduit> commandeProduits) {
        if (commandeProduits == null) return null;

        return commandeProduits.stream()
                .map(cp -> ProduitCommandeDto.builder()
                        .produitId(cp.getProduit().getId())
                        .quantite(cp.getQuantite())
                        .build())
                .collect(Collectors.toList());
    }
}
