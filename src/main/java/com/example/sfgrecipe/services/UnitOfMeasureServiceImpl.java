package com.example.sfgrecipe.services;

import com.example.sfgrecipe.model.UnitOfMeasure;
import com.example.sfgrecipe.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;
import org.thymeleaf.expression.Sets;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public Iterable<UnitOfMeasure> listAllUnitsOfMeasure() {
        return unitOfMeasureRepository.findAll();
    }
}
