package org.example.softwareengineeringlab6.service;

import org.example.softwareengineeringlab6.entity.ApplicationRequest;
import org.example.softwareengineeringlab6.entity.Courses;
import org.example.softwareengineeringlab6.entity.Operators;
import org.example.softwareengineeringlab6.repository.ApplicationRequestRepository;
import org.example.softwareengineeringlab6.repository.CoursesRepository;
import org.example.softwareengineeringlab6.repository.OperatorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationRequestService {

    @Autowired
    private ApplicationRequestRepository requestRepository;

    @Autowired
    private CoursesRepository coursesRepository;

    @Autowired
    private OperatorsRepository operatorsRepository;

    public List<ApplicationRequest> getAllRequests() {
        return requestRepository.findAll();
    }

    public ApplicationRequest getRequestById(Long id) {
        return requestRepository.findById(id).orElse(null);
    }

    public ApplicationRequest addRequest(ApplicationRequest request) {
        if (request.getCourse() != null && request.getCourse().getId() != null) {
            Courses course = coursesRepository.findById(request.getCourse().getId()).orElse(null);
            request.setCourse(course);
        }
        return requestRepository.save(request);
    }

    public ApplicationRequest updateRequest(Long id, ApplicationRequest details) {
        ApplicationRequest existing = getRequestById(id);
        if (existing != null) {
            existing.setHandled(details.isHandled());
            existing.setCommentary(details.getCommentary());
            existing.setPhone(details.getPhone());
            return requestRepository.save(existing);
        }
        return null;
    }

    public void deleteRequest(Long id) {
        requestRepository.deleteById(id);
    }

    public ApplicationRequest assignOperator(Long requestId, Long operatorId) {
        ApplicationRequest request = getRequestById(requestId);
        Operators operator = operatorsRepository.findById(operatorId).orElse(null);
        if (request != null && operator != null) {
            request.getOperators().add(operator);
            request.setHandled(true);
            return requestRepository.save(request);
        }
        return null;
    }
}
