package com.main.sentimentally.service;

import com.main.sentimentally.entity.Property;
import com.main.sentimentally.repository.PropertyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;

    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    public Property getProperty(String id) {
        return propertyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Property not found with id: " + id));
    }

    public Property saveProperty(Property property){
        return propertyRepository.save(property);
    }
}
