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

import java.time.LocalDate;

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

}
