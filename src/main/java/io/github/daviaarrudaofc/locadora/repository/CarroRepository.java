package io.github.daviaarrudaofc.locadora.repository;

import io.github.daviaarrudaofc.locadora.entities.CarroEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarroRepository extends JpaRepository<CarroEntity, Long> {
}
