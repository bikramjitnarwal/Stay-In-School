package com.example.stay_in_school;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A course that has a name, a course code, and periodic and scheduled sessions
 */
public class Course {
    private String title;
    private String code;

    private List<PeriodicSession> periodicSessions;
    private Map<PeriodicSession, List<ScheduledSession>> scheduledSessions;

    public Course (String title, String code) {
        this.title = title;
        this.code = code;
        periodicSessions = new ArrayList<>();
        scheduledSessions = new HashMap<>();
    }

    public Course(String title, String code, List<PeriodicSession> periodicSessions,
                  Map<PeriodicSession, List<ScheduledSession>> scheduledSessions) {
        this.title = title;
        this.code = code;
        this.periodicSessions = periodicSessions;
        this.scheduledSessions = scheduledSessions;
    }

    public void deletePeriodicSession(PeriodicSession session) {
        periodicSessions.remove(session);
        scheduledSessions.remove(session);
    }

    public void editPeriodicSession(PeriodicSession session) {
//        session.setStuff();
    }

    public void addPeriodicSession(PeriodicSession session) {
        periodicSessions.add(session);
    }

    public void cancelScheduledSession(ScheduledSession session) {
        // find the periodic session in the Map and deletes it
        // May Change implementation of Periodic + Scheduled (Paired together??)
    }

    public void editScheduledSession(ScheduledSession session) {
//        session.setStuff();
    }

    public void addScheduledSession(ScheduledSession session) {
        // find the periodic session in the Map and adss it
        // May Change implementation of Periodic + Scheduled (Paired together??)
    }

}
