package com.example.stay_in_school;

import android.icu.util.Calendar;
import android.util.Range;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    private Calendar currentTime = Calendar.getInstance();

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
     * Deletes all future sessions of this periodic session
     * @param session the periodic session being deleted
     */
    public void deletePeriodicSession(PeriodicSession session) {
        session.removePropertyChangeListener(this);
        sessionsMap.remove(session);
        updateFutureSessions(session, "delete");
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
     * Updates future sessions based on the periodic session changed
     * @param propertyChangeEvent the property change event
     */
    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        Object source = propertyChangeEvent.getSource();
        for (PeriodicSession periodicSession : sessionsMap.keySet()) {
            if (periodicSession.equals(source)) {
                updateFutureSessions(periodicSession, propertyChangeEvent.getPropertyName());
                return;
            }
        }
    }

    /**
     * Updates future scheduled sessions based on the property changed by the periodic session.
     * @param periodicSession the periodic sessions to update
     * @param propertyName the property to update
     */
    private void updateFutureSessions(PeriodicSession periodicSession, String propertyName) {
        List<ScheduledSession> sessionsList = sessionsMap.get(periodicSession);
        for (ScheduledSession session : sessionsList) {
            if (currentTime.compareTo(session.getStartTime()) < 0) {
                switch (propertyName) {
                    case "delete" :
                        sessionsList.remove(session);
                        break;
                    case "startTime" :
                        // TODO: need to figure out computation for new startTime
                        session.setStartTime(periodicSession.getStartTime());
                        break;
                    case "location" :
                        session.setLocation((periodicSession.getLocation()));
                        break;
                    case "period" :
                        // TODO: need to figure out computation for new period
                        break;
                }
            }
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

    public Set<PeriodicSession> getPeriodicSessions() {
        return sessionsMap.keySet();
    }

    public List<ScheduledSession> getAllScheduledSessions() {
        List<ScheduledSession> allScheduledSessions = new ArrayList<>();
        for (PeriodicSession periodicSession : sessionsMap.keySet())
            allScheduledSessions.addAll(sessionsMap.get(periodicSession));
        return allScheduledSessions;
    }

}
