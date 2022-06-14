package com.sofka.crud.democrud.services;


import com.sofka.crud.democrud.models.UsuarioModel;
import com.sofka.crud.democrud.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Clase tipo Servicio para el manejo de Usuarios
 *
 * @author Ricardo Ortega <tattortega.28@gmail.com>
 * @version 1.0.0 2022-06-14
 * @since 1.0.0
 */
@Service
public class UsuarioService {
    /**
     * Repositorio de Usuario
     */
    @Autowired
    UsuarioRepository usuarioRepository;

    /**
     * Método para obtener todos los usuarios
     *
     * @return List
     */
    @Transactional(readOnly = true)
    public List<UsuarioModel> getUsers() {
        return (List<UsuarioModel>) usuarioRepository.findAll();
    }

    /**
     * Método para obtener usuario por ID
     *
     * @param id Long
     * @return Optional
     */
    @Transactional(readOnly = true)
    public Optional<UsuarioModel> getById(Long id) {
        return usuarioRepository.findById(id);
    }

    /**
     * Método para guardar un usuario
     *
     * @param usuario UsuarioModel
     * @return UsuarioModel
     */
    @Transactional
    public UsuarioModel saveUser(UsuarioModel usuario) {
        return usuarioRepository.save(usuario);
    }

    /**
     * Método para obtener usuarios por prioridad
     *
     * @param prioridad Integer
     * @return List
     */
    @Transactional(readOnly = true)
    public List<UsuarioModel> getByPriority(Integer prioridad) {
        return usuarioRepository.findByPrioridad(prioridad);
    }

    /**
     * Método para actualizar un usuario
     *
     * @param id      Long
     * @param usuario UsuarioModel
     * @return UsuarioModel
     */
    @Transactional
    public UsuarioModel updateUser(Long id, UsuarioModel usuario) {
        usuario.setId(id);
        return usuarioRepository.save(usuario);
    }

    /**
     * Método para eliminar un usuario
     *
     * @param id Long
     * @return boolean
     */
    @Transactional
    public boolean deleteUser(Long id) {
        try {
            usuarioRepository.deleteById(id);
            return true;
        } catch (Exception err) {
            return false;
        }
    }

}