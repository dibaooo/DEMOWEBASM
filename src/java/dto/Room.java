/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author Asus
 */
public class Room {
    private int roomID;
    private String roomNumber;
    private int roomTypeID;
    private String status;

   
    public Room(int roomID, String roomNumber, int roomTypeID, String status) {
        this.roomID = roomID;
        this.roomNumber = roomNumber;
        this.roomTypeID = roomTypeID;
        this.status = status;
    }

    
    public Room() {
        roomID = 0;
        roomNumber ="";
        roomTypeID=0;
        status ="";
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getRoomTypeID() {
        return roomTypeID;
    }

    public void setRoomTypeID(int roomTypeID) {
        this.roomTypeID = roomTypeID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
