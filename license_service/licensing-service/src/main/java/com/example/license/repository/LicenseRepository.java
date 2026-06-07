package com.example.license.repository;

import com.example.license.model.License;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicenseRepository extends CrudRepository<License,Integer> {
    List<License> findAllByOrderByEndDateAscNamePoAsc();
    public Iterable<License> searchByNamePoContainingIgnoreCase(String keyword);
}
