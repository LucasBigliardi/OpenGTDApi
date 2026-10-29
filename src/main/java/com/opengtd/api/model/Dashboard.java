package com.opengtd.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dashboard {

    private long tarefasTotais;
    private long tarefasConcluidas;
    private long tarefasEmAndamento;
    private long tarefasEmEspera;
    private long tarefasFracassadas;
}
