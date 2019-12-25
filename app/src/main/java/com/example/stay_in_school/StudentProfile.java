package com.example.stay_in_school;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores the student's previous years and current year.
 */
public class StudentProfile {
    private SchoolYear currentYear;
    private List<SchoolYear> previousYears;

    public StudentProfile(SchoolYear schoolYear, List<SchoolYear> previousYears) {
        this.currentYear = schoolYear;
        this.previousYears = previousYears;
    }

    public StudentProfile(SchoolYear schoolYear) {
        this.currentYear = schoolYear;
        this.previousYears = new ArrayList<>();
    }

    public SchoolYear getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(SchoolYear currentYear) {
        this.currentYear = currentYear;
    }

    public List<SchoolYear> getPreviousYears() {
        return previousYears;
    }

    public void setPreviousYears(List<SchoolYear> previousYears) {
        this.previousYears = previousYears;
    }
}
