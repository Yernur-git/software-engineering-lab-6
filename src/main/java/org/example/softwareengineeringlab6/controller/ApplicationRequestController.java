package org.example.softwareengineeringlab6.controller;

import org.example.softwareengineeringlab6.ApplicationRequest;
import org.example.softwareengineeringlab6.entity.Courses;
import org.example.softwareengineeringlab6.repository.ApplicationRequestRepository;
import org.example.softwareengineeringlab6.repository.CoursesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

record ApplicationRequestDTO(
        String userName,
        Long courseId,
        String commentary,
        String phone,
        boolean handled
) {}

@RestController
@RequestMapping("/api/requests")
public class ApplicationRequestController {

    @Autowired
    private ApplicationRequestRepository requestRepository;
    @Autowired
    private CoursesRepository coursesRepository;

    @GetMapping
    public List<ApplicationRequest> getAllRequests() {
        return requestRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationRequest> getRequestById(@PathVariable Long id) {
        Optional<ApplicationRequest> request = requestRepository.findById(id);
        return request.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ApplicationRequest> addRequest(@RequestBody ApplicationRequestDTO requestDto) {
        Optional<Courses> courseOptional = coursesRepository.findById(requestDto.courseId());

        if (courseOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        ApplicationRequest newRequest = new ApplicationRequest(
                requestDto.userName(),
                courseOptional.get(),
                requestDto.commentary(),
                requestDto.phone()
        );

        if (requestDto.handled() != newRequest.isHandled()) {
            newRequest.setHandled(requestDto.handled());
        }

        ApplicationRequest savedRequest = requestRepository.save(newRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationRequest> updateRequestStatus(@PathVariable Long id, @RequestBody ApplicationRequest updateDetails) {
        Optional<ApplicationRequest> requestOptional = requestRepository.findById(id);

        if (requestOptional.isPresent()) {
            ApplicationRequest request = requestOptional.get();
            request.setHandled(updateDetails.isHandled());

            ApplicationRequest updatedRequest = requestRepository.save(request);
            return ResponseEntity.ok(updatedRequest);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        if (requestRepository.existsById(id)) {
            requestRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}