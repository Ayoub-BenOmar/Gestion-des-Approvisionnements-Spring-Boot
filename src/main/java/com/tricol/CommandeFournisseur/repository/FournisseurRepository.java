package com.tricol.CommandeFournisseur.repository;

import com.tricol.CommandeFournisseur.model.entities.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Integer> {
}
