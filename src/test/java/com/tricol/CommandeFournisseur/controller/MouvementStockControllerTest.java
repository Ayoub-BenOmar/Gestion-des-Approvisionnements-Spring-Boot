package com.tricol.CommandeFournisseur.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tricol.CommandeFournisseur.model.dto.MouvementStockDto;
import com.tricol.CommandeFournisseur.model.entities.CommandeFournisseur;
import com.tricol.CommandeFournisseur.model.entities.Fournisseur;
import com.tricol.CommandeFournisseur.model.entities.MouvementStock;
import com.tricol.CommandeFournisseur.model.entities.Produit;
import com.tricol.CommandeFournisseur.model.enums.TypeMouvement;
import com.tricol.CommandeFournisseur.model.mapper.MouvementStockMapper;
import com.tricol.CommandeFournisseur.service.MouvementStockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class MouvementStockControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MouvementStockService mouvementStockService;

    @Mock
    private MouvementStockMapper mouvementStockMapper;

    @InjectMocks
    private MouvementStockController mouvementStockController;

    private List<MouvementStock> mouvements;
    private List<MouvementStockDto> mouvementsDto;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(mouvementStockController).build();

        MouvementStock m1 = MouvementStock.builder()
                .id(1)
                .commande(CommandeFournisseur.builder().id(100).build())
                .produit(Produit.builder().id(10).build())
                .fournisseur(Fournisseur.builder().id(1).build())
                .quantite(5.0)
                .typeMouvement(TypeMouvement.ENTREE)
                .dateMouvement(LocalDate.now())
                .build();

        MouvementStock m2 = MouvementStock.builder()
                .id(2)
                .commande(CommandeFournisseur.builder().id(101).build())
                .produit(Produit.builder().id(11).build())
                .fournisseur(Fournisseur.builder().id(1).build())
                .quantite(3.0)
                .typeMouvement(TypeMouvement.SORTIE)
                .dateMouvement(LocalDate.now())
                .build();

        mouvements = List.of(m1, m2);

        MouvementStockDto d1 = MouvementStockDto.builder()
                .id(1)
                .commandeId(100)
                .produitId(10)
                .fournisseurId(1)
                .quantite(5.0)
                .typeMouvement(TypeMouvement.ENTREE)
                .dateMouvement(m1.getDateMouvement())
                .build();

        MouvementStockDto d2 = MouvementStockDto.builder()
                .id(2)
                .commandeId(101)
                .produitId(11)
                .fournisseurId(1)
                .quantite(3.0)
                .typeMouvement(TypeMouvement.SORTIE)
                .dateMouvement(m2.getDateMouvement())
                .build();

        mouvementsDto = List.of(d1, d2);

        when(mouvementStockMapper.toDto(any(MouvementStock.class))).thenAnswer(invocation -> {
            MouvementStock src = invocation.getArgument(0);
            return MouvementStockDto.builder()
                    .id(src.getId())
                    .commandeId(src.getCommande() != null ? src.getCommande().getId() : null)
                    .produitId(src.getProduit() != null ? src.getProduit().getId() : null)
                    .fournisseurId(src.getFournisseur() != null ? src.getFournisseur().getId() : null)
                    .quantite(src.getQuantite())
                    .typeMouvement(src.getTypeMouvement())
                    .dateMouvement(src.getDateMouvement())
                    .build();
        });
    }

    @Test
    void testGetAllMouvements() throws Exception {
        when(mouvementStockService.findMouvements(null, null, null))
                .thenReturn(mouvements);

        mockMvc.perform(get("/api/mouvements")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(mouvements.size()))
                .andExpect(jsonPath("$[0].typeMouvement").value("ENTREE"))
                .andExpect(jsonPath("$[1].typeMouvement").value("SORTIE"));
    }

    @Test
    void testGetMouvementsByType() throws Exception {
        TypeMouvement filterType = TypeMouvement.ENTREE;

        when(mouvementStockService.findMouvements(null, filterType, null))
                .thenReturn(mouvements.stream().filter(m -> m.getTypeMouvement() == filterType).toList());

        mockMvc.perform(get("/api/mouvements")
                        .param("type", filterType.name())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].typeMouvement").value("ENTREE"));
    }

    @Test
    void testGetMouvementsByCommandeId() throws Exception {
        int filterCommandeId = 101;

        when(mouvementStockService.findMouvements(null, null, filterCommandeId))
                .thenReturn(mouvements.stream().filter(m -> m.getCommande().getId() == filterCommandeId).toList());

        mockMvc.perform(get("/api/mouvements")
                        .param("commandeId", String.valueOf(filterCommandeId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].commandeId").value(filterCommandeId));
    }
}
