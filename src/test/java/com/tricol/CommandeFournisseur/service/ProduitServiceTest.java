package com.tricol.CommandeFournisseur.service;

import com.tricol.CommandeFournisseur.model.dto.ProduitDto;
import com.tricol.CommandeFournisseur.model.entities.Produit;
import com.tricol.CommandeFournisseur.model.mapper.ProduitMapper;
import com.tricol.CommandeFournisseur.repository.ProduitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProduitServiceTest {

    @Mock
    private ProduitRepository produitRepository;

    @Mock
    private ProduitMapper produitMapper;

    @Mock
    private MouvementStockService mouvementStockService;

    @InjectMocks
    private ProduitService produitService;

    @Test
    public void testSaveProduit() {
        ProduitDto dto = new ProduitDto();
        dto.setNom("test");
        dto.setPrixUnitaire(10.0);
        dto.setStock(5);

        Produit entity = new Produit();
        entity.setNom("test");
        entity.setPrixUnitaire(10.0);
        entity.setStock(5);

        when(produitMapper.toEntity(dto)).thenReturn(entity);
        when(produitRepository.save(entity)).thenReturn(entity);
        when(produitMapper.toDto(entity)).thenReturn(dto);

        ProduitDto savedDto = produitService.save(dto);

        assertNotNull(savedDto);
        assertEquals("test", savedDto.getNom());
        verify(produitRepository, times(1)).save(entity);
        verify(mouvementStockService, times(1)).createMouvementEntree(entity);
    }

    @Test
    public void testGetById() {
        Produit entity = new Produit();
        entity.setId(1);
        entity.setNom("test");

        ProduitDto dto = new ProduitDto();
        dto.setId(1);
        dto.setNom("test");

        when(produitRepository.findById(1)).thenReturn(Optional.of(entity));
        when(produitMapper.toDto(entity)).thenReturn(dto);

        Optional<ProduitDto> result = produitService.getById(1);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
        assertEquals("test", result.get().getNom());
    }
}
