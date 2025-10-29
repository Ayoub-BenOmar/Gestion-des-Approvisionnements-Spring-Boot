package com.tricol.CommandeFournisseur.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

@Entity
public class Fournisseur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String societe;
    private String adresse;
    private String contact;

    @Email
    private String email;
    private String telephone;
    private String ville;
    private String ICE;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getSociete() { return societe; }
    public void setSociete(String societe) { this.societe = societe; }
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }
    public String getICE() { return ICE; }
    public void setICE(String ICE) { this.ICE = ICE; }
}
