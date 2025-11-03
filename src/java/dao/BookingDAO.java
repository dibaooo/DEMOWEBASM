/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import dto.Booking;
import dbutil.DBUtil;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
/**
 *
 * @author Asus
 */
public class BookingDAO {
// Insert new booking
public int insertBooking(Booking booking) {
    Connection cn = null;
    int result = 0;
    try {
        cn = DBUtil.getConnection();
        if (cn != null) {
            String sql = "INSERT INTO BOOKING (GuestID, RoomID, CheckInDate, CheckOutDate, BookingDate, Status, TotalAmount) "
                       + "VALUES (?, ?, ?, ?, GETDATE(), 'Reserved', ?)";
            PreparedStatement st = cn.prepareStatement(sql);
            st.setInt(1, booking.getGuestID());
            st.setInt(2, booking.getRoomID());
            st.setDate(3, booking.getCheckInDate());
            st.setDate(4, booking.getCheckOutDate());
            st.setDouble(5, booking.getTotalAmount()); // Nếu có tính trước
            result = st.executeUpdate();
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try { if (cn != null) cn.close(); } catch (Exception e) {}
    }
    return result;
}

    // Get bookings for a guest that are not checked out
public ArrayList<Booking> getActiveBookings(int guestID) {
    ArrayList<Booking> result = new ArrayList<>();
    Connection cn = null;
    try {
        cn = DBUtil.getConnection();
        if (cn != null) {
//            String sql = "SELECT BookingID, RoomID, CheckInDate, CheckOutDate, Status "
//                       + "FROM BOOKING "
//                       + "WHERE GuestID = ? "
//                       + "AND Status IN ('Reserved', 'Checked-in') "
//                       + "AND CheckOutDate >= CAST(GETDATE() AS DATE)";  // Không quá khứ
String sql = "SELECT BookingID, RoomID, CheckInDate, CheckOutDate, Status "
           + "FROM BOOKING "
           + "WHERE GuestID = ? "
           + "AND Status IN ('Reserved', 'Checked-in') "
           + "AND CAST(CheckOutDate AS DATE) >= CAST(GETDATE() AS DATE)";
            PreparedStatement st = cn.prepareStatement(sql);
            st.setInt(1, guestID);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Booking b = new Booking(
                    rs.getInt("BookingID"),
                    guestID,
                    rs.getInt("RoomID"),
                    rs.getDate("CheckInDate"),
                    rs.getDate("CheckOutDate"),
                    null,
                    rs.getString("Status")
                );
                result.add(b);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try { if (cn != null) cn.close(); } catch (Exception e) {}
    }
    return result;
}
    // Update booking status
    public int updateBookingStatus(int bookingID, String newStatus) {
        Connection cn = null;
        int result = 0;
        try {
            cn = DBUtil.getConnection();
            if (cn != null) {
                String sql = "UPDATE BOOKING SET Status = ? WHERE BookingID = ?";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setString(1, newStatus);
                st.setInt(2, bookingID);
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
    
// Lấy danh sách booking theo trạng thái
public ArrayList<Booking> getBookingsByStatus(String status) {
    ArrayList<Booking> list = new ArrayList<>();
    Connection cn = null;
    try {
        cn = DBUtil.getConnection();
        if (cn != null) {
            String sql = "SELECT BookingID, GuestID, RoomID, CheckInDate, CheckOutDate, Status, TotalAmount "
                       + "FROM BOOKING WHERE Status = ?";
            PreparedStatement st = cn.prepareStatement(sql);
            st.setString(1, status);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Booking b = new Booking(
                    rs.getInt("BookingID"),
                    rs.getInt("GuestID"),
                    rs.getInt("RoomID"),
                    rs.getDate("CheckInDate"),
                    rs.getDate("CheckOutDate"),
                    rs.getString("Status"),
                    rs.getDouble("TotalAmount")  // Dùng constructor 7 tham số
                );
                list.add(b);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try { if (cn != null) cn.close(); } catch (Exception e) {}
    }
    return list;
}

// Lấy 1 booking theo ID
public Booking getBooking(int bookingID) {
    Connection cn = null;
    Booking b = null;
    try {
        cn = DBUtil.getConnection();
        if (cn != null) {
            String sql = "SELECT BookingID, GuestID, RoomID, CheckInDate, CheckOutDate, Status, TotalAmount "
                       + "FROM BOOKING WHERE BookingID = ?";
            PreparedStatement st = cn.prepareStatement(sql);
            st.setInt(1, bookingID);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                b = new Booking(
                    rs.getInt("BookingID"),
                    rs.getInt("GuestID"),
                    rs.getInt("RoomID"),
                    rs.getDate("CheckInDate"),
                    rs.getDate("CheckOutDate"),
                    rs.getString("Status"),
                    rs.getDouble("TotalAmount")  // Dùng constructor 7 tham số
                );
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try { if (cn != null) cn.close(); } catch (Exception e) {}
    }
    return b;
}
}