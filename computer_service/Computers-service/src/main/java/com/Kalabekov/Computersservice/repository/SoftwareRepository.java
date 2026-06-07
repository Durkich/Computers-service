package com.Kalabekov.Computersservice.repository;
import com.Kalabekov.Computersservice.model.Software;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoftwareRepository extends CrudRepository<Software,Integer> {
}
