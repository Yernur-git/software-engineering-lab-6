package org.example.softwareengineeringlab6.repository;

import org.example.softwareengineeringlab6.entity.ApplicationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRequestRepository extends JpaRepository<ApplicationRequest, Long> {
    List<ApplicationRequest> findAllByOrderByIdDesc();

    List<ApplicationRequest> findByHandledFalseOrderByIdDesc();

    List<ApplicationRequest> findByHandledTrueOrderByIdDesc();
}