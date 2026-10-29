package com.opengtd.api;

import com.opengtd.api.model.Perfil;
import com.opengtd.api.model.Tarefa;
import com.opengtd.api.repository.PerfilRepository;
import com.opengtd.api.repository.TarefaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class OpengtdApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpengtdApplication.class, args);
    }

    @Bean
    public CommandLineRunner carregarDadosIniciais(TarefaRepository tarefaRepository, PerfilRepository perfilRepository) {
        return args -> {
            if (perfilRepository.count() == 0) {
                perfilRepository.save(new Perfil(null, "Yuri", 15, 12, 500, 300));
            }

            if (tarefaRepository.count() == 0) {
                tarefaRepository.save(new Tarefa(
                        null,
                        "Lavar a Louca",
                        "Lavar a louca com esponja",
                        "intensa",
                        1291329012L,
                        "concluido",
                        List.of("cotidiano", "higiene", "organizacao")
                ));

                tarefaRepository.save(new Tarefa(
                        null,
                        "Gravar aula de Spring Boot",
                        "Gravar as aulas de Controller, Service e Repository da API GTD",
                        "complicada",
                        1700000000L,
                        "andamento",
                        List.of("curso", "backend", "java")
                ));
            }
        };
    }
}
