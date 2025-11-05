package com.tricol.CommandeFournisseur.service;

import com.tricol.CommandeFournisseur.model.dto.CommandeFournisseurDto;
import com.tricol.CommandeFournisseur.model.entities.CommandeFournisseur;
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
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommandeFournisseurService {

    private final CommandeFournisseurRepository commandeRepository;
    private final FournisseurRepository fournisseurRepository;
    private final ProduitRepository produitRepository;
    private final CommandeFournisseurMapper mapper;

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

        List<Produit> produits = produitRepository.findAllById(dto.getProduitIds());

        double total = produits.stream()
                .mapToDouble(Produit::getPrixUnitaire)
                .sum();

        CommandeFournisseur commande = new CommandeFournisseur();
        commande.setDateCommande(LocalDate.now());
        commande.setStatut(StatutCommande.EN_ATTENTE);
        commande.setFournisseur(fournisseur);
        commande.setProduits(produits);
        commande.setMontantTotal(total);

        CommandeFournisseur saved = commandeRepository.save(commande);

        CommandeFournisseurDto responseDto = new CommandeFournisseurDto();
        responseDto.setId(saved.getId());
        responseDto.setDateCommande(saved.getDateCommande());
        responseDto.setStatut(saved.getStatut());
        responseDto.setMontantTotal(saved.getMontantTotal());
        responseDto.setFournisseurId(saved.getFournisseur().getId());
        responseDto.setProduitIds(saved.getProduits().stream().map(Produit::getId).toList());

        return responseDto;
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

