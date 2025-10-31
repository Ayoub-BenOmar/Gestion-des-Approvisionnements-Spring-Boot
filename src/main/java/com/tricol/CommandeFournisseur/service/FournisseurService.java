package com.tricol.CommandeFournisseur.service;

import com.tricol.CommandeFournisseur.entities.Fournisseur;
import com.tricol.CommandeFournisseur.repository.FournisseurRepository;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

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

    public Optional<Fournisseur> findById(Integer id){
        if (id != null){
            return repository.findById(id);
        }else {
            return Optional.empty();
        }
    }

    public void update(Fournisseur fournisseur){
        repository.save(fournisseur);
    }
}
