/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import dbutil.DBUtil;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 * @author Asus
 */
public class BookingServiceDAO {
    // Insert new booking service
//    public int insertBookingService(int bookingID, int serviceID, int quantity, Date serviceDate) {
//        Connection cn = null;
//        int result = 0;
//        try {
//            cn = DBUtil.getConnection();
//            if (cn != null) {
//                String sql = "INSERT INTO BOOKING_SERVICE (BookingID, ServiceID, Quantity, ServiceDate, status) "
//                           + "VALUES (?, ?, ?, ?, 0)"; // status 0 for pending
//                PreparedStatement st = cn.prepareStatement(sql);
//                st.setInt(1, bookingID);
//                st.setInt(2, serviceID);
//                st.setInt(3, quantity);
//                st.setDate(4, serviceDate);
//                result = st.executeUpdate();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (cn != null) cn.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return result;
//    }
    public int insertBookingService(int bookingID, int serviceID, int quantity) {
    Connection cn = null;
    int result = 0;
    try {
        cn = DBUtil.getConnection();
        if (cn != null) {
            String sql = "INSERT INTO BOOKING_SERVICE (BookingID, ServiceID, Quantity, RequestDate, Status) "
                       + "VALUES (?, ?, ?, GETDATE(), 0)"; // 0 = pending
            PreparedStatement st = cn.prepareStatement(sql);
            st.setInt(1, bookingID);
            st.setInt(2, serviceID);
            st.setInt(3, quantity);
            result = st.executeUpdate();
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try { if (cn != null) cn.close(); } catch (Exception e) {}
    }
    return result;
}
    
        public double getServiceTotalByBooking(int bookingID) {
        double total = 0;
        Connection cn = null;
        try {
            cn = DBUtil.getConnection();
            if (cn != null) {
                String sql = "SELECT SUM(s.Price * bs.Quantity) AS ServiceTotal "
                           + "FROM BOOKING_SERVICE bs "
                           + "JOIN SERVICE s ON bs.ServiceID = s.ServiceID "
                           + "WHERE bs.BookingID = ? AND bs.Status = 1";  // Chỉ tính dịch vụ đã giao
                PreparedStatement st = cn.prepareStatement(sql);
                st.setInt(1, bookingID);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    total = rs.getDouble("ServiceTotal");
                    if (rs.wasNull()) total = 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (cn != null) cn.close(); } catch (Exception e) {}
        }
        return total;
    }
}
