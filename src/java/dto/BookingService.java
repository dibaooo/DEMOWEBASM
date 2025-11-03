/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.sql.Date;

/**
 *
 * @author Asus
 */
public class BookingService {
    private int bookingServiceID;
    private int bookingID;
    private int serviceID;
    private int quantity;
    private Date serviceDate;
    private int status;


    public BookingService(int bookingServiceID, int bookingID, int serviceID, int quantity, Date serviceDate, int status) {
        this.bookingServiceID = bookingServiceID;
        this.bookingID = bookingID;
        this.serviceID = serviceID;
        this.quantity = quantity;
        this.serviceDate = serviceDate;
        this.status = status;
    }

    public BookingService() {
        bookingServiceID=0;
        bookingID=0;
        serviceID=0;
        quantity=0;
        serviceDate=null;
        status=0;
    }

    public int getBookingServiceID() {
        return bookingServiceID;
    }

    public void setBookingServiceID(int bookingServiceID) {
        this.bookingServiceID = bookingServiceID;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(Date serviceDate) {
        this.serviceDate = serviceDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
