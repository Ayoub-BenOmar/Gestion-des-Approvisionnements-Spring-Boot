package com.tricol.CommandeFournisseur.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tricol.CommandeFournisseur.model.dto.FournisseurDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FournisseurControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateFournisseur() throws Exception {
        FournisseurDto dto = new FournisseurDto();
        dto.setSociete("Create test");
        dto.setAdresse("test");
        dto.setContact("test");
        dto.setEmail("test@example.com");
        dto.setTelephone("0600000000");
        dto.setVille("Agadir");
        dto.setICE("ICE123456123456");

        mockMvc.perform(post("/api/fournisseurs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.societe").value("Create test"))
                .andExpect(jsonPath("$.ville").value("Agadir"));
    }

    @Test
    void testGetFournisseurById() throws Exception {

        FournisseurDto dto = new FournisseurDto();
        dto.setSociete("Find test");
        dto.setAdresse("test");
        dto.setContact("test");
        dto.setEmail("test@example.com");
        dto.setTelephone("0700000000");
        dto.setVille("Agadir");
        dto.setICE("ICE123456123456");

        String response = mockMvc.perform(post("/api/fournisseurs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        FournisseurDto created = objectMapper.readValue(response, FournisseurDto.class);

        mockMvc.perform(get("/api/fournisseurs/{id}", created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.societe").value("Find test"));
    }

    @Test
    void testUpdateFournisseur() throws Exception {

        FournisseurDto dto = new FournisseurDto();
        dto.setSociete("Update test");
        dto.setAdresse("test");
        dto.setContact("test");
        dto.setEmail("test@example.com");
        dto.setTelephone("0500000000");
        dto.setVille("Rabat");
        dto.setICE("ICE123456123456");

        String response = mockMvc.perform(post("/api/fournisseurs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        FournisseurDto created = objectMapper.readValue(response, FournisseurDto.class);

        created.setSociete("Updated test");

        mockMvc.perform(put("/api/fournisseurs/{id}", created.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(created)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.societe").value("Updated test"));
    }

    @Test
    void testDeleteFournisseur() throws Exception {
        FournisseurDto dto = new FournisseurDto();
        dto.setSociete("Delete test");
        dto.setAdresse("test");
        dto.setContact("test");
        dto.setEmail("test@example.com");
        dto.setTelephone("0666666666");
        dto.setVille("Marrakech");
        dto.setICE("ICE123456123456");

        String response = mockMvc.perform(post("/api/fournisseurs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        FournisseurDto created = objectMapper.readValue(response, FournisseurDto.class);

        mockMvc.perform(delete("/api/fournisseurs/{id}", created.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/fournisseurs/{id}", created.getId()))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Fournisseur wih the id: " + created.getId() + "not found."));
    }
}
