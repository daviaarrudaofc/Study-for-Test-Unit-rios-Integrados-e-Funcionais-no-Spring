package io.github.daviaarrudaofc.locadora.service;

import io.github.daviaarrudaofc.locadora.entities.CarroEntity;
import io.github.daviaarrudaofc.locadora.exceptions.EntityNotFoundException;
import io.github.daviaarrudaofc.locadora.repository.CarroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CarroServiceTest {

    @InjectMocks
    CarroService carroService; //Mockito cria o serviço real e entrega a ele o repositório simulado.

    @Mock // Simula o repositório para testar o serviço sem acessar o banco
    CarroRepository carroRepository;

    @Test
    void deveSalvarUmCarro(){
        CarroEntity carroParaSalvar = new CarroEntity("Sedan", 10.0,2027);

        CarroEntity carroParaRetornar = new CarroEntity("Sedan", 10.0,2027);
        carroParaRetornar.setId(1L);

        Mockito.when( carroRepository.save(Mockito.any()) ).thenReturn(carroParaRetornar);//tanto faz o paramentro que eu passar aqui
                                                                        // eu quero que ele retorne esse carro,coringa

        var carroSalvo = carroService.salvar(carroParaSalvar);

        assertNotNull(carroSalvo);
        assertEquals("Sedan",carroSalvo.getModelo());

        Mockito.verify(carroRepository).save(Mockito.any());
    }

    @Test
    void deveDarErroAoTentarSalvarCarroComDiariaNegativa(){
        // 1. Cenário: criamos um carro com diária inválida.
        CarroEntity carro = new CarroEntity("Sedan", -10,2027);

        // 2. Execução: chamamos o serviço e capturamos a exceção.
        // Se não houver exceção, "erro" será null.
        var erro = catchThrowable( () -> carroService.salvar(carro) );

        // 3. Resultado: a regra do serviço deve rejeitar o carro.
        assertThat(erro).isInstanceOf(IllegalArgumentException.class);

        // 4. Interação: como o carro foi rejeitado, save() não pode ter sido chamado.
        Mockito.verify(carroRepository, Mockito.never()).save(Mockito.any());

    }

    @Test
    void deveAtualizarUmCarro(){
        //1. Cenário
        var carroExistente = new CarroEntity("Gol", 80.0,2026);
        Mockito.when(carroRepository.findById(1L)).thenReturn(Optional.of(carroExistente));

        var carroAtualizado = new CarroEntity("Gol", 80.0,2026);
        carroAtualizado.setId(1L);
        Mockito.when(carroRepository.save(Mockito.any())).thenReturn(carroAtualizado);

        Long id = 1L;
        var carro = new CarroEntity("Sedan", 10.0,2027);

        var resultado = carroService.atualizar(id,carro);

        assertEquals(resultado.getModelo(), "Gol");

        Mockito.verify(carroRepository, Mockito.times(1)).save(Mockito.any());

    }

    @Test
    void deveDarErroAoTentarAtualizarCarroInesxistente(){
        Long id = 1L;
        var carro = new CarroEntity("Sedan", 10.0,2027);

        Mockito.when(carroRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        var erro = catchThrowable(() -> carroService.atualizar(id, carro));

        assertThat(erro).isInstanceOf(EntityNotFoundException.class);

        Mockito.verify(carroRepository, Mockito.never()).save(Mockito.any());
    }

}