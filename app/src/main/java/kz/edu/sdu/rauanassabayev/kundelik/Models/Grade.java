package kz.edu.sdu.rauanassabayev.kundelik.Models;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;


/**
 * Created by rauanassabayev on 1/21/18.
 */


public class Grade extends RealmObject {
    @PrimaryKey
    private String id;
    @Required
    private String subjectName;
    private int grade;

    public Grade create(String subjectName, int grade, Date createdDate) {
        this.subjectName = subjectName;
        this.grade = grade;
        this.createdDate = createdDate;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Required
    private Date createdDate;

    public Grade() {}
}
