package com.example.stay_in_school;

import android.icu.util.Calendar;
import android.util.Range;
import java.util.List;

/**
 * A term in the school year that has a start date, end date, and a list of days off.
 */
class SchoolTerm {
    private Calendar classesStartDate;
    private Calendar classesEndDate;

    private List<Range<Calendar>> noClassesRanges;

    public SchoolTerm(Calendar classesStartDate, Calendar classesEndDate, List<Range<Calendar>> noClassesRanges) {
        this.classesStartDate = classesStartDate;
        this.classesEndDate = classesEndDate;
        this.noClassesRanges = noClassesRanges;
    }

    public Calendar getClassesStartDate() {
        return classesStartDate;
    }

    public void setClassesStartDate(Calendar classesStartDate) {
        this.classesStartDate = classesStartDate;
    }

    public Calendar getClassesEndDate() {
        return classesEndDate;
    }

    public void setClassesEndDate(Calendar classesEndDate) {
        this.classesEndDate = classesEndDate;
    }

    public List<Range<Calendar>> getNoClassesRanges() {
        return noClassesRanges;
    }

    public void setNoClassesRanges(List<Range<Calendar>> noClassesRanges) {
        this.noClassesRanges = noClassesRanges;
    }
}
