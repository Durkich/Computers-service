package com.Kalabekov.Computersservice.repository;

import com.Kalabekov.Computersservice.model.Computer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComputerRepository extends CrudRepository<Computer,Integer> {
}
