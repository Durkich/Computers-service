package com.example.license.service;

import com.example.license.model.Type;
import com.example.license.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TypeService {
    @Autowired
    private TypeRepository typeRepository;

    public Type getType(int typeId){
        return typeRepository.findById(typeId).orElse(null);
    }

    public Iterable<Type> findAllTypes(String keyword) {
        if (keyword != null) {
            return typeRepository.searchByNameContainsIgnoreCase(keyword);
        }
        return typeRepository.findAllByOrderByIdAsc();
    }

    public void createType(Type type){
        if (type != null){
            typeRepository.save(type);
        }
    }

    public boolean updateType(int typeId, Type type){
        Type existingType = typeRepository.findById(typeId).orElse(null);
        if (existingType != null) {
            existingType.setName(type.getName());
            typeRepository.save(existingType);
            return true;
        }
        return false;
    }

    public void deleteType(Type type){
        typeRepository.delete(type);
    }
}
