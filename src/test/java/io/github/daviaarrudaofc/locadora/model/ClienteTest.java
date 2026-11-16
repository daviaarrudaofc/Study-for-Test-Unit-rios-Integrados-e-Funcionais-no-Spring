package io.github.daviaarrudaofc.locadora.model;

import org.junit.jupiter.api.Assertions; // para pegar a classe em si, e depois o method delas -- chamar: Assertions.assertEquals(...)
import static org.junit.jupiter.api.Assertions.*; // pega já diretamente os methods --chamar: assertEquals(...)
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ClienteTest {

     @Test
     void deveCriarClienteComNome(){
         //1. Cenário
         Cliente cliente = new Cliente("Maria");

         //2. Execução
         String nome = cliente.getNome();


         //3. Verificação
         assertNotNull(nome);

         assertThat(nome).isEqualTo("Maria");
         assertThat(nome).isLessThan("Maria5");


         assertTrue(nome.startsWith("M"));
         assertFalse(nome.length() ==100);

         assertThat(nome.length()).isLessThan(100);

         assertThat(nome).contains("Ma");
     }

     @Test
    void deveCriarClienteSemNome(){
         var cliente = new Cliente(null);

         var nome = cliente.getNome();

         assertNull(nome);
     }
}
