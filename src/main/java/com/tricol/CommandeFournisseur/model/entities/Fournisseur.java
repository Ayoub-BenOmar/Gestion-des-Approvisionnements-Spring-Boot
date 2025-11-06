package com.tricol.CommandeFournisseur.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Fournisseur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Societe is required")
    private String societe;

    @NotBlank(message = "Adresse is required")
    private String adresse;

    @NotBlank(message = "Contact is required")
    private String contact;

    @Email(message = "Email must be valid")
    private String email;

    @Pattern(regexp = "\\d{10}", message = "Telephone must be 10 digits")
    private String telephone;

    private String ville;

    @Size(min = 15, max = 15, message = "ICE must be 15 characters")
    private String ICE;

    // Getters and Setters
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
