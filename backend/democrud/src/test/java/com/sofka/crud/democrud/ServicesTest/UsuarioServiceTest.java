package com.sofka.crud.democrud.ServicesTest;

import com.sofka.crud.democrud.models.UsuarioModel;
import com.sofka.crud.democrud.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test Data JPA Usuario
 *
 * @author Ricardo Ortega <tattortega.28@gmail.com>
 * @version 1.0.0 2022-06-14
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UsuarioServiceTest {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Test
    void testSaveUser() {
        UsuarioModel usuarioModel = new UsuarioModel("mateo", "mateo@gmail.com", 70);
        UsuarioModel usuarioModelRegistrado = usuarioRepository.save(usuarioModel);
        assertNotNull(usuarioModelRegistrado);
    }

    @Test
    void testFindUserById() {
        Long idBuscado = 1L;
        Optional<UsuarioModel> usuarioModelBuscado = usuarioRepository.findById(idBuscado);
        assertThat(usuarioModelBuscado.get().getId()).isEqualTo(idBuscado);
    }

    @Test
    public void testFindUserByEmail(){
        UsuarioModel usuarioModel=new UsuarioModel("mateo", "mateo@gmail.com", 70);
        UsuarioModel usuarioModelRegistrado = usuarioRepository.save(usuarioModel);
        ArrayList<UsuarioModel> usuarioModelBuscado=usuarioRepository.findByEmail(usuarioModel.getEmail());
        assertThat(usuarioModelBuscado.get(0).getEmail()).isEqualTo(usuarioModel.getEmail());
    }
    @Test
    void testGetAllUsers() {
        List<UsuarioModel> usuarioModelList = (List<UsuarioModel>) usuarioRepository.findAll();
        assertThat(usuarioModelList).size().isGreaterThan(0);
    }
}
