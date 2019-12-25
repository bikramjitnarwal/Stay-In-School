package com.example.stay_in_school;


import android.icu.util.Calendar;

/**
 * A lecture, tutorial, or practical on a student's
 * timetable that will be repeated weekly or bi-weekly
 */
public class ScheduledSession extends Session implements Comparable<ScheduledSession>{
       private boolean attended;

    public ScheduledSession(String courseCode, ClassType classType, Calendar startTime,
                            Calendar endTime, String location, boolean attended) {
        super(courseCode, classType, startTime, endTime, location);
        this.attended = attended;
    }

    public boolean isAttended() {
        return attended;
    }

    public void setAttended(boolean attended) {
        this.attended = attended;
    }

    @Override
    public int compareTo(ScheduledSession session) {
        return getStartTime().compareTo(session.getStartTime());
    }
}
