package com.tricol.CommandeFournisseur.model.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FournisseurDto {
    private Integer id;
    private String societe;
    private String adresse;
    private String contact;
    private String email;
    private String telephone;
    private String ville;
    private String ICE;
}
