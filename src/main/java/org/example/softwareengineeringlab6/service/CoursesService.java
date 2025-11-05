package org.example.softwareengineeringlab6.service;

import org.example.softwareengineeringlab6.entity.Courses;
import org.example.softwareengineeringlab6.repository.CoursesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoursesService {

    @Autowired
    private CoursesRepository coursesRepository;

    public List<Courses> getAllCourses() {
        return coursesRepository.findAll();
    }

    public Courses addCourse(Courses course) {
        return coursesRepository.save(course);
    }

    public void deleteCourse(Long id) {
        coursesRepository.deleteById(id);
    }
}
