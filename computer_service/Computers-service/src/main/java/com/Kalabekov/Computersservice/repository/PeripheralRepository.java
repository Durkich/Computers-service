package com.Kalabekov.Computersservice.repository;

import com.Kalabekov.Computersservice.model.Peripheral;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeripheralRepository extends CrudRepository<Peripheral,Integer> {
}
