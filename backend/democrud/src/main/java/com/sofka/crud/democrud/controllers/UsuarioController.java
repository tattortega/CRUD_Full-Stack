package com.sofka.crud.democrud.controllers;

import com.sofka.crud.democrud.models.UsuarioModel;
import com.sofka.crud.democrud.services.UsuarioService;
import com.sofka.crud.democrud.utility.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

/**
 * Controlador para el Usuario
 *
 * @author Ricardo Ortega <tattortega.28@gmail.com>
 * @version 1.0.0 2022-06-14
 * @since 1.0.0
 */

@CrossOrigin
@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    /**
     * Servicio para el manejo del Usuario
     */
    @Autowired
    UsuarioService usuarioService;
    /**
     * Variable para el manejo de las respuestas de las API
     */
    private final Response response = new Response();
    /**
     * Manejo del código HTTP que se responde en las API
     */
    private HttpStatus httpStatus = HttpStatus.OK;

    /**
     * Administrador para las excepciones del sistema
     *
     * @param exception Objeto Exception
     * @author Ricardo Ortega <tattortega.28@gmail.com>
     * @since 1.0.0
     */
    private void getErrorMessageInternal(Exception exception) {
        response.error = true;
        response.message = exception.getMessage();
        response.data = exception.getCause();
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    /**
     * Administrador para las excepciones en SQL con respecto al manejo del acceso a los datos
     *
     * @param exception Objeto DataAccessException
     * @author Ricardo Ortega <tattortega.28@gmail.com>
     * @since 1.0.0
     */
    private void getErrorMessageForResponse(DataAccessException exception) {
        response.error = true;
        if (exception.getRootCause() instanceof SQLException) {
            SQLException sqlEx = (SQLException) exception.getRootCause();
            var sqlErrorCode = sqlEx.getErrorCode();
            switch (sqlErrorCode) {
                case 1062:
                    response.message = "El dato ya está registrado";
                    break;
                case 1452:
                    response.message = "El usuario indicado no existe";
                    break;
                default:
                    response.message = exception.getMessage();
                    response.data = exception.getCause();
            }
            httpStatus = HttpStatus.BAD_REQUEST;
        } else {
            response.message = exception.getMessage();
            response.data = exception.getCause();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    /**
     * Endpoint para obtener todos los usuarios
     *
     * @return Objeto Response en formato JSON
     */
    @GetMapping()
    public ResponseEntity<Response> getAllUsers() {
        response.restart();
        try {
            response.data = usuarioService.getUsers();
            response.message = "Lista de usuarios";
            httpStatus = HttpStatus.OK;
        } catch (Exception exception) {
            getErrorMessageInternal(exception);
        }
        return new ResponseEntity<>(response, httpStatus);

    }

    /**
     * Endpoint para obtener un usuario por ID
     *
     * @param id Long
     * @return Objeto Response en formato JSON
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<Response> getUserById(@PathVariable("id") Long id) {
        response.restart();
        try {
            response.data = usuarioService.getById(id);
            response.message = "Usuario encontrado";
            httpStatus = HttpStatus.OK;
        } catch (Exception exception) {
            getErrorMessageInternal(exception);
        }
        return new ResponseEntity<>(response, httpStatus);
    }

    /**
     * Endpoint para obtener usuarios por prioridad
     *
     * @param prioridad Integer
     * @return Objeto Response en formato JSON
     */
    @GetMapping("/query")
    public ResponseEntity<Response> getUserByPriority(@RequestParam("priority") Integer prioridad) {
        response.restart();
        try {
            response.data = usuarioService.getByPriority(prioridad);
            response.message = "Usuarios filtrados por prioridad: " + prioridad;
            httpStatus = HttpStatus.OK;
        } catch (DataAccessException exception) {
            getErrorMessageForResponse(exception);
        } catch (Exception exception) {
            getErrorMessageInternal(exception);
        }
        return new ResponseEntity<>(response, httpStatus);
    }

    /**
     * Endpoint para guardar usuarios
     *
     * @param usuario UsuarioModel
     * @return Objeto Response en formato JSON
     */
    @PostMapping()
    public ResponseEntity<Response> saveUser(@RequestBody UsuarioModel usuario) {
        response.restart();
        try {
            response.data = usuarioService.saveUser(usuario);
            response.message = "Usuario creado";
            httpStatus = HttpStatus.CREATED;
        } catch (DataAccessException exception) {
            getErrorMessageForResponse(exception);
        } catch (Exception exception) {
            getErrorMessageInternal(exception);
        }
        return new ResponseEntity<>(response, httpStatus);
    }

    /**
     * Endpoint para actualizar usuario
     *
     * @param usuario UsuarioModel
     * @param id      Long
     * @return Objeto Response en formato JSON
     */
    @PutMapping("/{id}")
    public ResponseEntity<Response> updateUser(@RequestBody UsuarioModel usuario, @PathVariable(value = "id") Long id) {
        response.restart();
        try {
            response.data = usuarioService.updateUser(id, usuario);
            response.message = "Usuario actualizado";
            httpStatus = HttpStatus.OK;
        } catch (DataAccessException exception) {
            getErrorMessageForResponse(exception);
        } catch (Exception exception) {
            getErrorMessageInternal(exception);
        }
        return new ResponseEntity<>(response, httpStatus);
    }

    /**
     * Endpoint para eliminar usuario
     *
     * @param id Long
     * @return Objeto Response en formato JSON
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Response> deleteUser(@PathVariable("id") Long id) {
        response.restart();
        try {
            response.data = usuarioService.deleteUser(id);
            if (response.data == null) {
                response.message = "El usuario no existe";
                httpStatus = HttpStatus.NOT_FOUND;
            } else {
                response.message = "El usuario fue eliminado exitosamente";
                httpStatus = HttpStatus.OK;
            }
        } catch (DataAccessException exception) {
            getErrorMessageForResponse(exception);
        } catch (Exception exception) {
            getErrorMessageInternal(exception);
        }
        return new ResponseEntity<>(response, httpStatus);
    }

}