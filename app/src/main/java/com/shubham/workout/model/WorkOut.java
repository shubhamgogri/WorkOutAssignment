package com.shubham.workout.model;

import android.graphics.Bitmap;

import java.sql.Array;
import java.util.ArrayList;

public class WorkOut {

    private String title;
    private String coach;
    private String difficulty;
    private String duration;
    private Bitmap image;


    ArrayList<String> equipments;

    public WorkOut() {
    }


    public ArrayList<String> getEquipments() {
        return equipments;
    }

    public void setEquipments(ArrayList<String> equipments) {
        this.equipments = equipments;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
