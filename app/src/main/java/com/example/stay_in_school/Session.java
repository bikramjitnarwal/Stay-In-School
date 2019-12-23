package com.example.stay_in_school;

import java.util.Calendar;

public class Session {
    public enum ClassType {LECTURE, TUTORIAL, PRACTICAL}

    private ClassType classType;
    private Calendar startTime;
    private Calendar endTime;
    private String location;    // Will need another data type representation

    Session(ClassType classType, Calendar startTime, Calendar endTime, String location) {
        this.classType = classType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
    }

    public ClassType getClassType() {
        return classType;
    }

    public void setClassType(ClassType classType) {
        this.classType = classType;
    }

    Calendar getStartTime() {
        return startTime;
    }

    void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    Calendar getEndTime() {
        return endTime;
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
