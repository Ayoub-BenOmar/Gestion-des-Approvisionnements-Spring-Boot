package com.tricol.CommandeFournisseur.model.dto;

import com.tricol.CommandeFournisseur.model.enums.StatutCommande;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommandeFournisseurDto {
    private Integer id;
    private LocalDate dateCommande;
    private StatutCommande statut;
    private Double montantTotal;
    private Integer fournisseurId;
    private List<Integer> produitIds;
}
