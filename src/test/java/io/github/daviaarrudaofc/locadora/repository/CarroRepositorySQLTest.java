package io.github.daviaarrudaofc.locadora.repository;


import io.github.daviaarrudaofc.locadora.entities.CarroEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
public class CarroRepositorySQLTest {

    @Autowired
    CarroRepository carroRepository;

    @Test
    @Sql("/sql/popular-carros.sql")
    void deveBuscarCarroPorModelo(){
        List<CarroEntity> lista = carroRepository.findByModelo("SUV");

        var carro = lista.stream().findFirst().get();

        assertEquals(1, lista.size());

        assertThat(carro.getValorDiaria()).isEqualTo(150.0);
        assertThat(carro.getModelo()).isEqualTo("SUV");


    }
}
