package com.tricol.CommandeFournisseur.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
public class Produit {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    @Getter
    @Column(nullable = false)
    private String nom;
    @Setter
    @Getter
    private String description;

    @Setter
    @Getter
    @Column(nullable = false)
    private double prixUnitaire;
    @Setter
    @Getter
    private String categorie;

    @Setter
    @Getter
    @Column(nullable = false)
    private double stock = 0.0;

    @Setter
    @Getter
    @Column(nullable = false)
    private Double cump = 0.0;

    @Setter
    @Getter
    @OneToMany(mappedBy = "produit")
    private List<CommandeFournisseurProduit> commandesProduits;

}
