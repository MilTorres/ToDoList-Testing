// Función para obtener las tareas desde el servidor
async function fetchTareas(filtro) {
    try {
        let url = '/tareas'; // URL base para obtener tareas la cual cambiara segun el filtro

        // Modificar la URL según el filtro
        if (filtro === 'completadas') {
            url += '?completed=true'; // Obtener solo tareas completadas
        } else if (filtro === 'noCompletadas') {
            url += '?completed=false'; // Obtener solo tareas no completadas
        }

        const response = await fetch(url);
        if (!response.ok) {
            throw new Error('Error al obtener las tareas');
        }
        const tareas = await response.json();
        const tareasTableBody = document.getElementById('tareasTableBody');

        // Limpiar la tabla antes de agregar nuevas filas
        tareasTableBody.innerHTML = '';

        // Agregar cada tarea a la tabla
        tareas.forEach(tarea => {
            const row = document.createElement('tr');

            // Crear los botones de editar y eliminar que iran dentro de mi tabla en cada fila
            let btneditar = `
                <a href="#" onclick="editarTarea(${tarea.id})" class="btn btn-warning btn-circle btn-sm">
                    <i class="fas fa-edit"></i>
                </a>`;
            let btneliminar = `
                <a href="#" onclick="eliminarTarea(${tarea.id})" class="btn btn-danger btn-circle btn-sm">
                    <i class="fas fa-trash"></i>
                </a>`;

            row.innerHTML = `
                <td>${tarea.id}</td>
                <td>${tarea.description}</td>
                <td>${tarea.completed ? 'Sí' : 'No'}</td>
                <td>${btneditar}</td>
                <td>${btneliminar}</td>`;
            tareasTableBody.appendChild(row);
        });
    } catch (error) {
        console.error(error);
    }
}



// Llamar a la función al cargar la página
window.onload = fetchTareas;

async function eliminarTarea(id) {
    if (!confirm('Se eliminará la tarea de forma permanente \n ¿Estás seguro?')) {
        return;
    }

    const request = await fetch(`/elimartarea/${id}`, {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    });
    location.reload();
}

let tablaeditID;

function editarTarea(id) {
    // Guardar el ID de la tarea que se va a editar
    tablaeditID = id;

    // Obtener la tarea correspondiente al ID
    fetch(`/editartarea/${id}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al obtener la tarea');
            }
            return response.json();
        })
        .then(tarea => {
            // Rellenar el formulario del modal con los datos de la tarea
            document.getElementById('editId').value = tarea.id;
            document.getElementById('editDescription').value = tarea.description;
            document.getElementById('editCompleted').checked = tarea.completed;

            // Mostrar el modal
            $('#editUserModal').modal('show');
        })
        .catch(error => console.error(error));
}

function guardarCambios() {
    const tareaActualizada = {
        id: document.getElementById('editId').value,
        description: document.getElementById('editDescription').value,
        completed: document.getElementById('editCompleted').checked
    };



    // Enviar la solicitud para actualizar la tarea
    fetch(`/actualizar/${tablaeditID}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(tareaActualizada)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al actualizar la tarea');
            }
            // Cerrar el modal
            $('#editUserModal').modal('hide');
            // Actualizar la tabla
            fetchTareas();
        })
        .catch(error => console.error(error));
}
