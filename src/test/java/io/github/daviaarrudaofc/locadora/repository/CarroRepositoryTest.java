package io.github.daviaarrudaofc.locadora.repository;

import static org.junit.jupiter.api.Assertions.*;

import io.github.daviaarrudaofc.locadora.entities.CarroEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
class CarroRepositoryTest {

    @Autowired
    CarroRepository carroRepository;

    @Test
    void deveSalvarUmCarro(){
        var entity = new CarroEntity("Sedan", 100.0);

        carroRepository.save(entity);

        assertNotNull(entity.getId());
    }

}