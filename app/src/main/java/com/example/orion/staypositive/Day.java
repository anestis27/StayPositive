package com.example.orion.staypositive;

import java.util.Date;

/**
 * Created by Orion on 21/4/2016.
 */
public class Day {

    private String date;
    private int color;
    private String note1,note2,note3;

    public Day(String date, int color, String note1, String note2, String note3) {
        this.date = date;
        this.color = color;
        this.note1 = note1;
        this.note2 = note2;
        this.note3 = note3;
    }

    public Day() {}

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getNote1() {
        return note1;
    }

    public void setNote1(String note1) {
        this.note1 = note1;
    }

    public String getNote2() {
        return note2;
    }

    public void setNote2(String note2) {
        this.note2 = note2;
    }

    public String getNote3() {
        return note3;
    }

    public void setNote3(String note3) {
        this.note3 = note3;
    }
}
