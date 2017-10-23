package kz.edu.sdu.rauanassabayev.kundelik.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by rauanassabayev on 10/20/17.
 */

public class Subject extends RealmObject {
    @PrimaryKey
    private String id;
    private int day;
    private int number;
    @Required
    private String name;
    @Required
    private String timeTo;
    @Required
    private String timeFrom;
    private int notesCount;
    private int filesCount;

    public Subject() {
    }

    public Subject createSubject( int day, int number, String name, String timeTo, String timeFrom, int notesCount, int filesCount) {
        this.day = day;
        this.number = number;
        this.name = name;
        this.timeTo = timeTo;
        this.timeFrom = timeFrom;
        this.notesCount = notesCount;
        this.filesCount = filesCount;
        return this;
    }

    public Subject(int day, int number, String name, String timeTo, String timeFrom, int notesCount, int filesCount) {
        this.day = day;
        this.number = number;
        this.name = name;
        this.timeTo = timeTo;
        this.timeFrom = timeFrom;
        this.notesCount = notesCount;
        this.filesCount = filesCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public int getNotesCount() {
        return notesCount;
    }

    public void setNotesCount(int notesCount) {
        this.notesCount = notesCount;
    }

    public int getFilesCount() {
        return filesCount;
    }

    public void setFilesCount(int filesCount) {
        this.filesCount = filesCount;
    }


}