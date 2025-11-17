package com.tricol.CommandeFournisseur.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tricol.CommandeFournisseur.model.dto.ProduitDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class ProduitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateProduit() throws Exception {
        ProduitDto dto = new ProduitDto(
                null,
                "Pc",
                "Pc gamer",
                8000.0,
                "test",
                10.0,
                0.0
        );

        mockMvc.perform(post("/api/produits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nom").value("Pc"))
                .andExpect(jsonPath("$.categorie").value("test"))
                .andExpect(jsonPath("$.stock").value(10.0));
    }

    @Test
    void testGetProduitById() throws Exception {
        ProduitDto dto = new ProduitDto(
                null,
                "Pc",
                "test",
                4000.0,
                "test",
                20.0,
                0.0
        );

        String response = mockMvc.perform(post("/api/produits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        ProduitDto created = objectMapper.readValue(response, ProduitDto.class);

        mockMvc.perform(get("/api/produits/" + created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Pc"))
                .andExpect(jsonPath("$.categorie").value("test"))
                .andExpect(jsonPath("$.stock").value(20.0));
    }

    @Test
    void testGetAllProduits() throws Exception {
        for (int i = 1; i <= 3; i++) {
            ProduitDto dto = new ProduitDto(
                    null,
                    "Produit " + i,
                    "Description " + i,
                    100.0 * i,
                    "Categorie " + i,
                    5.0 * i,
                    0.0
            );

            mockMvc.perform(post("/api/produits")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isCreated());
        }

        mockMvc.perform(get("/api/produits")
                        .param("page", "0")
                        .param("size", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(3))
                .andExpect(jsonPath("$.content[0].nom").value("Produit 1"));
    }


    @Test
    void testUpdateProduit() throws Exception {
        ProduitDto dto = new ProduitDto(
                null, "PC", "HP", 7000.0, "Informatique", 5.0, 0.0
        );

        String response = mockMvc.perform(post("/api/produits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        ProduitDto created = objectMapper.readValue(response, ProduitDto.class);

        created.setNom("PC Gamer");
        created.setPrixUnitaire(9500);
        created.setStock(7);

        mockMvc.perform(put("/api/produits/" + created.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(created)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("PC Gamer"))
                .andExpect(jsonPath("$.prixUnitaire").value(9500.0))
                .andExpect(jsonPath("$.stock").value(7.0));
    }

    @Test
    void testDeleteProduit() throws Exception {
        ProduitDto dto = new ProduitDto(
                null, "Clavier", "Logitech", 300.0, "Informatique", 15.0, 0.0
        );

        String response = mockMvc.perform(post("/api/produits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        ProduitDto created = objectMapper.readValue(response, ProduitDto.class);

        mockMvc.perform(delete("/api/produits/" + created.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/produits/" + created.getId()))
                .andExpect(status().isNotFound());
    }
}
