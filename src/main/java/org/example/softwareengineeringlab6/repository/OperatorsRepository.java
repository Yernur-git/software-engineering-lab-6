package org.example.softwareengineeringlab6.repository;

import org.example.softwareengineeringlab6.entity.Operators;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperatorsRepository extends JpaRepository<Operators, Long> {
    List<Operators> findAll();
}