package com.example.chathuranga.sonitdriverapp.models;

/**
 * Created by pamba on 11/2/2015.
 */
public class
        ReservationShort {

    String pickAddress;
    String dropAddress;
    int reservationID;


    public ReservationShort(String pickAddress, String dropAddress,int reservationID) {
        this.pickAddress = pickAddress;
        this.dropAddress = dropAddress;
        this.reservationID = reservationID;
    }

    public ReservationShort() {
    }

    public String getPickAddress() {
        return pickAddress;
    }

    public void setPickAddress(String pickAddress) {
        this.pickAddress = pickAddress;
    }

    public String getDropAddress() {
        return dropAddress;
    }

    public void setDropAddress(String dropAddress) {
        this.dropAddress = dropAddress;
    }

    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }
}
