package com.example.stay_in_school;

import android.icu.util.Calendar;
import android.util.Pair;
import android.util.Range;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * A course that has a name, a course code, and periodic and scheduled sessions
 */
public class Course implements PropertyChangeListener {
    public static final Pattern COURSE_CODE_PATTERN = Pattern.compile("^[A-Z]{3}\\d{3}[HY]1 [FYS]$");

    private String title;
    private String code;

    private List<SchoolTerm> schoolTerms;
    private Map<PeriodicSession, List<ScheduledSession>> sessionsMap;

    /**
     * Constructs a new course with no existing sessions.
     * @param title the course name
     * @param code the course code
     * @param schoolTerms the list of terms the course takes place
     */
    public Course (String title, String code, List<SchoolTerm> schoolTerms) {
        this.title = title;
        this.code = code;
        this.schoolTerms = schoolTerms;
        sessionsMap = new HashMap<>();
    }

    /**
     * Constructs a course with sessions specified by the sessions map.
     * @param title the course name
     * @param code the course code
     * @param schoolTerms the list of terms the course takes place
     * @param sessionsMap the course sessions where the key is the periodic session and the value
     *                    is a list of scheduled sessions
     */
    public Course(String title, String code, List<SchoolTerm> schoolTerms,
                  Map<PeriodicSession, List<ScheduledSession>> sessionsMap) {
        this.title = title;
        this.code = code;
        this.schoolTerms = schoolTerms;
        this.sessionsMap = sessionsMap;
    }

    /**
     * Adds the periodic session and corresponding scheduled sessions to the course.
     * @param session the periodic session being added
     */
    public void addPeriodicSession(PeriodicSession session) {
        session.addPropertyChangeListener(this);
        sessionsMap.put(session, generateScheduledSessions(session));
    }

    /**
     * Deletes all future sessions of this periodic session
     * @param session the periodic session being deleted
     */
    public void deletePeriodicSession(PeriodicSession session) {
        session.removePropertyChangeListener(this);
        deleteFutureSessions(session, Calendar.getInstance());
    }

