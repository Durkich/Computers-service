package com.Kalabekov.Computersservice.service;

import com.Kalabekov.Computersservice.model.Laptop;
import com.Kalabekov.Computersservice.repository.LaptopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LaptopService {

    @Autowired
    private LaptopRepository laptopRepository;

    public Laptop getLaptop(int laptopId){
        return laptopRepository.findById(laptopId).orElse(null);
    }

    public Iterable<Laptop> findAllLaptops() {
        return laptopRepository.findAll();
    }

    public void createLaptop(Laptop laptop){
        if(laptop != null){
            laptopRepository.save(laptop);
        }
    }

    public void updateLaptop(int laptopId, Laptop laptop){
        Laptop existingLaptop = laptopRepository.findById(laptopId).orElse(null);
        if (existingLaptop != null) {
            laptop.setId(laptopId);
            laptopRepository.save(laptop);
        }
    }

    public void deleteLaptop(int laptopId){
        laptopRepository.deleteById(laptopId);
    }
}