package com.tricol.CommandeFournisseur.model.dto;

import com.tricol.CommandeFournisseur.model.enums.TypeMouvement;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MouvementStockDto {
    private Integer id;
    private LocalDate dateMouvement;
    private Double quantite;
    private TypeMouvement typeMouvement;
    private Integer fournisseurId;
    private Integer commandeId;
    private Integer produitId;
}
