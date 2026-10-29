package com.opengtd.api.controller;

import com.opengtd.api.model.Tarefa;
import com.opengtd.api.model.TarefaResumo;
import com.opengtd.api.service.TarefaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TarefaController {

    private final TarefaService tarefaService;

    @PostMapping
    public ResponseEntity<Tarefa> criar(@RequestBody Tarefa tarefa) {
        Tarefa novaTarefa = tarefaService.criar(tarefa);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaTarefa);
    }

    @GetMapping
    public ResponseEntity<List<Tarefa>> listarTodas() {
        return ResponseEntity.ok(tarefaService.listarTodas());
    }

    @GetMapping("/resumo")
    public ResponseEntity<List<TarefaResumo>> listarResumo() {
        return ResponseEntity.ok(tarefaService.listarResumo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tarefaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizar(@PathVariable Long id, @RequestBody Tarefa tarefa) {
        return ResponseEntity.ok(tarefaService.atualizar(id, tarefa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        tarefaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
