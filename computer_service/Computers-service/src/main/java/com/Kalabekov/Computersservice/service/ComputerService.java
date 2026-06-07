package com.Kalabekov.Computersservice.service;

import com.Kalabekov.Computersservice.model.Computer;
import com.Kalabekov.Computersservice.repository.ComputerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComputerService {

    @Autowired
    private ComputerRepository computerRepository;

    public Computer getComputer(int computerID){
        return computerRepository.findById(computerID).orElse(null);
    }

    public Iterable<Computer> findAllComputers() {
        return computerRepository.findAll();
    }

    public void createComputer(Computer computer){
        if(computer!=null){
            computerRepository.save(computer);
        }
    }

    public void updateComputer(int computerId, Computer computer){
        Computer existingComputer = computerRepository.findById(computerId).orElse(null);
        if (existingComputer != null) {
            computer.setId(computerId);
            computerRepository.save(computer);
        }
    }

    public void deleteComputer(int computerId){
        computerRepository.deleteById(computerId);
    }
}