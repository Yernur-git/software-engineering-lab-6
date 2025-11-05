package org.example.softwareengineeringlab6.controller;

import org.example.softwareengineeringlab6.entity.Courses;
import org.example.softwareengineeringlab6.service.CoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CoursesRestController {

    @Autowired
    private CoursesService coursesService;

    @GetMapping
    public ResponseEntity<List<Courses>> getAllCourses() {
        return ResponseEntity.ok(coursesService.getAllCourses());
    }

    @PostMapping
    public ResponseEntity<Courses> addCourse(@RequestBody Courses course) {
        return new ResponseEntity<>(coursesService.addCourse(course), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        coursesService.deleteCourse(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
