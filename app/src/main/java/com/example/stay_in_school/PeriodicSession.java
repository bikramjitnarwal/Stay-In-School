package com.example.stay_in_school;

import java.time.DayOfWeek;
import java.util.Calendar;

/**
 * A lecture, tutorial, or practical on a student's
 * timetable that will be repeated weekly or bi-weekly
 */
public class PeriodicSession extends Session{
    public enum Period {WEEKLY, BIWEEKLY}

    private DayOfWeek dayOfWeek;
    private Period period;

    /**
     * Constructs a periodic session
     * @param classType lecture, tutorial, or practical
     * @param startTime the first day of session and time of day the session starts
     * @param endTime the last day of session and time of day the session ends
     * @param location the location of the session
     * @param dayOfWeek the day of the week the session lays on
     * @param period session happens weekly, or bi-weekly
     */
    public PeriodicSession(ClassType classType, Calendar startTime, Calendar endTime,
                            String location, DayOfWeek dayOfWeek, Period period) {
        super(classType, startTime, endTime, location);
        this.dayOfWeek = dayOfWeek;
        this.period = period;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }
}
