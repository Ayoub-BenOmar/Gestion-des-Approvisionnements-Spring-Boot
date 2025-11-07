package com.tricol.CommandeFournisseur.model.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProduitDto {
    private Integer id;
    private String nom;
    private String description;
    private double prixUnitaire;
    private String categorie;
    private double stock = 0.0;
    private double cump = 0.0;
}
