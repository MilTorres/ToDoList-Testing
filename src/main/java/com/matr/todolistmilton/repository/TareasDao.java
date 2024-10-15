package com.matr.todolistmilton.repository;

import com.matr.todolistmilton.models.Tareas;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

/**
 * Interfaz que define las operaciones CRUD para la entidad Tareas.
 */
@Tag(name = "Tareas", description = "Operaciones relacionadas con las tareas en el sistema.")
public interface TareasDao {

    /**
     * Registra una nueva tarea en el sistema.
     *
     * @param tareas La tarea a registrar.
     */
    @Operation(summary = "Registrar tarea", description = "Registra una nueva tarea en el sistema.")
    void registrar(Tareas tareas);

    /**
     * Obtiene la lista de todas las tareas.
     *
     * @return Lista de tareas.
     */
    @Operation(summary = "Obtener tareas", description = "Obtiene todas las tareas registradas en el sistema.")
    List<Tareas> getTareas();

    /**
     * Elimina una tarea por su ID.
     *
     * @param rfid ID de la tarea a eliminar.
     */
    @Operation(summary = "Eliminar tarea", description = "Elimina una tarea del sistema.")
    void deleteTarea(int rfid);

    /**
     * Obtiene una tarea por su ID.
     *
     * @param id ID de la tarea a obtener.
     * @return La tarea correspondiente al ID, o null si no se encuentra.
     */
    @Operation(summary = "Obtener tarea por ID", description = "Obtiene una tarea específica por su ID.")
    Tareas obtenerTareaPorfId(int id);

    /**
     * Actualiza una tarea existente.
     *
     * @param id ID de la tarea a actualizar.
     * @param tareas Objeto Tareas que contiene los nuevos datos.
     */
    @Operation(summary = "Actualizar tarea", description = "Actualiza una tarea existente en el sistema.")
    void actualizar(int id, Tareas tareas);

    /**
     * Busca tareas según su estado de completado.
     *
     * @param completed Estado de completado para filtrar las tareas.
     * @return Lista de tareas que coinciden con el estado de completado.
     */
    @Operation(summary = "Buscar tareas por estado de completado", description = "Obtiene tareas filtradas por su estado de completado.")
    List<Tareas> findByCompleted(Boolean completed);
}
