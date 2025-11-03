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
public class Invoice {
//     private int invoiceID;
//    private int bookingID;
//    private Date issueDate;
//    private double totalAmount;
//    private String status;  
//
//    public Invoice() {
//        invoiceID=0;
//        bookingID=0;
//        issueDate=null;
//        totalAmount=0.0;
//        status="";
//    }
//
//    public Invoice(int invoiceID, int bookingID, Date issueDate, double totalAmount, String status) {
//        this.invoiceID = invoiceID;
//        this.bookingID = bookingID;
//        this.issueDate = issueDate;
//        this.totalAmount = totalAmount;
//        this.status = status;
//    }
//
//    public int getInvoiceID() {
//        return invoiceID;
//    }
//
//    public void setInvoiceID(int invoiceID) {
//        this.invoiceID = invoiceID;
//    }
//
//    public int getBookingID() {
//        return bookingID;
//    }
//
//    public void setBookingID(int bookingID) {
//        this.bookingID = bookingID;
//    }
//
//    public Date getIssueDate() {
//        return issueDate;
//    }
//
//    public void setIssueDate(Date issueDate) {
//        this.issueDate = issueDate;
//    }
//
//    public double getTotalAmount() {
//        return totalAmount;
//    }
//
//    public void setTotalAmount(double totalAmount) {
//        this.totalAmount = totalAmount;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//    
//    
//    
//}
    private int invoiceID;
    private int bookingID;
    private Date invoiceDate;
    private double subtotal;
    private double taxAmount;
    private double totalAmount;

    // Constructor đầy đủ
    public Invoice(int invoiceID, int bookingID, Date invoiceDate, double subtotal, double taxAmount, double totalAmount) {
        this.invoiceID = invoiceID;
        this.bookingID = bookingID;
        this.invoiceDate = invoiceDate;
        this.subtotal = subtotal;
        this.taxAmount = taxAmount;
        this.totalAmount = totalAmount;
    }

    // Constructor không ID (khi insert)
    public Invoice(int bookingID, Date invoiceDate, double subtotal, double taxAmount, double totalAmount) {
        this(0, bookingID, invoiceDate, subtotal, taxAmount, totalAmount);
    }

    // Getters and Setters
    public int getInvoiceID() { return invoiceID; }
    public void setInvoiceID(int invoiceID) { this.invoiceID = invoiceID; }

    public int getBookingID() { return bookingID; }
    public void setBookingID(int bookingID) { this.bookingID = bookingID; }

    public Date getInvoiceDate() { return invoiceDate; }
    public void setInvoiceDate(Date invoiceDate) { this.invoiceDate = invoiceDate; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    public double getTaxAmount() { return taxAmount; }
    public void setTaxAmount(double taxAmount) { this.taxAmount = taxAmount; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
}