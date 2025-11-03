package com.tricol.CommandeFournisseur.service;

import com.tricol.CommandeFournisseur.model.entities.Produit;
import com.tricol.CommandeFournisseur.repository.ProduitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduitService {
    private final ProduitRepository repository;

    public ProduitService(ProduitRepository repository) {
        this.repository = repository;
    }

    public Produit save(Produit produit){
        return repository.save(produit);
    }

    public List<Produit> getAll(Produit produit){

    }
}
