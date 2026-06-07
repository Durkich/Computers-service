package com.Kalabekov.Computersservice.service;

import com.Kalabekov.Computersservice.model.Software;
import com.Kalabekov.Computersservice.repository.SoftwareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SoftwareService {
    @Autowired
    private SoftwareRepository softwareRepository;

    public Software getSoftware(int softwareID) {
        return softwareRepository.findById(softwareID).orElse(null);

    }

    public Iterable<Software> getAllSoftware(){
        return softwareRepository.findAll();
    }

    public void createSoftware(Software software){
        if(software!=null){
            softwareRepository.save(software);
        }
    }

    public void updateSoftware(int softwareID, Software software){
        Software existingSoftware = softwareRepository.findById(softwareID).orElse(null);
        if (existingSoftware != null) {
            software.setId(softwareID);
            softwareRepository.save(software);
        }
    }

    public void deleteSoftware(int softwareID){
        softwareRepository.deleteById(softwareID);
    }
}
