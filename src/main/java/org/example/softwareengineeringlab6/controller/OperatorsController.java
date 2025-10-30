package org.example.softwareengineeringlab6.controller;

import org.example.softwareengineeringlab6.ApplicationRequest;
import org.example.softwareengineeringlab6.entity.Operators;
import org.example.softwareengineeringlab6.repository.ApplicationRequestRepository;
import org.example.softwareengineeringlab6.repository.OperatorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operators")
public class OperatorsController {

    @Autowired
    private OperatorsRepository operatorsRepository;

    @Autowired
    private ApplicationRequestRepository requestRepository;

    @GetMapping
    public List<Operators> getAllOperators() {
        return operatorsRepository.findAll();
    }

    @PostMapping
    public Operators addOperator(@RequestBody Operators operator) {
        return operatorsRepository.save(operator);
    }

    @PutMapping("/{operatorId}/assign/{requestId}")
    public ApplicationRequest assignOperatorToRequest(@PathVariable Long operatorId, @PathVariable Long requestId) {
        Operators operator = operatorsRepository.findById(operatorId).orElse(null);
        ApplicationRequest request = requestRepository.findById(requestId).orElse(null);

        if (operator != null && request != null) {
            request.getOperators().add(operator);
            request.setHandled(true);
            return requestRepository.save(request);
        }
        return null;
    }
}
