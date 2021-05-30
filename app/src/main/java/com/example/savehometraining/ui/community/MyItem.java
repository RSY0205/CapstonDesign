package com.example.savehometraining.ui.community;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;

import com.example.savehometraining.R;

public class MyItem {

    private Drawable icon;
    private String name;
    private String contents;
    private String date;
    private int comments_number;
    private int button_life=0;

    public Drawable getIcon() {
        return icon;
    }
    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
    }
    public String getDate() { return date; }
    public void setDate(String date) {
        this.date = date;
    }
    public String getContents() {
        return contents;
    }
    public void setContents(String contents) {
        this.contents = contents;
    }
    public int getbutton_life() { return button_life; }
    public void setbutton_life(int button_life) { this.button_life = button_life; }
    public int getComments_number(){return comments_number;}
    public void setComments_number(int comments_number){this.comments_number=comments_number;}



}