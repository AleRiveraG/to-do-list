package com.to_do_list.tareas.service;


import com.to_do_list.tareas.assembler.TareaModelAssembler;
import com.to_do_list.tareas.dto.TareaRequestDTO;
import com.to_do_list.tareas.dto.TareaResponseDTO;
import com.to_do_list.tareas.exception.TareaNotFoundException;
import com.to_do_list.tareas.model.Tarea;
import com.to_do_list.tareas.repository.TareaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TareaServiceTest {

    @Mock
    private TareaRepository tareaRepository;

    @Mock
    private TareaModelAssembler tareaModelAssembler;

    @InjectMocks
    private TareaService tareaService;
    private Tarea tarea;
    private TareaResponseDTO tareaResponseDTO;
    private TareaRequestDTO tareaRequestDTO;


    @BeforeEach
    void setUp(){
        tarea = new Tarea();
        tarea.setId(1L);
        tarea.setNombre("Estudiar");
        tarea.setHoraInicio(LocalTime.of(10,0));
        tarea.setHoraTermino(LocalTime.of(12,0));
        tarea.setEstado("PENDIENTE");

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
    @DisplayName("Listado de tareas")
    void testObtenerTodos(){
        when(tareaRepository.findAll()).thenReturn(List.of(tarea));
        when(tareaModelAssembler.toModel(any(Tarea.class))).thenReturn(tareaResponseDTO);

        List<TareaResponseDTO> tareas = tareaService.obtenerTodo();

        assertNotNull(tareas);
        assertEquals(1, tareas.size());
    }

    @Test
    @DisplayName("Agregar tarea")
    void testAgregarTarea(){
        TareaRequestDTO dto = new TareaRequestDTO();
        dto.setNombre(tarea.getNombre());
        dto.setHoraInicio(tarea.getHoraInicio());
        dto.setHoraTermino(tarea.getHoraTermino());

        when(tareaRepository.save(any(Tarea.class))).thenReturn(tarea);
        when(tareaModelAssembler.toModel(any(Tarea.class))).thenReturn(tareaResponseDTO);

        TareaResponseDTO tareaDTO = tareaService.agregarTarea(dto);
        tareaDTO.setEstado("PENDIENTE");

        assertNotNull(tareaDTO);
        assertEquals(tarea.getId(), tareaDTO.getId());
        assertEquals(tarea.getNombre(), tareaDTO.getNombre());
        assertEquals(tarea.getHoraInicio(), tareaDTO.getHoraInicio());
        assertEquals(tarea.getHoraTermino(), tareaDTO.getHoraFinal());
        assertEquals(tarea.getEstado(), tareaDTO.getEstado());

        verify(tareaRepository).save(any(Tarea.class));
    }

    @Test
    @DisplayName("Modificar tarea")
    void testModificarTarea(){
        Long id = 1L;
        Tarea tareaModificada = new Tarea(1L, "Trabajar", LocalTime.of(11,0), LocalTime.of(15,0),"PENDIENTE");
        TareaRequestDTO tareaRequestDTO = new TareaRequestDTO();
        tareaRequestDTO.setNombre(tareaModificada.getNombre());
        tareaRequestDTO.setHoraInicio(tareaModificada.getHoraInicio());
        tareaRequestDTO.setHoraTermino(tareaModificada.getHoraTermino());

        when(tareaRepository.findById(id)).thenReturn(Optional.of(tarea));
        when(tareaModelAssembler.toModel(any(Tarea.class))).thenReturn(tareaResponseDTO);

        when(tareaRepository.save(any(Tarea.class))).thenReturn(tareaModificada);

        TareaResponseDTO dto =  tareaService.modificarTarea(tareaRequestDTO, id);

        verify(tareaRepository).findById(id);
        verify(tareaRepository).save(tarea);

    }

    @Test
    @DisplayName("Debe lanzar una excepción cuando la tarea no exista")
    void testModificarTareaNotFound(){
        Long id = 1L;
        TareaRequestDTO dto = new TareaRequestDTO();
        dto.setNombre(tarea.getNombre());
        dto.setHoraInicio(tarea.getHoraInicio());
        dto.setHoraTermino(tarea.getHoraTermino());

        when(tareaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(TareaNotFoundException.class,
                () -> tareaService.modificarTarea(dto, id));

        verify(tareaRepository).findById(id);
        verify(tareaRepository, never()).save(any());
    }

    @Test
    @DisplayName("Eliminar tarea")
    void testEliminarTarea(){
        Long id = 1L;
        when(tareaRepository.findById(id)).thenReturn(Optional.of(tarea));

        tareaService.eliminarTarea(id);

        verify(tareaRepository).findById(id);
        verify(tareaRepository).deleteById(id);
    }

    @Test
    @DisplayName("Debe lanzar una excepción cuando la tarea no existe")
    void testEliminarTareaNotFound(){
        Long id = 1L;

        when(tareaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(TareaNotFoundException.class,
                () -> tareaService.eliminarTarea(id));

        verify(tareaRepository).findById(id);
        verify(tareaRepository, never()).deleteById(id);
    }



    @Test
    @DisplayName("Cambiar estados")
    void testCambiarEstado(){
        tareaResponseDTO.setEstado("COMPLETADO");
        Long id = 1L;
        when(tareaRepository.findById(id)).thenReturn(Optional.of(tarea));
        when(tareaRepository.save(any(Tarea.class))).thenReturn(tarea);
        when(tareaModelAssembler.toModel(any(Tarea.class))).thenReturn(tareaResponseDTO);

        TareaResponseDTO tareaResponseDTO = tareaService.cambiarEstado(id);

        assertEquals("COMPLETADO", tareaResponseDTO.getEstado());

        verify(tareaRepository).findById(id);
        verify(tareaRepository).save(tarea);
    }

    @Test
    @DisplayName("Debe lanzar una excepción cuando la tarea no existe")
    void testCambiarEstadoIdNotFound(){
        Long id = 1L;

        when(tareaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(TareaNotFoundException.class,
                () -> tareaService.cambiarEstado(id));

        verify(tareaRepository).findById(id);
        verify(tareaRepository, never()).save(any());
    }
}
