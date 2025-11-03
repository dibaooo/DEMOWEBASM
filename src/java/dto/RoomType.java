/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author Asus
 */
public class RoomType {
    private int roomTypeID;
    private String typeName;
    private int capacity;
    private double pricePerNight;

    public RoomType(int roomTypeID, String typeName, int capacity, double pricePerNight) {
        this.roomTypeID = roomTypeID;
        this.typeName = typeName;
        this.capacity = capacity;
        this.pricePerNight = pricePerNight;
    }

    // Getters/Setters...
    public int getRoomTypeID() { return roomTypeID; }
    public void setRoomTypeID(int roomTypeID) { this.roomTypeID = roomTypeID; }
    public String getTypeName() { return typeName; }
    public void setTypeName(String typeName) { this.typeName = typeName; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public double getPricePerNight() { return pricePerNight; }
    public void setPricePerNight(double pricePerNight) { this.pricePerNight = pricePerNight; }
}
