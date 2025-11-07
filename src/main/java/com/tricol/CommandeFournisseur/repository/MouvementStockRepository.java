package com.tricol.CommandeFournisseur.repository;

import com.tricol.CommandeFournisseur.model.entities.MouvementStock;
import com.tricol.CommandeFournisseur.model.enums.TypeMouvement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MouvementStockRepository extends JpaRepository<MouvementStock, Integer> {
    List<MouvementStock> findByProduitId(Integer produitId);
    List<MouvementStock> findByTypeMouvement(TypeMouvement typeMouvement);
    List<MouvementStock> findByCommandeId(Integer commandeId);
    List<MouvementStock> findByProduitIdAndTypeMouvement(Integer produitId, TypeMouvement typeMouvement);
    List<MouvementStock> findByProduitIdAndCommandeId(Integer produitId, Integer commandeId);
    List<MouvementStock> findByTypeMouvementAndCommandeId(TypeMouvement typeMouvement, Integer commandeId);
    List<MouvementStock> findByProduitIdAndTypeMouvementAndCommandeId(Integer produitId, TypeMouvement typeMouvement, Integer commandeId);
}
