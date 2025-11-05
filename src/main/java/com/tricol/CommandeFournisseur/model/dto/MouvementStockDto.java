package com.tricol.CommandeFournisseur.model.dto;

import com.tricol.CommandeFournisseur.model.entities.CommandeFournisseur;
import com.tricol.CommandeFournisseur.model.entities.Fournisseur;
import com.tricol.CommandeFournisseur.model.enums.TypeMouvement;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MouvementStockDto {
    private Integer id;

    private LocalDate dateMouvement;
    private Integer quantite;
    private TypeMouvement typeMouvement;
    private Fournisseur fournisseur;
    private CommandeFournisseur commande;
}
