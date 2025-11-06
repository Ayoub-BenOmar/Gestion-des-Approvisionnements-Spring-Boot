package com.tricol.CommandeFournisseur.model.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProduitCommandeDto {
    private Integer produitId;
    private Double quantite;
}
