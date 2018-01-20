package kz.edu.sdu.rauanassabayev.kundelik.Models;

/**
 * Created by rauanassabayev on 9/26/17.
 */

public class WeekScheduleItem {
    String day;

    public WeekScheduleItem(String day, String count) {
        this.day = day;
        this.count = count;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    String count;
}
