/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.BookingDAO;
import dao.RoomDAO;
import dbutil.IConstant;
import dto.Booking;
import dto.Guest;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
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
@WebServlet(name="MakeBookingController", urlPatterns={"/MakeBookingController"})
public class MakeBookingController extends HttpServlet {
   
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
        
        
        String url = "booking.jsp";
        try {
            HttpSession session = request.getSession();
            Guest guest = (Guest) session.getAttribute("guest");
            if (guest == null) {
                url = "login.jsp";
            } else {
                int roomID = Integer.parseInt(request.getParameter("roomID"));
                String checkInStr = request.getParameter("checkInDate");
                String checkOutStr = request.getParameter("checkOutDate");
                Date checkIn = Date.valueOf(checkInStr);
                Date checkOut = Date.valueOf(checkOutStr);
                Booking booking = new Booking(0, guest.getGuestID(), roomID, checkIn, checkOut, null, "Reserved");
                BookingDAO bookingDAO = new BookingDAO();
                int result = bookingDAO.insertBooking(booking);
                if (result > 0) {
                    url = IConstant.BOOKING_SUCCESS_PAGE;
                } else {
                    request.setAttribute("error", "Booking failed");
                    url = IConstant.ERROR_PAGE;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
