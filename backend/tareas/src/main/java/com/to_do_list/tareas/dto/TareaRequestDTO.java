package com.to_do_list.tareas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TareaRequestDTO {

    @NotBlank(message = "El nombre no puede estar vacio ni nulo")
    private String nombre;

    @NotNull(message = "Se debe asignar una hora de inicio")
    private LocalTime horaInicio;

    @NotNull(message = "Se debe asignar una hora de termino")
    private LocalTime horaTermino;
}
