package com.to_do_list.tareas.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tarea {

    @NotNull(message = "El id no puede ser nulo")
    private Long id;

    @NotBlank(message = "El nombre no puede ser nulo ni vacio")
    private String nombre;

    @NotNull(message = "Se tiene que asignar una hora de inicio")
    private LocalTime hora_inicio;

    @NotNull(message = "Se tiene que asignar una hora de termino")
    private LocalTime hora_termino;

    @NotNull(message = "Se tiene que asignar un estado")
    private String estado;

}
