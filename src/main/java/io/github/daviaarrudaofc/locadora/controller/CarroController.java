package io.github.daviaarrudaofc.locadora.controller;

import io.github.daviaarrudaofc.locadora.entities.CarroEntity;
import io.github.daviaarrudaofc.locadora.exceptions.EntityNotFoundException;
import io.github.daviaarrudaofc.locadora.service.CarroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("carros")
public class CarroController {
    private CarroService carroService;

    public CarroController(CarroService carroService) {
        this.carroService = carroService;
    }

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody CarroEntity carro){
        try {
            var carroSalvo = carroService.salvar(carro);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(carroSalvo);

        }catch (IllegalArgumentException e){
            return  ResponseEntity
                    .status(HttpStatus.UNPROCESSABLE_CONTENT)
                    .body(e.getMessage());
        }

    }

    //localhost:8080/carros/id
    @GetMapping("{id}")
    public ResponseEntity<CarroEntity> detalhesCarro( @PathVariable Long id){
        try {
            var carroEncontrado = carroService.buscarPorId(id);
            return ResponseEntity.ok(carroEncontrado);
        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }


}
