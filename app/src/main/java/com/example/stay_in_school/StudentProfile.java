package com.example.stay_in_school;

import java.util.ArrayList;
import java.util.List;

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
}
