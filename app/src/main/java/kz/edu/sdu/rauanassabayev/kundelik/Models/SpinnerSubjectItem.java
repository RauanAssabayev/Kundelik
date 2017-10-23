package kz.edu.sdu.rauanassabayev.kundelik.Models;

/**
 * Created by rauanassabayev on 10/14/17.
 */

public class SpinnerSubjectItem {
    String text;
    Integer imageId;
    public SpinnerSubjectItem(String text, Integer imageId){
        this.text=text;
        this.imageId=imageId;
    }

    public String getText(){
        return text;
    }
    public Integer getImageId(){
        return imageId;
    }
}
