package kz.edu.sdu.rauanassabayev.kundelik.Models;

import java.util.Date;

import javax.annotation.Nonnull;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by rauanassabayev on 1/10/18.
 */

public class HomeWork extends RealmObject {
    @PrimaryKey
    private String id;
    @Required
    private String subjectName;
    private boolean notifyDayBefore;
    @Required
    private Date notifyTime;
    @Required
    private String hwText;
    @Required
    private Date createdDate;

    public HomeWork(){}

    public HomeWork createHW(String subjectName, boolean notifyDayBefore, Date notifyTime, String hwText, Date createdDate) {
        this.subjectName = subjectName;
        this.notifyDayBefore = notifyDayBefore;
        this.notifyTime = notifyTime;
        this.hwText = hwText;
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

    public boolean isNotifyDayBefore() {
        return notifyDayBefore;
    }

    public void setNotifyDayBefore(boolean notifyDayBefore) {
        this.notifyDayBefore = notifyDayBefore;
    }

    public Date getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(Date notifyTime) {
        this.notifyTime = notifyTime;
    }

    public String getHwText() {
        return hwText;
    }

    public void setHwText(String hwText) {
        this.hwText = hwText;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
