package com.tricol.CommandeFournisseur.service;

import com.tricol.CommandeFournisseur.model.entities.CommandeFournisseur;
import com.tricol.CommandeFournisseur.model.entities.CommandeFournisseurProduit;
import com.tricol.CommandeFournisseur.model.entities.Produit;
import com.tricol.CommandeFournisseur.model.entities.MouvementStock;
import com.tricol.CommandeFournisseur.model.enums.TypeMouvement;
import com.tricol.CommandeFournisseur.repository.MouvementStockRepository;
import com.tricol.CommandeFournisseur.repository.ProduitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MouvementStockServiceTest {

    @Mock
    private MouvementStockRepository stockRepository;

    @Mock
    private ProduitRepository produitRepository;

    @InjectMocks
    private MouvementStockService service;

    @Test
    public void testCreateMouvementEntree() {
        Produit produit = new Produit();
        produit.setId(1);
        produit.setStock(50);

        service.createMouvementEntree(produit);

        ArgumentCaptor<MouvementStock> captor = ArgumentCaptor.forClass(MouvementStock.class);
        verify(stockRepository, times(1)).save(captor.capture());
        verify(produitRepository, times(1)).save(produit);

        MouvementStock mouvement = captor.getValue();
        assertEquals(produit, mouvement.getProduit());
        assertEquals(50, mouvement.getQuantite());
        assertEquals(TypeMouvement.ENTREE, mouvement.getTypeMouvement());
    }

    @Test
    public void testCreateMouvementSortie() {
        Produit produit = new Produit();
        produit.setId(1);
        produit.setStock(100);

        CommandeFournisseur commande = new CommandeFournisseur();
        CommandeFournisseurProduit cp = CommandeFournisseurProduit.builder()
                .produit(produit)
                .quantite(20)
                .commande(commande)
                .build();
        commande.setCommandeProduits(List.of(cp));

        service.createMouvementSortie(commande);

        ArgumentCaptor<MouvementStock> captor = ArgumentCaptor.forClass(MouvementStock.class);
        verify(stockRepository, times(1)).save(captor.capture());
        verify(produitRepository, times(1)).save(produit);

        MouvementStock mouvement = captor.getValue();
        assertEquals(produit, mouvement.getProduit());
        assertEquals(20, mouvement.getQuantite());
        assertEquals(TypeMouvement.SORTIE, mouvement.getTypeMouvement());
        assertEquals(80, produit.getStock()); // stock updated
    }
}
