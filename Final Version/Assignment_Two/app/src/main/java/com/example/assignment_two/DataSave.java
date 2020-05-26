package com.example.assignment_two;

import android.graphics.Bitmap;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class DataSave {
    public String location = null;
    public int start_year = 0;
    public int start_month = 0;
    public int start_day = 0;
    public int end_year = 0;
    public int end_month = 0;
    public int end_day = 0;
    public String start_date = null;
    public String end_date = null;
    public String adult = null;
    public String child = null;
    public String activity = null;
    public String name= null;
    public Uri imageUri = null;
    public Bitmap bitmap = null;

    public String getLocation(){
        return location;
    }
    public Bitmap getBitmap(){
        return bitmap;
    }
    public String getName(){
        return name;
    }
    public Uri getImageUri(){
        return imageUri;
    }
    public int getStart_year(){
        return start_year;
    }
    public int getStart_month(){
        return start_month;
    }
    public int getStart_day(){
        return start_day;
    }
    public String getStart_date(){
        return start_date;
    }
    public String getEnd_Date(){
        return end_date;
    }
    public int getEnd_year(){
        return end_year;
    }
    public int getEnd_month(){
        return end_month;
    }
    public int getEnd_day(){
        return end_day;
    }
    public String getAdult(){
        return adult;
    }
    public String getChild(){
        return child;
    }
    public String getActivity(){
        return activity;
    }
    public void setLocation(String location){
        this.location = location;
    }
    public void setStart_year(int year) {
        this.start_year = year;
    }
    public void setStart_month(int month) {
        this.start_month = month;
    }
    public void setStart_day(int day) {
        this.start_day = day;
    }
    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
    public void setStart_Date(String start_date){
        this.start_date = start_date;
    }
    public void setEnd_year(int end_year) {
        this.end_year = end_year;
    }
    public void setEnd_month(int end_month) {
        this.end_month = end_month;
    }
    public void setEnd_day(int end_day) {
        this.end_day = end_day;
    }
    public void setAdult(String adult){
        this.adult = adult;
    }
    public void setChild(String child){
        this.child = child;
    }
    public void setActivity(String activity){
        this.activity = activity;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setImageUri(Uri imageUri){
        this.imageUri = imageUri;
    }
    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }

}
