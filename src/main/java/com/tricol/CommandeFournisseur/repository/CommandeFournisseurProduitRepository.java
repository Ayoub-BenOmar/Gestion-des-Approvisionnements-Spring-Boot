package com.tricol.CommandeFournisseur.repository;

import com.tricol.CommandeFournisseur.model.entities.CommandeFournisseurProduit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeFournisseurProduitRepository extends JpaRepository<CommandeFournisseurProduit, Integer> {
}
