package com.tricol.CommandeFournisseur.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tricol.CommandeFournisseur.model.dto.CommandeFournisseurDto;
import com.tricol.CommandeFournisseur.model.dto.ProduitCommandeDto;
import com.tricol.CommandeFournisseur.model.enums.StatutCommande;
import com.tricol.CommandeFournisseur.repository.FournisseurRepository;
import com.tricol.CommandeFournisseur.repository.ProduitRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class CommandeFournisseurControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @Autowired
    private ProduitRepository produitRepository;

    private Integer fournisseurId;

    @BeforeEach
    void setup() {
        var fournisseur = new com.tricol.CommandeFournisseur.model.entities.Fournisseur();
        fournisseur.setSociete("Test Fournisseur");
        fournisseur.setAdresse("Agadir");
        fournisseur.setContact("test");
        fournisseur.setEmail("test@example.com");
        fournisseur.setTelephone("0600000000");
        fournisseur.setVille("Agadir");
        fournisseur.setICE("ICE123456789123");

        fournisseurId = fournisseurRepository.save(fournisseur).getId();
    }

    private CommandeFournisseurDto createCommande() throws Exception {
        CommandeFournisseurDto dto = CommandeFournisseurDto.builder()
                .dateCommande(LocalDate.now())
                .statut(StatutCommande.EN_ATTENTE)
                .fournisseurId(fournisseurId)
                .produits(List.of())
                .build();

        String response = mockMvc.perform(post("/api/commande")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        return objectMapper.readValue(response, CommandeFournisseurDto.class);
    }

    @Test
    void testCreateCommande() throws Exception {
        CommandeFournisseurDto created = createCommande();

        mockMvc.perform(get("/api/commande/{id}", created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statut").value("EN_ATTENTE"))
                .andExpect(jsonPath("$.fournisseurId").value(fournisseurId));
    }

    @Test
    void testGetAllCommandes() throws Exception {
        for (int i = 0; i < 3; i++) {
            createCommande();
        }

        mockMvc.perform(get("/api/commande")
                        .param("page", "0")
                        .param("size", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(3));
    }

    @Test
    void testUpdateStatus() throws Exception {
        CommandeFournisseurDto created = createCommande();

        mockMvc.perform(put("/api/commande/{id}/status", created.getId())
                        .param("statut", "VALIDEE"))
                .andExpect(status().isOk())
                .andExpect(content().string("Commande updated."));
    }

    @Test
    void testDeleteCommande() throws Exception {
        CommandeFournisseurDto created = createCommande();

        mockMvc.perform(delete("/api/commande/{id}", created.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Commande deleted."));

        mockMvc.perform(get("/api/commande/{id}", created.getId()))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Commande not found"));
    }
}
