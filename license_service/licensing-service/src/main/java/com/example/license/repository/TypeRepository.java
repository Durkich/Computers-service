package com.example.license.repository;

import com.example.license.model.Type;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeRepository extends CrudRepository<Type,Integer>  {
    List<Type> findAllByOrderByIdAsc();
    public Iterable<Type> searchByNameContainsIgnoreCase(String keyword);
}
