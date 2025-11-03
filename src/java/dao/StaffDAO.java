/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import dto.Staff;
import dbutil.DBUtil;
import dbutil.PasswordUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author Asus
 */
public class StaffDAO {


    // Get staff by username and password (for login)
    public Staff getStaff(String username, String password) {
        Connection cn = null;
        Staff result = null;
        try {
            cn = DBUtil.getConnection();
            if (cn != null) {
                String sql = "SELECT StaffID, FullName, Role, Username, PasswordHash, Phone, Email, Status "
                           + "FROM STAFF WHERE Username = ? AND PasswordHash = ?";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setString(1, username);
                // Hash input
                String hashedPassword = PasswordUtil.hashPassword(password);
                st.setString(2, hashedPassword);
                ResultSet table = st.executeQuery();
                if (table != null && table.next()) {
                    int staffID = table.getInt("StaffID");
                    String fullName = table.getString("FullName");
                    String role = table.getString("Role");
                    String phone = table.getString("Phone");
                    String email = table.getString("Email");
                    int status = table.getInt("Status");
                    result = new Staff(staffID, fullName, role, username, hashedPassword, phone, email, status);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (cn != null) cn.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return result;
    }

    // Insert new staff
    public int insertStaff(Staff staff) {
        Connection cn = null;
        int result = 0;
        try {
            cn = DBUtil.getConnection();
            if (cn != null) {
                String sql = "INSERT INTO STAFF (FullName, Role, Username, PasswordHash, Phone, Email, Status) "
                           + "VALUES (?, ?, ?, ?, ?, ?, 1)";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setString(1, staff.getFullname());
                st.setString(2, staff.getRole());
                st.setString(3, staff.getUsername());
                // Hash password
                String hashedPassword = PasswordUtil.hashPassword(staff.getPassword());
                st.setString(4, hashedPassword);
                st.setString(5, staff.getPhone());
                st.setString(6, staff.getEmail());
                result = st.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (cn != null) cn.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return result;
    }

    // Check duplicate username or email
    public boolean checkDuplicate(String value) {
        Connection cn = null;
        boolean result = false;
        try {
            cn = DBUtil.getConnection();
            if (cn != null) {
                String sql = "SELECT * FROM STAFF WHERE Username = ? OR Email = ?";
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

    // Search staffs by name
    public ArrayList<Staff> getStaffs(String name) {
        ArrayList<Staff> result = new ArrayList<>();
        Connection cn = null;
        try {
            cn = DBUtil.getConnection();
            if (cn != null) {
                String sql = "SELECT StaffID, FullName, Role, Username, PasswordHash, Phone, Email, Status "
                           + "FROM STAFF WHERE FullName LIKE ?";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setString(1, "%" + name + "%");
                ResultSet table = st.executeQuery();
                if (table != null) {
                    while (table.next()) {
                        int staffID = table.getInt("StaffID");
                        String fullName = table.getString("FullName");
                        String role = table.getString("Role");
                        String username = table.getString("Username");
                        String password = table.getString("PasswordHash");
                        String phone = table.getString("Phone");
                        String email = table.getString("Email");
                        int status = table.getInt("Status");
                        Staff staff = new Staff(staffID, fullName, role, username, password, phone, email, status);
                        result.add(staff);
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

    // Get staff by ID
    public Staff getStaff(int staffID) {
        Connection cn = null;
        Staff result = null;
        try {
            cn = DBUtil.getConnection();
            if (cn != null) {
                String sql = "SELECT StaffID, FullName, Role, Username, PasswordHash, Phone, Email, Status "
                           + "FROM STAFF WHERE StaffID = ?";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setInt(1, staffID);
                ResultSet table = st.executeQuery();
                if (table != null && table.next()) {
                    String fullName = table.getString("FullName");
                    String role = table.getString("Role");
                    String username = table.getString("Username");
                    String password = table.getString("PasswordHash");
                    String phone = table.getString("Phone");
                    String email = table.getString("Email");
                    int status = table.getInt("Status");
                    result = new Staff(staffID, fullName, role, username, password, phone, email, status);
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

    // Update staff (example: update name, role, password, phone; username/email not updated)
    public int updateStaff(Staff staff) {
        Connection cn = null;
        int result = 0;
        try {
            cn = DBUtil.getConnection();
            if (cn != null) {
                String sql = "UPDATE STAFF SET FullName = ?, Role = ?, PasswordHash = ?, Phone = ? "
                           + "WHERE StaffID = ?";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setString(1, staff.getFullname());
                st.setString(2, staff.getRole());
                st.setString(3, staff.getPassword());
                st.setString(4, staff.getPhone());
                st.setInt(5, staff.getStaffid());
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

    // Update staff status (active/inactive)
    public int updateStaffStatus(int staffID, int newStatus) {
        Connection cn = null;
        int result = 0;
        try {
            cn = DBUtil.getConnection();
            if (cn != null) {
                String sql = "UPDATE STAFF SET Status = ? WHERE StaffID = ?";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setInt(1, newStatus);
                st.setInt(2, staffID);
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
    
    
}
