package com.example.stay_in_school;

import android.icu.util.Calendar;

/**
 * An event with a location and a start and end time.
 */
public class Session {
    public enum ClassType {LECTURE, TUTORIAL, PRACTICAL}

    private String courseCode;
    private ClassType classType;
    private Calendar startTime;
    private Calendar endTime;
    private String location;    // Will need another data type representation

    Session(String courseCode, ClassType classType, Calendar startTime, Calendar endTime, String location) {
        this.courseCode = courseCode;
        this.classType = classType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public ClassType getClassType() {
        return classType;
    }

    Calendar getStartTime() {
        return (Calendar) startTime.clone();
    }

    void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    Calendar getEndTime() {
        return (Calendar) endTime.clone();
    }

    void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
