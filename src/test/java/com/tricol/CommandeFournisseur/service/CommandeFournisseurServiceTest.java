package com.tricol.CommandeFournisseur.service;

import com.tricol.CommandeFournisseur.model.dto.CommandeFournisseurDto;
import com.tricol.CommandeFournisseur.model.dto.ProduitCommandeDto;
import com.tricol.CommandeFournisseur.model.entities.CommandeFournisseur;
import com.tricol.CommandeFournisseur.model.entities.CommandeFournisseurProduit;
import com.tricol.CommandeFournisseur.model.entities.Fournisseur;
import com.tricol.CommandeFournisseur.model.entities.Produit;
import com.tricol.CommandeFournisseur.model.enums.StatutCommande;
import com.tricol.CommandeFournisseur.model.mapper.CommandeFournisseurMapper;
import com.tricol.CommandeFournisseur.repository.CommandeFournisseurRepository;
import com.tricol.CommandeFournisseur.repository.FournisseurRepository;
import com.tricol.CommandeFournisseur.repository.ProduitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommandeFournisseurServiceTest {

    @Mock
    private CommandeFournisseurRepository commandeRepository;

    @Mock
    private FournisseurRepository fournisseurRepository;

    @Mock
    private ProduitRepository produitRepository;

    @Mock
    private CommandeFournisseurMapper mapper;

    @Mock
    private MouvementStockService mouvementStockService;

    @InjectMocks
    private CommandeFournisseurService service;

    @Test
    public void testSaveCommande() {
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setId(1);

        Produit produit = new Produit();
        produit.setId(1);
        produit.setCump(10.0);

        ProduitCommandeDto pDto = new ProduitCommandeDto();
        pDto.setProduitId(1);
        pDto.setQuantite(5);

        CommandeFournisseurDto dto = CommandeFournisseurDto.builder()
                .fournisseurId(1)
                .produits(List.of(pDto))
                .build();

        when(fournisseurRepository.findById(1)).thenReturn(Optional.of(fournisseur));
        when(produitRepository.findById(1)).thenReturn(Optional.of(produit));

        CommandeFournisseur savedCommande = new CommandeFournisseur();
        savedCommande.setId(100);
        savedCommande.setFournisseur(fournisseur);
        savedCommande.setDateCommande(LocalDate.now());
        savedCommande.setStatut(StatutCommande.EN_ATTENTE);
        savedCommande.setMontantTotal(50.0);
        savedCommande.setCommandeProduits(Arrays.asList(
                CommandeFournisseurProduit.builder().commande(savedCommande).produit(produit).quantite(5).build()
        ));

        when(commandeRepository.save(any(CommandeFournisseur.class))).thenReturn(savedCommande);

        CommandeFournisseurDto result = service.save(dto);

        assertEquals(1, result.getFournisseurId());
        assertEquals(1, result.getProduits().size());
        verify(mouvementStockService, times(1)).createMouvementSortie(savedCommande);
        verify(commandeRepository, times(1)).save(any(CommandeFournisseur.class));
    }

    @Test
    public void testUpdateStatusCommande() {
        CommandeFournisseur commande = new CommandeFournisseur();
        commande.setId(1);
        commande.setStatut(StatutCommande.EN_ATTENTE);

        when(commandeRepository.findById(1)).thenReturn(Optional.of(commande));
        when(commandeRepository.save(commande)).thenReturn(commande);

        String response = service.updateStatus(1, StatutCommande.VALIDEE);

        assertEquals("Commande updated.", response);
        assertEquals(StatutCommande.VALIDEE, commande.getStatut());
        verify(commandeRepository, times(1)).save(commande);
    }

    @Test
    public void testDeleteCommande() {
        CommandeFournisseur commande = new CommandeFournisseur();
        commande.setId(1);

        when(commandeRepository.findById(1)).thenReturn(Optional.of(commande));

        String response = service.delete(1);

        assertEquals("Commande deleted.", response);
        verify(commandeRepository, times(1)).delete(commande);
    }
}
