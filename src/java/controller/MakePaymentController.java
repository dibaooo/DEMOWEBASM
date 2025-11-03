/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.BookingDAO;
import dao.BookingServiceDAO;
import dao.ConfigDAO;
import dao.InvoiceDAO;
import dao.PaymentDAO;
import dao.RoomDAO;
import dbutil.DBUtil;
import dbutil.IConstant;
import dto.Booking;
import dto.Guest;
import dto.Invoice;
import dto.Payment;
import dto.Staff;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
@WebServlet(name="MakePaymentController", urlPatterns={"/MakePaymentController"})
public class MakePaymentController extends HttpServlet {
   
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
        Staff staff = (Staff) session.getAttribute("staff");

        // === Chỉ staff (Receptionist, Manager, Admin) mới được thanh toán ===
        if (staff == null) {
            request.setAttribute("error", "Please login as staff to make payment.");
            request.getRequestDispatcher(url).forward(request, response);
            return;
        }

        try {
            BookingDAO bookingDAO = new BookingDAO();
            BookingServiceDAO serviceDAO = new BookingServiceDAO();
            RoomDAO roomDAO = new RoomDAO();
            InvoiceDAO invoiceDAO = new InvoiceDAO();
            PaymentDAO paymentDAO = new PaymentDAO();
            ConfigDAO configDAO = new ConfigDAO();

            // === GET: Hiển thị danh sách booking đang "Checked-in" ===
            if (request.getMethod().equals("GET")) {
                ArrayList<Booking> bookings = bookingDAO.getBookingsByStatus("Checked-in");
                request.setAttribute("bookings", bookings);
                url = IConstant.PAYMENT_PAGE;
            } 
            // === POST: Xử lý thanh toán ===
            else if (request.getMethod().equals("POST")) {
                int bookingID = Integer.parseInt(request.getParameter("bookingID"));
                String paymentMethod = request.getParameter("paymentMethod");

                Booking booking = bookingDAO.getBooking(bookingID);
                if (booking == null || !"Checked-in".equals(booking.getStatus())) {
                    request.setAttribute("error", "Invalid booking for payment.");
                    url = IConstant.PAYMENT_PAGE;
                } else {
                    // 1. Tính tiền phòng
                    double roomPrice = roomDAO.getRoomPricePerNight(booking.getRoomID());
                    long nights = (booking.getCheckOutDate().getTime() - booking.getCheckInDate().getTime()) / (1000 * 60 * 60 * 24);
                    double roomTotal = roomPrice * nights;

                    // 2. Tính tiền dịch vụ
                    double serviceTotal = serviceDAO.getServiceTotalByBooking(bookingID);

                    // 3. Tính VAT
                    String vatStr = configDAO.getConfigValue("VAT_RATE");
                    double vatRate = vatStr != null ? Double.parseDouble(vatStr) : 0.10;
                    double subtotal = roomTotal + serviceTotal;
                    double vatAmount = subtotal * vatRate;
                    double grandTotal = subtotal + vatAmount;

                    // 4. Tạo Invoice
                    Invoice invoice = new Invoice(0, bookingID, new Date(System.currentTimeMillis()), subtotal, vatAmount, grandTotal);
                    int invoiceID = invoiceDAO.insertInvoice(invoice);
                    if (invoiceID <= 0) {
                        request.setAttribute("error", "Failed to create invoice.");
                        url = IConstant.ERROR_PAGE;
                    } else {
                        // 5. Tạo Payment
                        Payment payment = new Payment(0, invoiceID, new Date(System.currentTimeMillis()), grandTotal, paymentMethod, "Completed");
                        int paymentResult = paymentDAO.insertPayment(payment);
                        if (paymentResult <= 0) {
                            request.setAttribute("error", "Payment failed.");
                            url = IConstant.ERROR_PAGE;
                        } else {
                            // 6. Cập nhật Booking → Checked-out
                            bookingDAO.updateBookingStatus(bookingID, "Checked-out");

                            // 7. Cập nhật Room → Dirty
                            roomDAO.updateRoomStatus(booking.getRoomID(), "Dirty");

                            // 8. Thành công
                            request.setAttribute("invoiceID", invoiceID);
                            request.setAttribute("grandTotal", grandTotal);
                            url = IConstant.PAYMENT_SUCCESS_PAGE;
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Payment error: " + e.getMessage());
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
