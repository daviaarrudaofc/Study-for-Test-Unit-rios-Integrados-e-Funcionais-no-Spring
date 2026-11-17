package io.github.daviaarrudaofc.locadora.model;

import io.github.daviaarrudaofc.locadora.exceptions.ReservaInvalidaExcpetion;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


class ReservaTest {
 Cliente cliente;
 Carro carro;

 @BeforeEach
 void setUp(){
  cliente = new Cliente("José");
  carro = new Carro("Hatch", 50.0);
 }

  @Test
  void deveCriarUmaReserva(){
   //1. Cenário
   var dias = 5;

  //2. Execução
   var reserva = new Reserva(5, cliente, carro);

   //3. Verificação
   Assertions.assertThat(reserva).isNotNull();
  }

  @Test
 void deveDarErroAoCriarUmaRespostaComDiasNegativos(){
    //JUnit
    assertThrows(ReservaInvalidaExcpetion.class, () -> new Reserva(0, cliente, carro));
    assertDoesNotThrow(() -> new Reserva(1, cliente, carro));

    //AssertJ
   var erro = Assertions.catchThrowable(() -> new Reserva(0, cliente, carro));

   Assertions.assertThat(erro)
           .isInstanceOf(ReservaInvalidaExcpetion.class)
           .hasMessage("A Reserva não pode ter uma quantidade de dias menor que 1");

  }

  @Test
 void deveCalcularOTotalDoAluguel(){
    var reserva = new Reserva(3, cliente, carro);

    var total = reserva.calcularTotal();

    Assertions.assertThat(total).isEqualTo(150.0);
  }

}
