package rivas.itzel.com.testingcoordinates;

import java.io.Serializable;

public class Report implements Serializable {

    private long time;
    private String incident;
    private double longitude;
    private double latitude;
    private String description;
    private byte [] image;

    public Report(){
        this.time = System.currentTimeMillis();
    }

    public Report(String incident){
        this.time = System.currentTimeMillis();
        this.incident = incident;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getIncident() {
        return incident;
    }

    public void setIncident(String incident) {
        this.incident = incident;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public byte[] getImage() {
//        return image;
//    }
//
//    public void setImage(byte[] image) {
//        this.image = image;
//    }


}
