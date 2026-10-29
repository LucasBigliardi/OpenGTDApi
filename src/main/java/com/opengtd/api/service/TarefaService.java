package com.opengtd.api.service;

import com.opengtd.api.model.Dashboard;
import com.opengtd.api.model.Tarefa;
import com.opengtd.api.model.TarefaResumo;
import com.opengtd.api.repository.TarefaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final PerfilService perfilService;

    public Tarefa criar(Tarefa tarefa) {
        if (tarefa.getTimestamp() == null) {
            tarefa.setTimestamp(Instant.now().getEpochSecond());
        }
        if (tarefa.getStatus() == null || tarefa.getStatus().isBlank()) {
            tarefa.setStatus("espera");
        }
        if (tarefa.getPeso() == null || tarefa.getPeso().isBlank()) {
            tarefa.setPeso("tranquila");
        }
        return tarefaRepository.save(tarefa);
    }

    public List<Tarefa> listarTodas() {
        return tarefaRepository.findAll();
    }

    public List<TarefaResumo> listarResumo() {
        return tarefaRepository.findAll().stream()
                .map(t -> new TarefaResumo(t.getId(), t.getTitulo(), t.getTimestamp(), t.getPeso()))
                .toList();
    }

    public Tarefa buscarPorId(Long id) {
        return tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa nao encontrada com o ID: " + id));
    }

    public Tarefa atualizar(Long id, Tarefa dadosAtualizados) {
        Tarefa tarefaExistente = buscarPorId(id);
        String statusAnterior = tarefaExistente.getStatus();

        if (dadosAtualizados.getTitulo() != null) {
            tarefaExistente.setTitulo(dadosAtualizados.getTitulo());
        }
        if (dadosAtualizados.getDescricao() != null) {
            tarefaExistente.setDescricao(dadosAtualizados.getDescricao());
        }
        if (dadosAtualizados.getPeso() != null) {
            tarefaExistente.setPeso(dadosAtualizados.getPeso());
        }
        if (dadosAtualizados.getTimestamp() != null) {
            tarefaExistente.setTimestamp(dadosAtualizados.getTimestamp());
        }
        if (dadosAtualizados.getStatus() != null) {
            tarefaExistente.setStatus(dadosAtualizados.getStatus());
        }
        if (dadosAtualizados.getTags() != null && !dadosAtualizados.getTags().isEmpty()) {
            tarefaExistente.setTags(dadosAtualizados.getTags());
        }

        Tarefa salva = tarefaRepository.save(tarefaExistente);

        if (dadosAtualizados.getStatus() != null && !dadosAtualizados.getStatus().equalsIgnoreCase(statusAnterior)) {
            perfilService.aplicarRegraDeXp(salva.getPeso(), salva.getStatus());
        }

        return salva;
    }

    public void deletar(Long id) {
        Tarefa tarefa = buscarPorId(id);
        tarefaRepository.delete(tarefa);
    }

    public Dashboard obterDashboard() {
        long total = tarefaRepository.count();
        long concluidas = tarefaRepository.countByStatusIgnoreCase("concluido");
        long emAndamento = tarefaRepository.countByStatusIgnoreCase("andamento");
        long emEspera = tarefaRepository.countByStatusIgnoreCase("espera");
        long fracassadas = tarefaRepository.countByStatusIgnoreCase("fracassado");

        return new Dashboard(total, concluidas, emAndamento, emEspera, fracassadas);
    }
}