    /**
     * Updates future sessions based on the periodic session changed
     * @param propertyChangeEvent the property change event
     */
    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        Object source = propertyChangeEvent.getSource();
        for (PeriodicSession periodicSession : sessionsMap.keySet()) {
            if (periodicSession.equals(source)) {
                switch (propertyChangeEvent.getPropertyName()) {
                    case "time" :
                        updateFutureStartTimes(periodicSession,
                                (Pair<Calendar, Calendar>) propertyChangeEvent.getOldValue(),
                                Calendar.getInstance());
                        break;
                    case "location" :
                        updateFutureLocations(periodicSession, Calendar.getInstance());
                        break;
                    case "period" :
                        sessionsMap.replace(periodicSession, generateScheduledSessions(periodicSession));
                }
                return;
            }
        }
    }

    // Updates the time of future events either by replacing them if the change is drastic or
    // changing the day, hour, minute parameters
    private void updateFutureStartTimes(PeriodicSession periodicSession,
                                        Pair<Calendar, Calendar> oldTimes, Calendar currentTime) {
        // If the period starts on a different week, then regenerate all the events
        if (oldTimes.first.get(Calendar.WEEK_OF_YEAR) !=
                periodicSession.getStartTime().get(Calendar.WEEK_OF_YEAR)) {
            sessionsMap.replace(periodicSession, generateScheduledSessions(periodicSession));
            return;
        }

        // Changes the day of week and time of day for each future session
        List<ScheduledSession> sessionsList = sessionsMap.get(periodicSession);
        for (ScheduledSession session : sessionsList) {
            if (currentTime.before(session.getEndTime())) {
                Calendar newStartTime = session.getStartTime();
                Calendar periodicStartTime = periodicSession.getStartTime();
                newStartTime.set(Calendar.DAY_OF_WEEK, periodicStartTime.get(Calendar.DAY_OF_WEEK));
                newStartTime.set(Calendar.HOUR_OF_DAY, periodicStartTime.get(Calendar.HOUR_OF_DAY));
                newStartTime.set(Calendar.MINUTE, periodicStartTime.get(Calendar.MINUTE));
                session.setStartTime(newStartTime);

                Calendar newEndTime = session.getEndTime();
                Calendar periodicEndTime = periodicSession.getEndTime();
                newEndTime.set(Calendar.DAY_OF_WEEK, periodicEndTime.get(Calendar.DAY_OF_WEEK));
                newEndTime.set(Calendar.HOUR_OF_DAY, periodicEndTime.get(Calendar.HOUR_OF_DAY));
                newEndTime.set(Calendar.MINUTE, periodicEndTime.get(Calendar.MINUTE));
                session.setEndTime(newEndTime);
            }
        }
    }

    //
    private void updateFutureLocations(PeriodicSession periodicSession, Calendar currentTime) {
        List<ScheduledSession> sessionsList = sessionsMap.get(periodicSession);
        for (ScheduledSession session : sessionsList) {
            if (currentTime.before(session.getEndTime()))
                session.setLocation(periodicSession.getLocation());
        }
    }

    private void deleteFutureSessions(PeriodicSession periodicSession, Calendar currentTime) {
        List<ScheduledSession> sessionsList = sessionsMap.get(periodicSession);
        for (ScheduledSession session : sessionsList) {
            if (currentTime.before(session.getEndTime()))
                sessionsList.remove(session);
        }
    }

    /**
     * Removes a scheduled session from the course
     * @param session the scheduled session to be removed
     */
    public void cancelScheduledSession(ScheduledSession session) {
        // Goes through each key of the map and removes the scheduled session if it is in the list
        for (PeriodicSession periodicSession : sessionsMap.keySet())
            if (sessionsMap.get(periodicSession).remove(session)) break;
    }

    /**
     * Adds a single, non-periodic scheduled session to the course
     * @param session a scheduled session
     */
    public void addScheduledSession(ScheduledSession session) {
        PeriodicSession periodicSession = new PeriodicSession(session.getCourseCode(),
                session.getClassType(), session.getStartTime(), session.getEndTime(),
                session.getLocation(), PeriodicSession.Period.ONCE);
        periodicSession.addPropertyChangeListener(this);

        List<ScheduledSession> sessionsList = new ArrayList<>();
        sessionsList.add(session);
        sessionsMap.put(periodicSession, sessionsList);
    }

    /**
     * Generates a list of scheduled sessions based on the information given by the periodic session
     * and the school terms
     * @param periodicSession the information for a repeating session
     * @return list of scheduled sessions
     */
    private List<ScheduledSession> generateScheduledSessions(PeriodicSession periodicSession) {
        List<ScheduledSession> scheduledSessions = new ArrayList<>();
        int weekIncrement = (periodicSession.getPeriod() == PeriodicSession.Period.BIWEEKLY) ? 2: 1;
        for (SchoolTerm term : this.schoolTerms) {
            // May need to set leniency of time
            // Increments weekly or bi-weekly creating a new scheduled session
            for (Calendar time = periodicSession.getStartTime(); time.before(term.getClassesEndDate());
                time.add(Calendar.WEEK_OF_YEAR, weekIncrement)) {

                // ensures the session is not during a break
                while (!validClassTime(time, term)) {
                    time.add(Calendar.WEEK_OF_YEAR, 1);
                }
                if(time.after(term.getClassesEndDate())) break;

                // Calculates the end time of the scheduled session
                Calendar endTime = Calendar.getInstance();
                int endHour = periodicSession.getEndTime().get(Calendar.HOUR_OF_DAY);
                int endMinute = periodicSession.getEndTime().get(Calendar.MINUTE);
                endTime.set(time.get(Calendar.YEAR), time.get(Calendar.MONTH), time.get(Calendar.DATE),
                        endHour, endMinute);

                scheduledSessions.add(new ScheduledSession(periodicSession.getCourseCode(),
                        periodicSession.getClassType(), time, endTime,
                        periodicSession.getLocation(), false));
            }
        }

        return scheduledSessions;
    }

    private boolean validClassTime(Calendar time, SchoolTerm schoolTerm) {
        if (time.before(schoolTerm.getClassesStartDate())) return false;
        for (Range<Calendar> noClassesRange : schoolTerm.getNoClassesRanges()) {
            if (noClassesRange.contains(time)) return false;
        }

        return true;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<PeriodicSession> getActualPeriodicSessions() {
        List<PeriodicSession> periodicSessions = new ArrayList<>(sessionsMap.keySet());
        for (PeriodicSession session : periodicSessions)
            if (session.getPeriod().equals(PeriodicSession.Period.ONCE)) periodicSessions.remove(session);
        return periodicSessions;
    }

    public List<ScheduledSession> getAllScheduledSessions() {
        List<ScheduledSession> allScheduledSessions = new ArrayList<>();
        for (PeriodicSession periodicSession : sessionsMap.keySet())
            allScheduledSessions.addAll(sessionsMap.get(periodicSession));
        return allScheduledSessions;
    }

}
