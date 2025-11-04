package com.tricol.CommandeFournisseur.model.mapper;

import com.tricol.CommandeFournisseur.model.dto.CommandeFournisseurDto;
import com.tricol.CommandeFournisseur.model.entities.CommandeFournisseur;
import com.tricol.CommandeFournisseur.model.entities.Fournisseur;
import com.tricol.CommandeFournisseur.model.entities.Produit;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CommandeFournisseurMapper {

    CommandeFournisseurDto toDto(CommandeFournisseur commande);
    CommandeFournisseur toEntity(CommandeFournisseurDto dto);

    @Named("fournisseurToId")
    default Integer fournisseurToId(Fournisseur fournisseur) {
        return fournisseur != null ? fournisseur.getId() : null;
    }
    @Named("produitsToIds")
    default List<Integer> produitsToIds(List<Produit> produits) {
        return produits.stream().map(Produit::getId).collect(Collectors.toList());
    }

    @Named("idToFournisseur")
    default Fournisseur idToFournisseur(Integer id) {
        return null;
    }
    @Named("idsToProduits")
    default List<Produit> idsToProduits(List<Integer> ids) {
        return null;
    }

}
