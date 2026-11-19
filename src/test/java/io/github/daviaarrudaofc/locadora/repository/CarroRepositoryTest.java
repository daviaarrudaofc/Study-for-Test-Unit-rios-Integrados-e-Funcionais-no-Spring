package io.github.daviaarrudaofc.locadora.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import io.github.daviaarrudaofc.locadora.entities.CarroEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@ActiveProfiles("test")
@DataJpaTest
class CarroRepositoryTest {

    @Autowired
    CarroRepository carroRepository;

    CarroEntity carroEntity;

    @BeforeEach
    void setUp(){
        carroEntity =  new CarroEntity("Honda Civic", 200.0,2027);
    }

    @Test
    void deveSalvarUmCarro(){
        var entity = new CarroEntity("Sedan", 100.0, 2027);

        carroRepository.save(entity);

        assertNotNull(entity.getId());
    }

    @Test
    void deveBuscarCarroPorId(){
        var carroSalvo = carroRepository.save(carroEntity);

        Optional<CarroEntity> carroEncontrado = carroRepository.findById(carroSalvo.getId());

        assertThat(carroEncontrado).isPresent();
        assertThat(carroEncontrado.get().getModelo()).isEqualTo("Honda Civic");

    }

    @Test
    void deveAtualizarCarro(){
        var carroSalvo = carroRepository.save(carroEntity);

        carroSalvo.setAno(2028);

        var carroAtualizado = carroRepository.save(carroSalvo);

        assertThat(carroAtualizado.getAno()).isEqualTo(2028);
    }

    @Test
    void deveDeletarCarro(){
        var carroSalvo = carroRepository.save(carroEntity);

        carroRepository.deleteById(carroSalvo.getId());

        Optional<CarroEntity> carroEncontrado = carroRepository.findById(carroSalvo.getId());

        assertThat(carroEncontrado).isEmpty();


    }

}