package com.smhrd.DangBody;

public class WalkRecord {
    String time;
    String meters;
//    String image;
    String date;

    public WalkRecord(String time, String meters, String date) {
        this.time = time;
        this.meters = meters;
//        this.image = image;
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() { return date;}

    public void setDate(String date) { this.date = date;}

    public String getMeters() {
        return meters;
    }

    public void setMeters(String meters) {
        this.meters = meters;
    }

//    public String getImage() {
//        return image;
//    }
//
//    public void setImage(String image) {
//        this.image = image;
//    }
}
