package com.example.stay_in_school;

import android.icu.util.Calendar;
import android.util.Pair;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * A lecture, tutorial, or practical on a student's
 * timetable that will be repeated weekly or bi-weekly
 */
public class PeriodicSession extends Session{
    public enum Period {ONCE, WEEKLY, BIWEEKLY}

    private Period period;
    private PropertyChangeSupport support;

    /**
     * Constructs a periodic session
     * @param classType lecture, tutorial, or practical
     * @param startTime the first day of session and time of day the session starts
     * @param endTime the last day of session and time of day the session ends
     * @param location the location of the session
     * @param period session happens weekly, or bi-weekly
     */
    public PeriodicSession(String courseCode, ClassType classType, Calendar startTime,
                           Calendar endTime, String location, Period period) {
        super(courseCode, classType, startTime, endTime, location);
        this.period = period;
        this.support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    public void setTime(Calendar startTime, Calendar endTime) {
        Pair<Calendar, Calendar> oldValue = new Pair<>(getStartTime(), getEndTime());
        super.setStartTime(startTime);
        super.setEndTime(endTime);
        support.firePropertyChange("time", oldValue, new Pair<>(startTime, endTime));

    }

    public void setLocation(String location) {
        String oldValue = getLocation();
        super.setLocation(location);
        support.firePropertyChange("location", oldValue, location);
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        Period oldValue = this.period;
        this.period = period;
        support.firePropertyChange("period", oldValue, period);

    }
}
