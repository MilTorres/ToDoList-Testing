$(document).ready(function () {
});

async function registrartarea() {
    let datos = {};

    // Se obtiene el valor de descripcion y se inserta en datos para posteriormente mandarlo en el end point
    datos.description = document.getElementById('txtdescripcion').value;

    // Se obtiene el estado de la tarea como booleano
    datos.completed = document.getElementById('taskStatus').value === 'true';

    if (!datos.description) {
        alert('El campo descripcion debe estar lleno');
        return;
    }



    // Realizar la solicitud POST
    const request = await fetch('/registrartareas', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(datos) // Convierte el objeto a JSON
    });

    if (request.ok) {
        alert("La tarea fue registrada con éxito");
        window.location.href = 'nuevatarea.html'; // Cambiar a la página de nuevastareas
    } else {
        alert("Error al registrar la tarea.");
    }
}
