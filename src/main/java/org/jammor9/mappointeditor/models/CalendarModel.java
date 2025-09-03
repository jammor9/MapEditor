package org.jammor9.mappointeditor.models;

import java.util.LinkedHashMap;

public class CalendarModel extends ModelComposite {

    record Month(int monthDuration, int leapDays, int leapDuration) { }

    private static final String CLASS_META_KEY = CalendarModel.class.getCanonicalName();

    private String calendarName;
    private String calendarSuffixPost;
    private String calendarSuffixAnte;
    private LinkedHashMap<String, Month> monthMap;

    public CalendarModel(String calendarName) {
        super(CLASS_META_KEY);
        this.calendarName = calendarName;
        this.monthMap = new LinkedHashMap<>();
    }

    public void addMonth(String monthName, int monthDuration) {
        monthMap.put(monthName, new Month(monthDuration, 0 ,0));
    }

    public void addMonth(String monthName, int monthDuration, int leapDays, int leapDuration) {
        monthMap.put(monthName, new Month(monthDuration, leapDays, leapDuration));    }

    public void removeMonth(String monthName) {
        monthMap.remove(monthName);
    }

    public void setCalendarSuffixPost(String suffix) {
        this.calendarSuffixPost = suffix;
    }

    public String getCalendarSuffixPost() {
        return this.calendarSuffixPost;
    }

    public void setCalendarSuffixAnte(String suffix) {
        this.calendarSuffixAnte = suffix;
    }

    public String getCalendarSuffixAnte() {
        return this.calendarSuffixAnte;
    }

    @Override
    public String toString() {
        return this.calendarName;
    }

    @Override
    public void setName(String s) {
        this.calendarName = s;
    }
}
