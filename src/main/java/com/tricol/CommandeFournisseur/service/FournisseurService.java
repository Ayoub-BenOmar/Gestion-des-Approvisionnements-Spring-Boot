package com.tricol.CommandeFournisseur.service;

import com.tricol.CommandeFournisseur.entities.Fournisseur;
import com.tricol.CommandeFournisseur.repository.FournisseurRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FournisseurService {
    private final FournisseurRepository repository;

    public FournisseurService(FournisseurRepository repository) {
        this.repository = repository;
    }

    public List<Fournisseur> getAll() {
        return repository.findAll();
    }

    public Fournisseur save(Fournisseur fournisseur) {
        return repository.save(fournisseur);
    }
}
