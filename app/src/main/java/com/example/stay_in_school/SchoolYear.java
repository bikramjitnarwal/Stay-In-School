package com.example.stay_in_school;

import java.util.HashMap;
import java.util.Map;

/**
 * A school year that contains a first term and a second term. Also, a list of courses taken by the student.
 */
public class SchoolYear {
    private String year;
    private SchoolTerm firstTerm;
    private SchoolTerm secondTerm;

    // The key is the course code, the value is the course
    private Map<String, Course> courses;

    public SchoolYear(String year, SchoolTerm firstTerm, SchoolTerm secondTerm, Map<String, Course> courses) {
        this.year = year;
        this.firstTerm = firstTerm;
        this.secondTerm = secondTerm;
        this.courses = courses;
    }

    public SchoolYear(String year, SchoolTerm firstTerm, SchoolTerm secondTerm) {
        this.year = year;
        this.firstTerm = firstTerm;
        this.secondTerm = secondTerm;
        this.courses = new HashMap<>();
    }

    public void addCourse(Course course) {
        courses.put(course.getCode(), course);
    }

    public void removeCourse(Course course) {
        courses.remove(course.getCode());
    }

    /**
     * A map of courses, where the key is the course code and the value is the course object
     * @return all courses in this school year
     */
    public Map<String, Course> getCourses() {
        return new HashMap<>(courses);
    }
}
