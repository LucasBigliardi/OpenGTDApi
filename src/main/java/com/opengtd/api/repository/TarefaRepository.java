package com.opengtd.api.repository;

import com.opengtd.api.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    long countByStatusIgnoreCase(String status);

    List<Tarefa> findByStatusIgnoreCase(String status);
}
