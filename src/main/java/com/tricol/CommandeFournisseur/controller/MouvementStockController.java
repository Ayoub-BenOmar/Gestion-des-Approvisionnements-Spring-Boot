package com.tricol.CommandeFournisseur.controller;

import com.tricol.CommandeFournisseur.model.dto.MouvementStockDto;
import com.tricol.CommandeFournisseur.model.entities.MouvementStock;
import com.tricol.CommandeFournisseur.model.enums.TypeMouvement;
import com.tricol.CommandeFournisseur.model.mapper.MouvementStockMapper;
import com.tricol.CommandeFournisseur.service.MouvementStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/mouvements")
@RequiredArgsConstructor
public class MouvementStockController {
    private final MouvementStockService mouvementStockService;
    private final MouvementStockMapper mouvementStockMapper;

    @GetMapping
    public List<MouvementStockDto> findMouvements(@RequestParam(required = false) Integer produitId, @RequestParam(required = false) TypeMouvement type, @RequestParam(required = false) Integer commandeId) {
        List<MouvementStock> mouvements = mouvementStockService.findMouvements(produitId, type, commandeId);
        return mouvements.stream().map(mouvementStockMapper::toDto).collect(Collectors.toList());
    }
}

