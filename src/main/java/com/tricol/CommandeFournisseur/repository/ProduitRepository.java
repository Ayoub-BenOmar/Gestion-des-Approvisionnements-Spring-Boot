package com.tricol.CommandeFournisseur.repository;

import com.tricol.CommandeFournisseur.model.entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Integer> {
}
