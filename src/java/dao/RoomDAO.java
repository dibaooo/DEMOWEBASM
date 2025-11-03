/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.Room;
import dbutil.DBUtil;
import dto.RoomType;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Asus
 */
public class RoomDAO {
    
// Search available rooms by date and room type
    public ArrayList<Room> searchAvailableRooms(Date checkIn, Date checkOut, int roomTypeID) {
        ArrayList<Room> result = new ArrayList<>();
        Connection cn = null;
        try {
            cn = DBUtil.getConnection();
            if (cn != null) {
                String sql = "SELECT r.RoomID, r.RoomNumber, r.RoomTypeID, r.Status "
                           + "FROM ROOM r "
                           + "WHERE r.RoomTypeID = ? AND r.Status = 'Available' "
                           + "AND NOT EXISTS ( "
                           + "    SELECT 1 FROM BOOKING b "
                           + "    WHERE b.RoomID = r.RoomID "
                           + "    AND b.Status NOT IN ('Canceled', 'Completed') "
                           + "    AND (b.CheckInDate < ? AND b.CheckOutDate > ?) "
                           + ") ";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setInt(1, roomTypeID);
                st.setDate(2, checkOut);
                st.setDate(3, checkIn);
                ResultSet table = st.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        int roomID = table.getInt("RoomID");
                        String roomNumber = table.getString("RoomNumber");
                        int typeID = table.getInt("RoomTypeID");
                        String status = table.getString("Status");
                        result.add(new Room(roomID, roomNumber, typeID, status));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) cn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    // Other methods if needed, e.g., update room status
    public int updateRoomStatus(int roomID, String newStatus) {
        Connection cn = null;
        int result = 0;
        try {
            cn = DBUtil.getConnection();
            if (cn != null) {
                String sql = "UPDATE ROOM SET Status = ? WHERE RoomID = ?";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setString(1, newStatus);
                st.setInt(2, roomID);
                result = st.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cn != null) cn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    
    
    public ArrayList<RoomType> getAllRoomTypes() {
    ArrayList<RoomType> result = new ArrayList<>();
    Connection cn = null;
    try {
        cn = DBUtil.getConnection();
        if (cn != null) {
            String sql = "SELECT RoomTypeID, TypeName, Capacity, PricePerNight FROM ROOM_TYPE";
            PreparedStatement st = cn.prepareStatement(sql);
            ResultSet table = st.executeQuery();
            while (table.next()) {
                int id = table.getInt("RoomTypeID");
                String name = table.getString("TypeName");
                int cap = table.getInt("Capacity");
                double price = table.getDouble("PricePerNight");
                result.add(new RoomType(id, name, cap, price));
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try { if (cn != null) cn.close(); } catch (Exception e) { e.printStackTrace(); }
    }
    return result;
}
    
    // Lấy giá phòng mỗi đêm theo RoomID
    public double getRoomPricePerNight(int roomID) {
        double price = 0;
        Connection cn = null;
        try {
            cn = DBUtil.getConnection();
            if (cn != null) {
                String sql = "SELECT rt.PricePerNight "
                           + "FROM ROOM r "
                           + "JOIN ROOM_TYPE rt ON r.RoomTypeID = rt.RoomTypeID "
                           + "WHERE r.RoomID = ?";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setInt(1, roomID);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    price = rs.getDouble("PricePerNight");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (cn != null) cn.close(); } catch (Exception e) {}
        }
        return price;
    }
}