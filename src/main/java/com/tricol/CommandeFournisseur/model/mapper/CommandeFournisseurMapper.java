package com.tricol.CommandeFournisseur.model.mapper;

import com.tricol.CommandeFournisseur.model.dto.CommandeFournisseurDto;
import com.tricol.CommandeFournisseur.model.entities.CommandeFournisseur;
import com.tricol.CommandeFournisseur.model.entities.Fournisseur;
import com.tricol.CommandeFournisseur.model.entities.Produit;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CommandeFournisseurMapper {

    @Mapping(target = "fournisseurId", source = "fournisseur.id")
    @Mapping(target = "produitIds", expression = "java(mapProduitsToIds(commandeFournisseur.getProduits()))")
    CommandeFournisseurDto toDto(CommandeFournisseur commandeFournisseur);

    @Mapping(target = "fournisseur", ignore = true)
    @Mapping(target = "produits", ignore = true)
    CommandeFournisseur toEntity(CommandeFournisseurDto dto);

    List<CommandeFournisseurDto> toDtoList(List<CommandeFournisseur> commandes);

    default List<Integer> mapProduitsToIds(List<Produit> produits) {
        if (produits == null) return null;
        return produits.stream()
                .map(Produit::getId)
                .collect(Collectors.toList());
    }
}
