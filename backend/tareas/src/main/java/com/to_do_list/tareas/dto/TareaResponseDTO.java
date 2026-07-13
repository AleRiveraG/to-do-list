package com.to_do_list.tareas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TareaResponseDTO {

    private Long id;
    private String nombre;
    private LocalTime horaInicio;
    private LocalTime horaFinal;
    private String estado;
}
