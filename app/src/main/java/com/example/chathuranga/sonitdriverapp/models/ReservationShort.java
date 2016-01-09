package com.example.chathuranga.sonitdriverapp.models;

/**
 * Created by pamba on 11/2/2015.
 */
public class
        ReservationShort {

    String pickAddress;
    int reservationID;
    double pickupLat, pickuplong;


    public ReservationShort() {
    }

    public ReservationShort(String pickAddress, int reservationID, double pickupLat, double pickuplong) {
        this.pickAddress = pickAddress;
        this.reservationID = reservationID;
        this.pickupLat = pickupLat;
        this.pickuplong = pickuplong;
    }

    public String getPickAddress() {
        return pickAddress;
    }

    public void setPickAddress(String pickAddress) {
        this.pickAddress = pickAddress;
    }

    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public double getPickupLat() {
        return pickupLat;
    }

    public void setPickupLat(double pickupLat) {
        this.pickupLat = pickupLat;
    }

    public double getPickuplong() {
        return pickuplong;
    }

    public void setPickuplong(double pickuplong) {
        this.pickuplong = pickuplong;
    }
}
