package com.sofka.crud.democrud.ControllerServiceTest;

import com.sofka.crud.democrud.repositories.UsuarioRepository;
import com.sofka.crud.democrud.services.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test Mock Usuario
 *
 * @author Edgar Morillo <edgar.morillo@sofka.com.co>
 * @version 1.0.0 2022-06-08
 * @since 1.0.0
 */
@SpringBootTest
class UsuarioServiceMockTest {

    @MockBean
    UsuarioRepository usuarioRepository;

    @Autowired
    UsuarioService usuarioService;

    @Test
    void testUsuarioMock() {
        when(usuarioRepository.findAll()).thenReturn(new ArrayList<>());
        assertThat(usuarioService.getUsers()).isEmpty();
        verify(usuarioRepository).findAll();
    }

}
