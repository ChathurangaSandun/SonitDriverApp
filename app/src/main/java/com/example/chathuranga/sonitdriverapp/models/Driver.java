package com.example.chathuranga.sonitdriverapp.models;

/**
 * Created by chathuranga on 1/11/2016.
 */
public class Driver {
    private String userName;
    private String userType;
    private int driverID;

    public int getDriverID() {
        return driverID;
    }

    public Driver() {
    }

    public void setDriverID(int driverID) {
        this.driverID = driverID;
    }

    public Driver(String userName, String userType) {

        this.userName = userName;
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
