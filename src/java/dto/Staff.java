/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author Asus
 */
public class Staff {
private int staffid;
    private String fullname;
    private String role;
    private String username;
    private String password;
    private String phone;
    private String email;
    private int status;  // 1: Active, 0: Inactive

    // Full constructor
    public Staff(int staffid, String fullname, String role, String username, String password, String phone, String email, int status) {
        this.staffid = staffid;
        this.fullname = fullname;
        this.role = role;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.status = status;
    }

    // Constructor without status (default to 1 in DAO)
    public Staff(int staffid, String fullname, String role, String username, String password, String phone, String email) {
        this(staffid, fullname, role, username, password, phone, email, 1);
    }

    // Default constructor
    public Staff() {}

    // Getters and Setters
    public int getStaffid() {
        return staffid;
    }

    public void setStaffid(int staffid) {
        this.staffid = staffid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}