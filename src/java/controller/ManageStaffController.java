/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.StaffDAO;
import dbutil.IConstant;
import dto.Staff;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Asus
 */
@WebServlet(name="ManageStaffController", urlPatterns={"/ManageStaffController"})
public class ManageStaffController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        
    String url = IConstant.LOGIN_PAGE;

        HttpSession session = request.getSession();
        Staff currentStaff = (Staff) session.getAttribute("staff");

        // === ADMIN CHECK ===
        if (currentStaff == null || !"Admin".equals(currentStaff.getRole())) {
            request.setAttribute("error", "Access denied. Only Admin can manage staff.");
            request.getRequestDispatcher(url).forward(request, response);
            return;
        }

        url = IConstant.MANAGE_STAFF_PAGE; // Default page
        StaffDAO dao = new StaffDAO();

        try {
            String action = request.getParameter("staffAction");

            // === LIST / SEARCH ===
            if ("list".equals(action) || action == null) {
                String searchName = request.getParameter("searchName");
                if (searchName == null) searchName = "";
                ArrayList<Staff> staffs = dao.getStaffs(searchName);
                request.setAttribute("staffs", staffs);
            } 
            // === ADD STAFF ===
            else if ("add".equals(action)) {
                String fullName = request.getParameter("fullName");
                String role = request.getParameter("role");
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                String phone = request.getParameter("phone");
                String email = request.getParameter("email");

                if (fullName == null || username == null || password == null || role == null) {
                    request.setAttribute("error", "All fields are required.");
                } else if (dao.checkDuplicate(username) || dao.checkDuplicate(email)) {
                    request.setAttribute("error", "Username or Email already exists.");
                } else {
                    Staff newStaff = new Staff(0, fullName, role, username, password, phone, email, 1);
                    int result = dao.insertStaff(newStaff);
                    if (result > 0) {
                        request.setAttribute("success", "Staff added successfully.");
                    } else {
                        request.setAttribute("error", "Failed to add staff.");
                    }
                }
                // Reload list
                ArrayList<Staff> staffs = dao.getStaffs("");
                request.setAttribute("staffs", staffs);
            } 
            // === UPDATE STAFF ===
            else if ("update".equals(action)) {
                int staffID = Integer.parseInt(request.getParameter("staffID"));
                String fullName = request.getParameter("fullName");
                String role = request.getParameter("role");
                String password = request.getParameter("password");
                String phone = request.getParameter("phone");

                Staff staff = new Staff(staffID, fullName, role, null, password, phone, null, -1); // status not updated
                int result = dao.updateStaff(staff);
                if (result > 0) {
                    request.setAttribute("success", "Staff updated successfully.");
                } else {
                    request.setAttribute("error", "Failed to update staff.");
                }
                // Reload list
                ArrayList<Staff> staffs = dao.getStaffs("");
                request.setAttribute("staffs", staffs);
            } 
            // === TOGGLE STATUS ===
            else if ("toggleStatus".equals(action)) {
                int staffID = Integer.parseInt(request.getParameter("staffID"));
                int newStatus = Integer.parseInt(request.getParameter("newStatus"));
                int result = dao.updateStaffStatus(staffID, newStatus);
                if (result > 0) {
                    request.setAttribute("success", "Staff status updated.");
                }
                // Reload list
                ArrayList<Staff> staffs = dao.getStaffs("");
                request.setAttribute("staffs", staffs);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
