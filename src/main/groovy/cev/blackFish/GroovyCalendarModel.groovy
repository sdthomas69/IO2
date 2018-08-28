package cev.blackFish

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

class GroovyCalendarModel {
    
    private Date date;
    private List<ArrayList<CalendarDay>> rows = new ArrayList<ArrayList<CalendarDay>>();
    protected int month;
    protected int year;
    private Integer nextMonth;
    private Integer nextYear;
    private Integer previousMonth;
    private Integer previousYear;
    private Integer currentMonth;
    private Integer currentYear;
    
    public GroovyCalendarModel() {}

    /**
     * Creates a Model for the given month and year. Builds a 7x5 or 7x6
     * grid that is full of empty days.
     *
     * @param month 0-11 (0 = January)
     * @param year
     */
    public GroovyCalendarModel(int month, int year) {
        
        this.month = month;
        this.year = year;
        Calendar m = Calendar.getInstance();
        m.set(year, month,  1);
        date = m.getTime();
        int dayOfWeek = m.get(Calendar.DAY_OF_WEEK);
        int offsets = 0;
        int currentDay = 0;
        boolean done = false;
        
        for(int i = 0; i < 5; i++) {
            ArrayList<CalendarDay> row = new ArrayList<CalendarDay>();
            for(int j = 0; j < 7; j++) {
                offsets++;
                if (offsets >= dayOfWeek && !done){
                    currentDay++;
                }
                row.add(new CalendarDay(currentDay));
                m.set(year, month, currentDay+1);
                if (m.get(Calendar.MONTH) > month || m.get(Calendar.YEAR) > year){
                    currentDay = 0;
                    done = true;
                } else {
                    m.add(Calendar.DATE, 1);
                }
            }
            rows.add(row);
        }

        // In case another week is needed
        if (!done) {
            ArrayList<CalendarDay> row = new ArrayList<CalendarDay>();
            for(int j = 0; j < 7; j++) {
                if (!done){
                    currentDay++;
                }
                row.add(new CalendarDay(currentDay));
                m.set(year, month, currentDay+1);
                if (m.get(Calendar.MONTH) > month || m.get(Calendar.YEAR) > year){
                    currentDay = 0;
                    done = true;
                } else {
                    m.add(Calendar.DATE, 1);
                }
            }
            rows.add(row);
        }
        initNavigationDates();
    }

    /**
     * Sorts a list of Events into their proper days.
     * @param events
     */
    public void setEvents(List events) {
        Calendar date = Calendar.getInstance();
        date.clear();
        // Put each event into the correct day.
        for (Iterator it = events.iterator(); it.hasNext();) {
            def ev = it.next();
            date.setTime(ev.getDate());
            int weekOfMonth = date.get(Calendar.WEEK_OF_MONTH) - 1;
            int dayOfWeek = date.get(Calendar.DAY_OF_WEEK) - 1;
            ArrayList<CalendarDay> row = getRow(weekOfMonth);
            CalendarDay day = (CalendarDay) row.get(dayOfWeek);
            day.addEvent(ev);
        }
    }
    
    protected void initNavigationDates() {
        
        Calendar next = DateUtils.createCalendarMonth(month + 1, 1, this.year);
        nextMonth = new Integer(next.get(Calendar.MONTH));
        nextYear = new Integer(next.get(Calendar.YEAR));

        Calendar previous = DateUtils.createCalendarMonth(month - 1, 1, this.year);
        previousMonth = new Integer(previous.get(Calendar.MONTH));
        previousYear = new Integer(previous.get(Calendar.YEAR));
        
        Calendar current = DateUtils.createCalendarMonth(month, 1, this.year);
        currentMonth = new Integer(current.get(Calendar.MONTH));
        currentYear = new Integer(current.get(Calendar.YEAR));
    }
    
    /**
     * Iterated over by the View.
     * @return
     */
    public List<ArrayList<CalendarDay>> getRows() {
        return rows;
    }

    /**
     * Get row at the given index.
     * @param rowNumber
     * @return
     */
    private ArrayList<CalendarDay> getRow(int rowNumber){
        return rows.get(rowNumber);
    }
    
    public Integer getCurrentYear() {
        return this.currentYear;
    }
    
    public Integer getCurrentMonth() {
        return this.currentMonth;
    }
    
    public Integer getNextMonth() {
        return this.nextMonth;
    }
    
    public Integer getNextYear() {
        return this.nextYear;
    }
    
    public Integer getPreviousMonth() {
        return this.previousMonth;
    }
    
    public Integer getPreviousYear() {
        return this.previousYear;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }

}
