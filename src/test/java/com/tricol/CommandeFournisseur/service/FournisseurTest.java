package com.tricol.CommandeFournisseur.service;

import com.tricol.CommandeFournisseur.model.dto.FournisseurDto;
import com.tricol.CommandeFournisseur.model.entities.Fournisseur;
import com.tricol.CommandeFournisseur.model.mapper.FournisseurMapper;
import com.tricol.CommandeFournisseur.repository.FournisseurRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class FournisseurTest {

    @Mock
    private FournisseurRepository fournisseurRepository;

    @Mock
    private FournisseurMapper fournisseurMapper;

    @InjectMocks
    private FournisseurService fournisseurService;

    @Test
    public void testSaveFournisseur() {
        FournisseurDto fournisseurDto = new FournisseurDto();
        fournisseurDto.setSociete("Test Fournisseur");
        fournisseurDto.setAdresse("123");
        fournisseurDto.setEmail("test@gmail.com");
        fournisseurDto.setTelephone("0123456789");
        fournisseurDto.setContact("test");
        fournisseurDto.setVille("agadir");
        fournisseurDto.setICE("123456");

        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setSociete("Test Fournisseur");
        fournisseur.setAdresse("123");
        fournisseur.setEmail("test@gmail.com");
        fournisseur.setTelephone("0123456789");
        fournisseur.setContact("test");
        fournisseur.setVille("Agadir");
        fournisseur.setICE("123456");

        when(fournisseurMapper.toEntity(fournisseurDto)).thenReturn(fournisseur);
        when(fournisseurRepository.save(fournisseur)).thenReturn(fournisseur);
        when(fournisseurMapper.toDto(fournisseur)).thenReturn(fournisseurDto);

        FournisseurDto savedDto = fournisseurService.save(fournisseurDto);

        assertNotNull(savedDto);
        assertEquals("Test Fournisseur", savedDto.getSociete());
        verify(fournisseurRepository, times(1)).save(fournisseur);
    }

    @Test
    public void testUpdateFournisseur() {
        FournisseurDto fournisseurDto = new FournisseurDto();
        fournisseurDto.setId(1);
        fournisseurDto.setSociete("Updated Fournisseur");
        fournisseurDto.setAdresse("New Address");
        fournisseurDto.setEmail("new@email.com");
        fournisseurDto.setTelephone("9999999999");
        fournisseurDto.setContact("1230");
        fournisseurDto.setVille("New Ville");
        fournisseurDto.setICE("123");

        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setId(1);
        fournisseur.setSociete("Updated Fournisseur");
        fournisseur.setAdresse("New Address");
        fournisseur.setEmail("new@email.com");
        fournisseur.setTelephone("9999999999");
        fournisseur.setContact("New Contact");
        fournisseur.setVille("New Ville");
        fournisseur.setICE("123");

        when(fournisseurMapper.toEntity(fournisseurDto)).thenReturn(fournisseur);
        when(fournisseurRepository.save(fournisseur)).thenReturn(fournisseur);
        when(fournisseurMapper.toDto(fournisseur)).thenReturn(fournisseurDto);

        FournisseurDto result = fournisseurService.update(fournisseurDto);

        assertNotNull(result);
        assertEquals("Updated Fournisseur", result.getSociete());
        assertEquals("new@email.com", result.getEmail());
        verify(fournisseurMapper, times(1)).toEntity(fournisseurDto);
        verify(fournisseurRepository, times(1)).save(fournisseur);
        verify(fournisseurMapper, times(1)).toDto(fournisseur);
    }


}
