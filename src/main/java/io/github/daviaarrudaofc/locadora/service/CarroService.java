package io.github.daviaarrudaofc.locadora.service;

import io.github.daviaarrudaofc.locadora.entities.CarroEntity;
import io.github.daviaarrudaofc.locadora.exceptions.EntityNotFoundException;
import io.github.daviaarrudaofc.locadora.repository.CarroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarroService {

    private CarroRepository carroRepository;

    public CarroService(CarroRepository carroRepository) {
        this.carroRepository = carroRepository;
    }

    public CarroEntity salvar(CarroEntity carro){
        if(carro.getValorDiaria() <= 0){
            throw new  IllegalArgumentException("Preço da diária não pode ser negativo");
        }
        return carroRepository.save(carro);
    }

    public CarroEntity atualizar(Long id, CarroEntity carroAtualizado){
        var carroExistente = carroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carro não Encontrado"));

        carroExistente.setAno(carroAtualizado.getAno());
        carroExistente.setModelo(carroAtualizado.getModelo());
        carroExistente.setValorDiaria(carroAtualizado.getValorDiaria());

        return carroRepository.save(carroExistente);
    }

    public void deletar(Long id){
        var carroExistente = carroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carro não Encontrado"));

        carroRepository.deleteById(id);
    }

    public CarroEntity buscarPorId(Long id){
        return carroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carro não Encontrado"));
    }

    public List<CarroEntity> listarTodos(){
        return  carroRepository.findAll();
    }
}
