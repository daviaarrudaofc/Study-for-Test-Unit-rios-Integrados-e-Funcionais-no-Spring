package io.github.daviaarrudaofc.locadora.model;

import io.github.daviaarrudaofc.locadora.exceptions.ReservaInvalidaExcpetion;

public class Reserva {
    private int quantidadeDias;
    private Cliente cliente;
    private Carro carro;

    public Reserva(int quantidadeDias, Cliente cliente, Carro carro) {
        if(quantidadeDias<1){
            throw new ReservaInvalidaExcpetion
                    ("A Reserva não pode ter uma quantidade de dias menor que 1");
        }
        this.quantidadeDias = quantidadeDias;
        this.cliente = cliente;
        this.carro = carro;
    }

    public int getQuantidadeDias() {
        return quantidadeDias;
    }

    public void setQuantidadeDias(int quantidadeDias) {
        this.quantidadeDias = quantidadeDias;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public double calcularTotal(){
        return this.carro.calcularValorAluguel(this.quantidadeDias);
    }
}
