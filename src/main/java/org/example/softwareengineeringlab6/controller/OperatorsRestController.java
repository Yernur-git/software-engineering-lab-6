package org.example.softwareengineeringlab6.controller;

import org.example.softwareengineeringlab6.entity.Operators;
import org.example.softwareengineeringlab6.service.OperatorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operators")
public class OperatorsRestController {

    @Autowired
    private OperatorsService operatorsService;

    @GetMapping
    public ResponseEntity<List<Operators>> getAllOperators() {
        return ResponseEntity.ok(operatorsService.getAllOperators());
    }

    @PostMapping
    public ResponseEntity<Operators> addOperator(@RequestBody Operators operator) {
        return new ResponseEntity<>(operatorsService.addOperator(operator), HttpStatus.CREATED);
    }
}
