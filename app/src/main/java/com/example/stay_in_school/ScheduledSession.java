package com.example.stay_in_school;

import java.util.Calendar;

/**
 * A concrete session that takes place at a specific date
 */
public class ScheduledSession extends Session {
    private boolean attended;

    public ScheduledSession(ClassType classType, Calendar startTime, Calendar endTime, String location) {
        super(classType, startTime, endTime, location);
        this.attended = false;
    }

    public boolean isAttended() {
        return attended;
    }

    public void setAttended(boolean attended) {
        this.attended = attended;
    }
}
