package com.sofka.crud.democrud.repositories;

import com.sofka.crud.democrud.models.UsuarioModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * Interfaz del repositorio de Entidad del Usuario
 *
 * @author Ricardo Ortega <tattortega.28@gmail.com>
 * @version 1.0.0 2022-06-14
 * @since 1.0.0
 */
@Repository
public interface UsuarioRepository extends CrudRepository<UsuarioModel, Long> {
    /**
     * Método para buscar por email
     *
     * @param email String
     * @return ArrayList
     */
    ArrayList<UsuarioModel> findByEmail(String email);



}