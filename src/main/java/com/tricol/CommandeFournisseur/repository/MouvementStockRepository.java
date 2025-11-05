package com.tricol.CommandeFournisseur.repository;

import com.tricol.CommandeFournisseur.model.entities.MouvementStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MouvementStockRepository extends JpaRepository<MouvementStock, Integer> {
}
