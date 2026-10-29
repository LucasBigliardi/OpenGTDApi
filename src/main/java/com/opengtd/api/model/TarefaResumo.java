package com.opengtd.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TarefaResumo {

    private Long id;
    private String titulo;
    private Long timestamp;
    private String peso;
}
