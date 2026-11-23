package io.github.daviaarrudaofc.locadora.service;

import io.github.daviaarrudaofc.locadora.entities.CarroEntity;
import io.github.daviaarrudaofc.locadora.model.Carro;
import io.github.daviaarrudaofc.locadora.repository.CarroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CarroServiceTest {

    @InjectMocks
    CarroService carroService; // so faz injetar a classe original

    @Mock // Simula o repositório para testar o serviço sem acessar o banco
    CarroRepository carroRepository;

    @Test
    void deveSalvarUmCarro(){

        Mockito
                .when(carroRepository.findById(1L))
                .thenReturn(Optional.of(new CarroEntity("TesteMock", 10.0, 2026)));

        Optional<CarroEntity> CarroEncantrado = carroRepository.findById(1L);
        System.out.println(CarroEncantrado.get().getModelo());
    }

}