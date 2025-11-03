/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.Guest;
import dbutil.DBUtil; 
import dbutil.PasswordUtil;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Asus
 */
public class GuestDAO {
// Login: Get guest by email and password
    public Guest getGuest(String email, String password) {
        Connection cn = null;
        Guest result = null;
        try {
            cn = DBUtil.getConnection();
            if (cn != null) {
                String sql = "SELECT GuestID, FullName, Phone, Email, Address, IDNumber, DateOfBirth, PasswordHash "
                           + "FROM GUEST WHERE Email = ? AND PasswordHash = ?";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setString(1, email);
                // Hash input password
                String hashedPassword = PasswordUtil.hashPassword(password);
                st.setString(2, hashedPassword);
                ResultSet table = st.executeQuery();
                if (table != null && table.next()) {
                    int guestID = table.getInt("GuestID");
                    String fullName = table.getString("FullName");
                    String phone = table.getString("Phone");
                    String address = table.getString("Address");
                    String idNumber = table.getString("IDNumber");
                    Date dateOfBirth = table.getDate("DateOfBirth");
                    String passwordHash = table.getString("PasswordHash");
                    result = new Guest(guestID, fullName, phone, email, address, idNumber, dateOfBirth, passwordHash);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (cn != null) cn.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return result;
    }

    // Register: Insert new guest
   public int insertGuest(Guest guest) {
    Connection cn = null;
    int result = 0;
    try {
        cn = DBUtil.getConnection();
        if (cn != null) {
            String sql = "INSERT INTO GUEST (FullName, Phone, Email, Address, IDNumber, DateOfBirth, PasswordHash) "
                       + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement st = cn.prepareStatement(sql);
            st.setString(1, guest.getFullName());
            st.setString(2, guest.getPhone());
            st.setString(3, guest.getEmail());
            st.setString(4, guest.getAddress());
            st.setString(5, guest.getIdNumber());
            st.setDate(6, guest.getDateOfBirth());
            // Hash password before insert
            String hashedPassword = PasswordUtil.hashPassword(guest.getPasswordHash());
            st.setString(7, hashedPassword);
            result = st.executeUpdate();
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try { if (cn != null) cn.close(); } catch (Exception e) { e.printStackTrace(); }
    }
    return result;
}

    // Check duplicate email or phone
    public boolean checkDuplicate(String value) {
        Connection cn = null;
        boolean result = false;
        try {
            cn = DBUtil.getConnection();
            if (cn != null) {
                String sql = "SELECT * FROM GUEST WHERE Email = ? OR Phone = ?";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setString(1, value);
                st.setString(2, value);
                ResultSet table = st.executeQuery();
                if (table != null && table.next()) {
                    result = true;
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
}