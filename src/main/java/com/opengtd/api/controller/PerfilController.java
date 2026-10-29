package com.opengtd.api.controller;

import com.opengtd.api.model.Perfil;
import com.opengtd.api.service.PerfilService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/perfil")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PerfilController {

    private final PerfilService perfilService;

    @GetMapping
    public ResponseEntity<Perfil> obterPerfil() {
        return ResponseEntity.ok(perfilService.obterPerfil());
    }

    @PutMapping
    public ResponseEntity<Perfil> atualizarPerfil(@RequestBody Perfil perfil) {
        return ResponseEntity.ok(perfilService.atualizarPerfil(perfil));
    }
}
