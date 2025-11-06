package com.tricol.CommandeFournisseur.service;

import com.tricol.CommandeFournisseur.model.entities.CommandeFournisseur;
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

//    public void createMouvementSortie(CommandeFournisseur commandeFournisseur){
//        MouvementStock mouvement = MouvementStock.builder()
//                .dateMouvement(LocalDate.now())
//                .
//    }
}
