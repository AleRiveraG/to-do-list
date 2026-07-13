package com.to_do_list.tareas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TareaResponseDTO {

    private Long id;
    private String nombre;
    private String horaInicio;
    private String horaFinal;
    private String estado;
}
