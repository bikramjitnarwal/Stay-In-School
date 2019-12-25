package com.example.stay_in_school;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class ScheduledSessionsManager {
    private List<ScheduledSession> sessionsList;
    private Calendar currentTime = Calendar.getInstance(); // need to agree on locale

    public ScheduledSessionsManager(List<ScheduledSession> sessionsList) {
        Collections.sort(sessionsList);
        this.sessionsList = sessionsList;
    }

    public int getLongestStreak() {
        int longestStreak = 0;
        int currentStreak = 0;
        for (int i = 0; i < sessionsList.size() &&
                currentTime.before(sessionsList.get(i).getEndTime()); i++) {
            if (sessionsList.get(i).isAttended()) {
                currentStreak += 1;
            } else {
                if (currentStreak > longestStreak) longestStreak = currentStreak;
                currentStreak = 0;
            }
        }

        return (longestStreak > currentStreak) ? longestStreak : currentStreak;
    }

    public int getCurrentStreak() {
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

}
