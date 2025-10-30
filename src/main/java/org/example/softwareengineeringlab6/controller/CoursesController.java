package org.example.softwareengineeringlab6.controller;

import org.example.softwareengineeringlab6.entity.Courses;
import org.example.softwareengineeringlab6.repository.CoursesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CoursesController {

    @Autowired
    private CoursesRepository coursesRepository;

    @GetMapping
    public List<Courses> getAllCourses() {
        return coursesRepository.findAll();
    }

    @PostMapping
    public Courses addCourse(@RequestBody Courses course) {
        return coursesRepository.save(course);
    }

    @DeleteMapping("/{id}")
    public String deleteCourse(@PathVariable Long id) {
        coursesRepository.deleteById(id);
        return "Course with ID " + id + " deleted successfully.";
    }
}
