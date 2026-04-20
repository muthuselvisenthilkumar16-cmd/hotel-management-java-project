package com.hotel.management;

public class Booking {

    private String customerName;
    private String roomNumber;
    private String date;
    private String phone;
    private String email;

    public Booking(String customerName, String roomNumber, String date, String phone, String email) {
        this.customerName = customerName;
        this.roomNumber = roomNumber;
        this.date = date;
        this.phone = phone;
        this.email = email;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getDate() {
        return date;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}