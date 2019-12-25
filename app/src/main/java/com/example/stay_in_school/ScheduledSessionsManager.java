package com.example.stay_in_school;

import android.icu.util.Calendar;
import android.util.Range;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Calculates properties on a list of scheduled sessions.
 */
public abstract class ScheduledSessionsManager {
    /**
     * Return the largest number of sessions attended continuously in the given list
     * @return the longest streak in the list
     */
    public static int getLongestStreak(Collection<ScheduledSession> sessions) {
        List<ScheduledSession> sessionsList = new ArrayList<>(sessions);
        Collections.sort(sessionsList);
        int longestStreak = 0;
        int currentStreak = 0;
        for (int i = 0; i < sessionsList.size(); i++) {
            if (sessionsList.get(i).isAttended())
                currentStreak += 1;
            else {
                if (currentStreak > longestStreak) longestStreak = currentStreak;
                currentStreak = 0;
            }
        }

        return (longestStreak > currentStreak) ? longestStreak : currentStreak;
    }

    /**
     * Return the number of sessions attended continuously since the beginning or last missed class
     * @return the current streak in the list
     */
    public static int getCurrentStreak(Collection<ScheduledSession> sessions, Calendar currentTime) {
        List<ScheduledSession> sessionsList = new ArrayList<>(sessions);
        Collections.sort(sessionsList);
        int currentStreak = 0;
        for (int i = 0; i < sessionsList.size() &&
                currentTime.before(sessionsList.get(i).getEndTime()); i++) {
            if (sessionsList.get(i).isAttended())
                currentStreak += 1;
            else
                currentStreak = 0;
        }

        return currentStreak;
    }

    /**
     * Returns a list of sessions that are in the specified range
     * @param sessionsList list of sessions to consider
     * @param range the necessary range
     * @return the sessions in the given list that are in the necessary range
     */
    public static List<ScheduledSession> getSessionsInRange(List<ScheduledSession> sessionsList,
                                                     Range<Calendar> range) {
        List<ScheduledSession> sessionsInRange = new ArrayList<>();
        for (ScheduledSession session : sessionsList) {
            if (range.contains(session.getStartTime()))
                sessionsInRange.add(session);
        }

        return sessionsInRange;
    }

    /**
     * Returns the list of all sessions that are of the specified class type from the given sessions list
     * @param sessionsList list of sessions to consider
     * @param classType the desired class type
     * @return the sessions in the given list that are of the desired class type
     */
    public static List<ScheduledSession> getListOfClassType (List<ScheduledSession> sessionsList,
                                                             Session.ClassType classType) {
        List<ScheduledSession> sessionsOfType = new ArrayList<>();
        for (ScheduledSession session : sessionsList) {
            if (classType.equals(session.getClassType()))
                sessionsOfType.add(session);
        }

        return sessionsOfType;
    }

}
