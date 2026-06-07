package com.Kalabekov.Computersservice.repository;

import com.Kalabekov.Computersservice.model.Laptop;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaptopRepository extends CrudRepository<Laptop,Integer> {
}
