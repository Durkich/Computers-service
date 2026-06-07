package com.Kalabekov.Computersservice.service;
import com.Kalabekov.Computersservice.model.Peripheral;
import com.Kalabekov.Computersservice.repository.PeripheralRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PeripheralService {
    @Autowired
    private PeripheralRepository peripheralRepository;

    public Peripheral getPeripheral(int peripheralID){
        return peripheralRepository.findById(peripheralID).orElse(null);
    }

    public Iterable<Peripheral> getAllPeripherals(){
        return peripheralRepository.findAll();
    }


    public void createPeripheral(Peripheral peripheral){
        if(peripheral!=null){
            peripheralRepository.save(peripheral);
        }
    }

    public void updatePeripheral(int peripheralID,Peripheral peripheral){
        Peripheral existingPeripheral = peripheralRepository.findById(peripheralID).orElse(null);
        if (existingPeripheral != null) {
            peripheral.setId(peripheralID);
            peripheralRepository.save(peripheral);
        }
    }

    public void deletePeripheral(int peripheralID){
        peripheralRepository.deleteById(peripheralID);
    }
}
