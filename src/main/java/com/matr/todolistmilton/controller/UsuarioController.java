package com.matr.todolistmilton.controller;

import com.matr.todolistmilton.models.Tareas;
import com.matr.todolistmilton.repository.TareasDao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/") // Mi Prefijo para todos los endpoints
public class UsuarioController {

    @Autowired
    private TareasDao tareasDao;

    // Endpoint de prueba para verificar la conexión
    @GetMapping("/")
    @Operation(summary = "Verificar conexión", description = "Endpoint para verificar la conexión entre el front y el back.")
    public String c() {
        return "Conexión Exitosa Front y Back";
    }

    // Endpoint para registrar nuevas tareas
    @PostMapping(value = "/registrartareas")
    @Operation(summary = "Registrar tarea", description = "Registra una nueva tarea.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tarea registrada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<Void> registrarUsuarios(@RequestBody Tareas tareas) {
        tareasDao.registrar(tareas);
        return ResponseEntity.status(HttpStatus.CREATED).build(); // Retorna 201 Created
    }

    // Endpoint para obtener tareas
    @GetMapping("/tareas")
    @Operation(summary = "Obtener tareas", description = "Obtiene la lista de tareas. Puedes filtrar por estado completado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tareas obtenida exitosamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron tareas")
    })
    public ResponseEntity<List<Tareas>> obtenerTareas(@RequestParam(required = false) Boolean completed) {
        List<Tareas> tareas;
        if (completed != null) {
            tareas = tareasDao.findByCompleted(completed);
        } else {
            tareas = tareasDao.getTareas();
        }
        return ResponseEntity.ok(tareas);
    }

    // Endpoint para eliminar una tarea
    @DeleteMapping(value = "/elimartarea/{id}")
    @Operation(summary = "Eliminar tarea", description = "Elimina una tarea existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tarea eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Tarea no encontrada")
    })
    public ResponseEntity<Void> deleteTarea(@PathVariable("id") int id) {
        tareasDao.deleteTarea(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }

    // Endpoint para obtener una tarea por ID
    @GetMapping(value = "/editartarea/{id}")
    @Operation(summary = "Obtener tarea por ID", description = "Obtiene una tarea específica por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarea encontrada"),
            @ApiResponse(responseCode = "404", description = "Tarea no encontrada")
    })
    public ResponseEntity<Tareas> getUsuario(@PathVariable("id") int id) {
        Tareas tarea = tareasDao.obtenerTareaPorfId(id);
        if (tarea != null) {
            return ResponseEntity.ok(tarea);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Retorna 404 Not Found
        }
    }

    // Endpoint para actualizar una tarea
    @PatchMapping(value = "/actualizar/{id}")
    @Operation(summary = "Actualizar tarea", description = "Actualiza una tarea existente por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarea actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Tarea no encontrada"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    public ResponseEntity<Void> actualizarUsuario(@PathVariable("id") int id, @RequestBody Tareas tareas) {
        tareasDao.actualizar(id, tareas);
        return ResponseEntity.ok().build(); // Retorna 200 OK
    }
}
