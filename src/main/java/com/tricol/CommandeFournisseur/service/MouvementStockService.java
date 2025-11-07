package com.tricol.CommandeFournisseur.service;

import com.tricol.CommandeFournisseur.model.entities.CommandeFournisseur;
import com.tricol.CommandeFournisseur.model.entities.CommandeFournisseurProduit;
import com.tricol.CommandeFournisseur.model.entities.MouvementStock;
import com.tricol.CommandeFournisseur.model.entities.Produit;
import com.tricol.CommandeFournisseur.model.enums.TypeMouvement;
import com.tricol.CommandeFournisseur.repository.MouvementStockRepository;
import com.tricol.CommandeFournisseur.repository.ProduitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MouvementStockService {
    private final MouvementStockRepository stockRepository;
    private final ProduitRepository produitRepository;

    public void createMouvementEntree(Produit produit) {
        MouvementStock mouvement = MouvementStock.builder()
                .produit(produit)
                .quantite(produit.getStock())
                .typeMouvement(TypeMouvement.ENTREE)
                .dateMouvement(LocalDate.now())
                .build();

        stockRepository.save(mouvement);
        produitRepository.save(produit);
    }

    public void createMouvementSortie(CommandeFournisseur commandeFournisseur) {
        for (CommandeFournisseurProduit cp : commandeFournisseur.getCommandeProduits()) {
            MouvementStock mouvement = MouvementStock.builder()
                    .dateMouvement(LocalDate.now())
                    .typeMouvement(TypeMouvement.SORTIE)
                    .fournisseur(commandeFournisseur.getFournisseur())
                    .commande(commandeFournisseur)
                    .produit(cp.getProduit())
                    .quantite(cp.getQuantite())
                    .build();

            Produit produit = cp.getProduit();
            produit.setStock(produit.getStock() - cp.getQuantite());

            stockRepository.save(mouvement);
            produitRepository.save(produit);
        }
    }

    @Transactional(readOnly = true)
    public List<MouvementStock> findMouvements(Integer produitId, TypeMouvement type, Integer commandeId) {
        if (produitId != null && type != null && commandeId != null) {
            return stockRepository.findByProduitIdAndTypeMouvementAndCommandeId(produitId, type, commandeId);
        } else if (produitId != null && type != null) {
            return stockRepository.findByProduitIdAndTypeMouvement(produitId, type);
        } else if (produitId != null && commandeId != null) {
            return stockRepository.findByProduitIdAndCommandeId(produitId, commandeId);
        } else if (type != null && commandeId != null) {
            return stockRepository.findByTypeMouvementAndCommandeId(type, commandeId);
        } else if (produitId != null) {
            return stockRepository.findByProduitId(produitId);
        } else if (type != null) {
            return stockRepository.findByTypeMouvement(type);
        } else if (commandeId != null) {
            return stockRepository.findByCommandeId(commandeId);
        } else {
            return stockRepository.findAll();
        }
    }

}
