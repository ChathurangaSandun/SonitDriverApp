package com.example.chathuranga.sonitdriverapp.models;

/**
 * Created by Chathuranga-Pamba on 15/11/21.
 */
public class ReservationAllDetals {
    String name;
    int tpNumber;
    String pickUp;
    String dropOff;
    String note;
    int noOfPassengets;

    public ReservationAllDetals(String name, int tpNumber, String pickUp, String dropOff, String note, int noOfPassengets) {
        this.name = name;
        this.tpNumber = tpNumber;
        this.pickUp = pickUp;
        this.dropOff = dropOff;
        this.note = note;
        this.noOfPassengets = noOfPassengets;
    }

    public ReservationAllDetals() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTpNumber() {
        return tpNumber;
    }

    public void setTpNumber(int tpNumber) {
        this.tpNumber = tpNumber;
    }

    public String getPickUp() {
        return pickUp;
    }

    public void setPickUp(String pickUp) {
        this.pickUp = pickUp;
    }

    public String getDropOff() {
        return dropOff;
    }

    public void setDropOff(String dropOff) {
        this.dropOff = dropOff;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getNoOfPassengets() {
        return noOfPassengets;
    }

    public void setNoOfPassengets(int noOfPassengets) {
        this.noOfPassengets = noOfPassengets;
    }
}
