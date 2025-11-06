package org.example.softwareengineeringlab6.controller;

import org.example.softwareengineeringlab6.entity.ApplicationRequest;
import org.example.softwareengineeringlab6.service.ApplicationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class ApplicationRequestRestController {

    @Autowired
    private ApplicationRequestService requestService;

    @GetMapping
    public ResponseEntity<List<ApplicationRequest>> getAllRequests() {
        return ResponseEntity.ok(requestService.getAllRequests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationRequest> getRequestById(@PathVariable Long id) {
        ApplicationRequest req = requestService.getRequestById(id);
        if (req == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(requestService.getRequestById(id));
    }

    @PostMapping
    public ResponseEntity<ApplicationRequest> addRequest(@RequestBody ApplicationRequest request) {
        ApplicationRequest saved = requestService.addRequest(request);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationRequest> updateRequest(@PathVariable Long id, @RequestBody ApplicationRequest requestDetails) {
        ApplicationRequest existingRequest = requestService.getRequestById(id);
        if (existingRequest == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingRequest.setHandled(requestDetails.isHandled());

        ApplicationRequest updatedRequest = requestService.addRequest(existingRequest);
        return new ResponseEntity<>(updatedRequest, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        requestService.deleteRequest(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{requestId}/assign/{operatorId}")
    public ResponseEntity<ApplicationRequest> assignOperator(@PathVariable Long requestId, @PathVariable Long operatorId) {
        ApplicationRequest updated = requestService.assignOperator(requestId, operatorId);
        return updated != null ? ResponseEntity.ok(updated) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
