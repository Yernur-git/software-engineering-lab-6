package org.example.softwareengineeringlab6.service;

import org.example.softwareengineeringlab6.entity.Operators;
import org.example.softwareengineeringlab6.repository.OperatorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperatorsService {

    @Autowired
    private OperatorsRepository operatorsRepository;

    public List<Operators> getAllOperators() {
        return operatorsRepository.findAll();
    }

    public Operators addOperator(Operators operator) {
        return operatorsRepository.save(operator);
    }
}
