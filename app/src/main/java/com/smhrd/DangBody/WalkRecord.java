package com.smhrd.DangBody;

public class WalkRecord {
    String time;
    String meters;
    String image;

    public WalkRecord(String time, String meters, String image) {
        this.time = time;
        this.meters = meters;
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMeters() {
        return meters;
    }

    public void setMeters(String meters) {
        this.meters = meters;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
