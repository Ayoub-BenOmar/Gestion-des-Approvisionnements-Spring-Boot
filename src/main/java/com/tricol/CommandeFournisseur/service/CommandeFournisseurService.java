package com.tricol.CommandeFournisseur.service;

import com.tricol.CommandeFournisseur.model.dto.CommandeFournisseurDto;
import com.tricol.CommandeFournisseur.model.entities.CommandeFournisseur;
import com.tricol.CommandeFournisseur.model.entities.CommandeFournisseurProduit;
import com.tricol.CommandeFournisseur.model.entities.Fournisseur;
import com.tricol.CommandeFournisseur.model.entities.Produit;
import com.tricol.CommandeFournisseur.model.enums.StatutCommande;
import com.tricol.CommandeFournisseur.model.mapper.CommandeFournisseurMapper;
import com.tricol.CommandeFournisseur.repository.CommandeFournisseurRepository;
import com.tricol.CommandeFournisseur.repository.FournisseurRepository;
import com.tricol.CommandeFournisseur.repository.ProduitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommandeFournisseurService {

    private final CommandeFournisseurRepository commandeRepository;
    private final FournisseurRepository fournisseurRepository;
    private final ProduitRepository produitRepository;
    private final CommandeFournisseurMapper mapper;
    private final MouvementStockService mouvementStockService;

    public List<CommandeFournisseurDto> getAll() {
        List<CommandeFournisseur> commandes = commandeRepository.findAll();
        return mapper.toDtoList(commandes);
    }

    public Optional<CommandeFournisseurDto> findById(Integer id) {
        return commandeRepository.findById(id).map(mapper::toDto);
    }

    public CommandeFournisseurDto save(CommandeFournisseurDto dto) {
        Fournisseur fournisseur = fournisseurRepository.findById(dto.getFournisseurId())
                .orElseThrow(() -> new RuntimeException("Fournisseur not found"));

        CommandeFournisseur commande = new CommandeFournisseur();
        commande.setDateCommande(LocalDate.now());
        commande.setStatut(StatutCommande.EN_ATTENTE);
        commande.setFournisseur(fournisseur);

        double total = 0.0;
        List<CommandeFournisseurProduit> commandeProduits = new ArrayList<>();

        for (var pDto : dto.getProduits()) {
            Produit produit = produitRepository.findById(pDto.getProduitId())
                    .orElseThrow(() -> new RuntimeException("Produit not found"));
            total += produit.getCump() * pDto.getQuantite();

            commandeProduits.add(CommandeFournisseurProduit.builder()
                    .commande(commande)
                    .produit(produit)
                    .quantite(pDto.getQuantite())
                    .build());
        }

        commande.setMontantTotal(total);
        commande.setCommandeProduits(commandeProduits);

        CommandeFournisseur saved = commandeRepository.save(commande);
        mouvementStockService.createMouvementSortie(saved);

        return CommandeFournisseurDto.builder()
                .id(saved.getId())
                .dateCommande(saved.getDateCommande())
                .statut(saved.getStatut())
                .montantTotal(saved.getMontantTotal())
                .fournisseurId(saved.getFournisseur().getId())
                .produits(dto.getProduits())
                .build();
    }


    public String updateStatus(Integer id, StatutCommande statut) {
        Optional<CommandeFournisseur> opt = commandeRepository.findById(id);
        if (opt.isPresent()) {
            CommandeFournisseur commande = opt.get();
            commande.setStatut(statut);
            commandeRepository.save(commande);
            return "Commande updated.";
        } else {
            return "Commande not found.";
        }
    }

    public String delete(Integer id) {
        Optional<CommandeFournisseur> opt = commandeRepository.findById(id);
        if (opt.isPresent()) {
            commandeRepository.delete(opt.get());
            return "Commande deleted.";
        } else {
            return "Commande not found.";
        }
    }
}

