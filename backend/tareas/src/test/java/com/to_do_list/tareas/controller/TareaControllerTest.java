package com.to_do_list.tareas.controller;

import com.to_do_list.tareas.assembler.TareaModelAssembler;
import com.to_do_list.tareas.dto.TareaRequestDTO;
import com.to_do_list.tareas.dto.TareaResponseDTO;
import com.to_do_list.tareas.service.TareaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import({TareaModelAssembler.class})
public class TareaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TareaService tareaService;

    private TareaResponseDTO tareaResponseDTO;
    private TareaRequestDTO tareaRequestDTO;

    @BeforeEach
    void setUp(){
        tareaResponseDTO = new TareaResponseDTO();
        tareaResponseDTO.setId(1L);
        tareaResponseDTO.setNombre("Estudiar");
        tareaResponseDTO.setHoraInicio(LocalTime.of(10,0));
        tareaResponseDTO.setHoraFinal(LocalTime.of(12,0));

        tareaRequestDTO = new TareaRequestDTO();
        tareaRequestDTO.setNombre("Estudiar");
        tareaRequestDTO.setHoraInicio(LocalTime.of(10,0));
        tareaRequestDTO.setHoraTermino(LocalTime.of(12,0));

    }

    @Test
    public void testObtenerTodo() throws Exception{
        when(tareaService.obtenerTodo()).thenReturn(List.of(tareaResponseDTO));

        mockMvc.perform(get("/api/tareas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.tareaResponseDTOList[0].id").value(1L))
                .andExpect(jsonPath("$._embedded.tareaResponseDTOList[0].nombre").value("Estudiar"));
    }

    @Test
    public void testAgregarTarea() throws Exception{
        when(tareaService.agregarTarea(any(TareaRequestDTO.class))).thenReturn(tareaResponseDTO);

        mockMvc.perform(post("/api/tareas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tareaRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Estudiar"));
    }

    @Test
    public void testModificarTarea() throws Exception{
        when(tareaService.modificarTarea(any(TareaRequestDTO.class), (eq(1L)))).thenReturn(tareaResponseDTO);

        mockMvc.perform(put("/api/tareas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tareaRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Estudiar"));
    }

    @Test
    public void testEliminarJuego() throws Exception{
        doNothing().when(tareaService).eliminarTarea(1L);

        mockMvc.perform(delete("/api/tareas/1"))
                .andExpect(status().isNoContent());

        verify(tareaService, times(1)).eliminarTarea(1L);

    }

    @Test
    public void testCambiarEstado() throws Exception{
        tareaResponseDTO.setEstado("COMPLETADO");
        when(tareaService.cambiarEstado(1L)).thenReturn(tareaResponseDTO);

        mockMvc.perform(patch("/api/tareas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Estudiar"))
                .andExpect(jsonPath("$.estado").value("COMPLETADO"));

        verify(tareaService).cambiarEstado(1L);

    }
}
