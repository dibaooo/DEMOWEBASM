/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.BookingDAO;
import dao.BookingServiceDAO;
import dao.ServiceDAO;
import dbutil.IConstant;
import dto.Booking;
import dto.Guest;
import dto.Service;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
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
@WebServlet(name="RequestServicesController", urlPatterns={"/RequestServicesController"})
public class RequestServicesController extends HttpServlet {
   
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
        Guest guest = (Guest) session.getAttribute("guest");

        // === BẮT BUỘC PHẢI LOGIN GUEST ===
        if (guest == null) {
            request.setAttribute("error", "Please login to request services.");
            request.getRequestDispatcher(url).forward(request, response);
            return;
        }

        BookingDAO bookingDAO = new BookingDAO();
        ServiceDAO serviceDAO = new ServiceDAO();
        BookingServiceDAO bsDAO = new BookingServiceDAO();

        try {
            if (request.getMethod().equals("GET")) {
                // === HIỂN THỊ FORM: LẤY BOOKING CỦA GUEST + TẤT CẢ SERVICES ===
                ArrayList<Booking> bookings = bookingDAO.getActiveBookings(guest.getGuestID());
                ArrayList<Service> services = serviceDAO.getAllServices();

                request.setAttribute("bookings", bookings);
                request.setAttribute("services", services);
                url = IConstant.REQUEST_SERVICES_PAGE;

            } else if (request.getMethod().equals("POST")) {
                // === XỬ LÝ YÊU CẦU DỊCH VỤ ===
                int bookingID = Integer.parseInt(request.getParameter("bookingID"));
                String[] serviceIDs = request.getParameterValues("serviceID");
                String[] quantities = request.getParameterValues("quantity");

                if (serviceIDs != null && quantities != null) {
                    for (int i = 0; i < serviceIDs.length; i++) {
                        int sid = Integer.parseInt(serviceIDs[i]);
                        int qty = Integer.parseInt(quantities[i]);
                        if (qty > 0) {
                            bsDAO.insertBookingService(bookingID, sid, qty);
                        }
                    }
                }
                url = IConstant.SERVICES_SUCCESS_PAGE;
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Service request failed: " + e.getMessage());
            url = IConstant.ERROR_PAGE;
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
