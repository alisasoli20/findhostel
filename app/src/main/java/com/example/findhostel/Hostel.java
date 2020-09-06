package com.example.findhostel;

import android.net.Uri;

public class Hostel {
    private String hostelName;
    private String hostelAddress;
    private String rentPerPerson;
    private String numberOfRooms;
    private String hostelType;
    private String phoneNumber;
    private String hostelBuildingType;
    private String hostelImage;
    private String peopleCapacity;
    public String getHostelName() {
        return hostelName;
    }

    public void setHostelName(String hostelName) {
        this.hostelName = hostelName;
    }

    public String getHostelAddress() {
        return hostelAddress;
    }

    public void setHostelAddress(String hostelAddress) {
        this.hostelAddress = hostelAddress;
    }

    public String getRentPerPerson() {
        return rentPerPerson;
    }

    public void setRentPerPerson(String rentPerPerson) {
        this.rentPerPerson = rentPerPerson;
    }

    public String getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(String numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public String getHostelType() {
        return hostelType;
    }

    public void setHostelType(String hostelType) {
        this.hostelType = hostelType;
    }

    public String getHostelBuildingType() {
        return hostelBuildingType;
    }

    public void setHostelBuildingType(String hostelBuildingType) {
        this.hostelBuildingType = hostelBuildingType;
    }

    public String getHostelImage() {
        return hostelImage;
    }

    public void setHostelImage(String hostelImage) {
        this.hostelImage = hostelImage;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPeopleCapacity() {
        return peopleCapacity;
    }

    public void setPeopleCapacity(String peopleCapacity) {
        this.peopleCapacity = peopleCapacity;
    }
}
