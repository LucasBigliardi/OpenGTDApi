package com.opengtd.api.service;

import com.opengtd.api.model.Perfil;
import com.opengtd.api.repository.PerfilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PerfilService {

    private final PerfilRepository perfilRepository;

    public Perfil obterPerfil() {
        return perfilRepository.findAll().stream().findFirst().orElseGet(() -> {
            Perfil padrao = new Perfil(null, "Yuri", 15, 12, 500, 300);
            return perfilRepository.save(padrao);
        });
    }

    public Perfil atualizarPerfil(Perfil dados) {
        Perfil perfil = obterPerfil();
        if (dados.getNome() != null) perfil.setNome(dados.getNome());
        if (dados.getNivel() != null) perfil.setNivel(dados.getNivel());
        if (dados.getStreak() != null) perfil.setStreak(dados.getStreak());
        if (dados.getXpAtual() != null) perfil.setXpAtual(dados.getXpAtual());
        if (dados.getXpProximoNivel() != null) perfil.setXpProximoNivel(dados.getXpProximoNivel());
        return perfilRepository.save(perfil);
    }

    public void aplicarRegraDeXp(String peso, String status) {
        if (status == null) return;

        Perfil perfil = obterPerfil();
        double pesoBase = obterMultiplicadorPeso(peso);
        double xpAtual = perfil.getXpAtual();
        double streak = perfil.getStreak();

        int variacaoXp = (int) Math.round(((xpAtual * pesoBase) * (streak / 100.0)) + (xpAtual * pesoBase));

        if ("concluido".equalsIgnoreCase(status)) {
            int novoXp = perfil.getXpAtual() + variacaoXp;
            int xpParaSubir = perfil.getXpAtual() + perfil.getXpProximoNivel();

            if (novoXp >= xpParaSubir) {
                perfil.setNivel(perfil.getNivel() + 1);
            }
            perfil.setXpAtual(novoXp);
            perfil.setStreak(perfil.getStreak() + 1);
            perfilRepository.save(perfil);
        } else if ("fracassado".equalsIgnoreCase(status)) {
            int novoXp = Math.max(0, perfil.getXpAtual() - variacaoXp);
            if (novoXp < perfil.getXpAtual() && perfil.getNivel() > 1) {
                perfil.setNivel(perfil.getNivel() - 1);
            }
            perfil.setXpAtual(novoXp);
            perfil.setStreak(0);
            perfilRepository.save(perfil);
        }
    }

    private double obterMultiplicadorPeso(String peso) {
        if (peso == null) return 0.1;
        return switch (peso.toLowerCase()) {
            case "intensa" -> 0.3;
            case "complicada" -> 0.2;
            default -> 0.1; // "tranquila"
        };
    }
}
