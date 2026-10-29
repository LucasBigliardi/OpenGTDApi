package com.opengtd.api.controller;

import com.opengtd.api.model.Dashboard;
import com.opengtd.api.service.TarefaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class DashboardController {

    private final TarefaService tarefaService;

    @GetMapping
    public ResponseEntity<Dashboard> obterDashboard() {
        return ResponseEntity.ok(tarefaService.obterDashboard());
    }
}
