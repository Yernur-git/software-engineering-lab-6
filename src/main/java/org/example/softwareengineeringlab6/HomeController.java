package org.example.softwareengineeringlab6;

import org.example.softwareengineeringlab6.entity.Operators;
import org.example.softwareengineeringlab6.repository.ApplicationRequestRepository;
import org.example.softwareengineeringlab6.repository.CoursesRepository;
import org.example.softwareengineeringlab6.repository.OperatorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private ApplicationRequestRepository requestRepository;
    @Autowired
    private CoursesRepository coursesRepository;
    @Autowired
    private OperatorsRepository operatorsRepository;

    @GetMapping("/")
    public String index(Model model) {
        List<ApplicationRequest> requests = requestRepository.findAll();
        model.addAttribute("requests", requests);
        return "index";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable Long id, Model model) {
        Optional<ApplicationRequest> request = requestRepository.findById(id);
        if (request.isPresent()) {
            model.addAttribute("request", request.get());

            model.addAttribute("allOperators", operatorsRepository.findAll());
            return "details";
        }
        return "redirect:/";
    }

    @GetMapping("/new-requests")
    public String pendingRequests(Model model) {
        List<ApplicationRequest> pendingRequests = requestRepository.findAllByHandled(false);
        model.addAttribute("requests", pendingRequests);
        model.addAttribute("status", "New");
        return "filtered-list";
    }

    @GetMapping("/processed-requests")
    public String processedRequests(Model model) {
        List<ApplicationRequest> processedRequests = requestRepository.findAllByHandled(true);
        model.addAttribute("requests", processedRequests);
        model.addAttribute("status", "Processed");
        return "filtered-list";
    }

    @PostMapping("/assign-operators/{requestId}")
    public String assignOperators(@PathVariable Long requestId, @RequestParam(value = "operatorIds", required = false) List<Long> operatorIds) {
        Optional<ApplicationRequest> requestOptional = requestRepository.findById(requestId);

        if (requestOptional.isPresent()) {
            ApplicationRequest request = requestOptional.get();


            if (operatorIds != null && !operatorIds.isEmpty()) {
                List<Operators> selectedOperators = operatorsRepository.findAllById(operatorIds);
                request.setOperators(selectedOperators);
            } else {
                request.setOperators(List.of());
            }

            request.setHandled(true);

            requestRepository.save(request);
        }
        return "redirect:/details/" + requestId;
    }

    @PostMapping("/remove-operator/{requestId}/{operatorId}")
    public String removeOperator(@PathVariable Long requestId, @PathVariable Long operatorId) {
        Optional<ApplicationRequest> requestOptional = requestRepository.findById(requestId);

        if (requestOptional.isPresent()) {
            ApplicationRequest request = requestOptional.get();

            request.getOperators().removeIf(operator -> operator.getId().equals(operatorId));

            requestRepository.save(request);
        }

        return "redirect:/details/" + requestId;
    }

    @PostMapping("/delete-request/{id}")
    public String deleteRequest(@PathVariable Long id) {
        requestRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/add-request")
    public String addRequestForm(Model model) {

        model.addAttribute("applicationRequest", new ApplicationRequest());

        model.addAttribute("courses", coursesRepository.findAll());
        return "add-request";
    }

    @PostMapping("/add-request")
    public String addRequestSubmit(@ModelAttribute ApplicationRequest applicationRequest) {

        applicationRequest.setHandled(false);

        requestRepository.save(applicationRequest);
        return "redirect:/";
    }
}