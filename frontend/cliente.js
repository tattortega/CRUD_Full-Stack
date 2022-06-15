//Definición de variables
const url = "http://localhost:8080/usuario/";
const tbody = document.querySelector('tbody')
let resultados = ''

const modalUsuario = new bootstrap.Modal(document.getElementById('modalUsuario'))
const modalUsuarioBuscar = new bootstrap.Modal(document.getElementById('modalUsuarioBuscar'))
const modalUsuarioEliminar = new bootstrap.Modal(document.getElementById('modalUsuarioEliminar'))
const formUsuario = document.querySelector('form')
const nombre = document.getElementById('nombre')
const email = document.getElementById('email')
const prioridad = document.getElementById('prioridad')
var opcion = ''

btnCrear.addEventListener('click', () => {
    nombre.value = ''
    email.value = ''
    prioridad.value = ''
    modalUsuario.show()
    opcion = 'crear'
})

//Función para mostrar los resultados
async function mostrar() {
    try {
        let res = await fetch(url)
        let json = await res.json();
        if (!res.ok) throw {status: res.status, statusText: res.statusText};

        json.data.forEach(usuario => {
            resultados += `<tr>
                            <td>${usuario.id}</td>
                            <td>${usuario.nombre}</td>
                            <td>${usuario.email}</td>
                            <td>${usuario.prioridad}</td>
                            <td class="text-center"><a class="btnEditar btn btn-primary">Editar</a><a class="btnBorrar btn btn-danger">Borrar</a></td>
                       </tr>
                    `
        })
        tbody.innerHTML = resultados;
    } catch (err) {
        let message = err.statusText || "Ocurrió un error";
        tbody.insertAdjacentHTML("afterend", `<p><b>Error ${err.status}: ${message}</b></p>`);
    }
}

document.addEventListener("DOMContentLoaded", mostrar)

const on = (element, event, selector, handler) => {
    element.addEventListener(event, e => {
        if (e.target.closest(selector)) {
            handler(e)
        }
    })
}

//Procedimiento Borrar
on(document, 'click', '.btnBorrar', e => {
    const fila = e.target.parentNode.parentNode
    const id = fila.firstElementChild.innerHTML
    alertify.confirm("¿Estás seguro de eliminar este usuario?",
        function () {
            fetch(url + id, {
                method: 'DELETE'
            })
                .then(res => res.json())
                .then(() => location.reload())
            alertify.success('Ok')
        },
        function () {
            alertify.error('Cancelado')
        })
})

//Procedimiento Editar
let idForm = 0
on(document, 'click', '.btnEditar', e => {
    const fila = e.target.parentNode.parentNode
    idForm = fila.children[0].innerHTML
    const nombreForm = fila.children[1].innerHTML
    const emailForm = fila.children[2].innerHTML
    const prioridadForm = fila.children[3].innerHTML
    nombre.value = nombreForm
    email.value = emailForm
    prioridad.value = prioridadForm
    opcion = 'editar'
    modalUsuario.show()

})

//Procedimiento para Crear y Editar
formUsuario.addEventListener('submit', (e) => {
    e.preventDefault()
    if (opcion === 'crear') {
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                nombre: nombre.value,
                email: email.value,
                prioridad: prioridad.value
            })
        })
            .then(response => response.json())
            .then(() => {
                location.reload()
                mostrar()
            })
    }
    if (opcion === 'editar') {
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: idForm,
                nombre: nombre.value,
                email: email.value,
                prioridad: prioridad.value
            })
        })
            .then(response => response.json())
            .then(response => location.reload())
    }
    modalUsuario.hide()
})

//Funcion para buscar usuario por email
async function buscar() {
    const emailBuscar = document.getElementById('emailBuscar').value
    let res = await fetch(`${url}query?email=${emailBuscar}`);
    let json = await res.json();
    if (json.data.length === 0) {
        alertify.error("El email no existe")
    } else {
        json.data.forEach(usuario => {
            resultados = `<tr>
                            <td>${usuario.id}</td>
                            <td>${usuario.nombre}</td>
                            <td>${usuario.email}</td>
                            <td>${usuario.prioridad}</td>
                            <td class="text-center"><a class="btnEditar btn btn-primary">Editar</a><a class="btnBorrar btn btn-danger">Borrar</a></td>
                       </tr>
                    `
        })
        tbody.innerHTML = resultados;
        modalUsuarioBuscar.hide()
    }
}

//Funcion para eliminar usuario por email
async function eliminar() {
    const emailEliminar = document.getElementById('emailEliminar').value
    let res = await fetch(`${url}query?email=${emailEliminar}`);
    let json = await res.json();
    if (json.data.length === 0) {
        alertify.error("El email no existe")
    } else {
        alertify.confirm("¿Estás seguro de eliminar este usuario?",
            function () {
                fetch(`${url}email/${emailEliminar}`, {
                    method: 'DELETE'
                })
                    .then(res => res.json())
                    .then(() => location.reload())
                alertify.success('Ok')
            },
            function () {
                alertify.error('Cancelado')
            })
        modalUsuarioEliminar.hide()
    }
}


