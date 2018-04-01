package com.example.marwa.androidproject;

/**
 * Created by marwa on 24/03/18.
 */

public class Trip {
    private String tripName;
    private String startPoint;
    private String endPoint;
    private String notes;
    private String tripType;
    private String date;
    private Boolean flag;
    //private  int id;


    public Trip() {

    }

    public Trip(String tripName, String tripType, String date,String notes,String startPoint,String endPoint) {
        this.tripType = tripType;
        this.tripName = tripName;
        this.notes = notes;
        this.date = date;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public Boolean getFlag(){

        return flag;
    }
    public void setFlag(Boolean flag) {

            this.flag = flag;
        }



    public String getTripName() {

        return tripName;
    }
    public void setTripName(String name) {
        if(name!="") {
            this.tripName = name;
        }
    }

    public String getStartPoint() {
        return startPoint;
    }
    public void setStartPoint(String start)
    { if(start!=null) {
        this.startPoint = start;
    }
    }

    public String getEndPoint() {
        return endPoint;
    }
    public void setEndPoint(String end) {
        if(end!=null) {
            this.endPoint = end;
        }
    }


    public String getNotes() {
        return notes;
    }
    public void setNotes(String note)
    {
        if (note!=null) {
            this.notes = note;
        }
    }


    public String getTripType() {
        return tripType;
    }
    public void setTripType(String type)
    { if (type!=null) {
        this.tripType = type;
    }
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date)
    {if (date!=null) {
        this.date = date;
    }
    }






}
