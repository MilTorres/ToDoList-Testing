package com.matr.todolistmilton.repository;

import com.matr.todolistmilton.models.Tareas;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

/**
 * Implementación del repositorio TareasDao para realizar operaciones sobre la entidad Tareas.
 */
@Repository
@Transactional
@Tag(name = "Tareas", description = "Implementación de operaciones CRUD para las tareas.")
public class TareasDaoImplementacion implements TareasDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Operation(summary = "Registrar tarea", description = "Inserta una nueva tarea en la base de datos.")
    public void registrar(Tareas tareas) {
        entityManager.merge(tareas); // Inserta objeto tareas en la base de datos
    }

    @Override
    @Operation(summary = "Obtener tareas", description = "Devuelve una lista de todas las tareas registradas en la base de datos.")
    @Transactional
    public List<Tareas> getTareas() {
        String query = "FROM Tareas";
        TypedQuery<Tareas> typedQuery = entityManager.createQuery(query, Tareas.class);
        return typedQuery.getResultList();
    }

    @Override
    @Operation(summary = "Eliminar tarea", description = "Elimina una tarea de la base de datos utilizando su ID.")
    @Transactional
    public void deleteTarea(int rfid) {
        try {
            // Busca la tarea por el ID
            Tareas tarea = entityManager.createQuery("SELECT t FROM Tareas t WHERE t.id = :id", Tareas.class)
                    .setParameter("id", rfid)
                    .getSingleResult();

            // Elimina la tarea
            entityManager.remove(tarea);
        } catch (NoResultException e) {
            // Maneja el caso en que no se encuentra la tarea
            // Aquí puedes decidir si lanzar una excepción o simplemente ignorar el error.
        }
    }




    @Override
    @Operation(summary = "Obtener tarea por ID", description = "Devuelve una tarea específica según su ID.")
    @Transactional
    public Tareas obtenerTareaPorfId(int id) {
        String query = "FROM Tareas WHERE id = :id";
        TypedQuery<Tareas> typedQuery = entityManager.createQuery(query, Tareas.class);
        typedQuery.setParameter("id", id);
        return typedQuery.getSingleResult();
    }

    @Override
    @Operation(summary = "Actualizar tarea", description = "Actualiza los datos de una tarea existente en la base de datos.")
    @Transactional
    public void actualizar(int id, Tareas tareas) {
        // Encuentra la tarea existente
        Tareas tareasExistentes = entityManager.createQuery("FROM Tareas WHERE id = :id", Tareas.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con el id: " + id));

        // Actualiza los campos necesarios
        tareasExistentes.setDescription(tareas.getDescription());
        tareasExistentes.setCompleted(tareas.isCompleted());

        // La entidad se actualiza automáticamente gracias a la persistencia de JPA
    }

    @Override
    @Operation(summary = "Buscar tareas por estado de completado", description = "Devuelve una lista de tareas filtradas por su estado de completado.")
    @Transactional
    public List<Tareas> findByCompleted(Boolean completed) {
        String query = "FROM Tareas WHERE completed = :completed";
        TypedQuery<Tareas> typedQuery = entityManager.createQuery(query, Tareas.class);
        typedQuery.setParameter("completed", completed);
        return typedQuery.getResultList();
    }
}
