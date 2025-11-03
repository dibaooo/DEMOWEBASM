/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.ConfigDAO;
import dao.ServiceDAO;
import dbutil.IConstant;
import dto.Service;
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
@WebServlet(name="ConfigureSystemController", urlPatterns={"/ConfigureSystemController"})
public class ConfigureSystemController extends HttpServlet {
   
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
        
        String url = "configure.jsp";
        try {
            HttpSession session = request.getSession();
            Staff staff = (Staff) session.getAttribute("staff");

            if (staff == null || !"Admin".equals(staff.getRole())) {
                request.setAttribute("error", "Access denied. Admin only.");
                request.getRequestDispatcher(IConstant.LOGIN_PAGE).forward(request, response);
                return;
            }
            String configAction = request.getParameter("configAction");
            ConfigDAO configDAO = new ConfigDAO();
            if ("update".equals(configAction)) {
                String key = request.getParameter("configKey");
                String value = request.getParameter("newValue");
                configDAO.updateConfig(key, value);
            } else if ("addService".equals(configAction)) {
                String name = request.getParameter("serviceName");
                String type = request.getParameter("serviceType");
                double price = Double.parseDouble(request.getParameter("price"));
                ServiceDAO serviceDAO = new ServiceDAO();
                serviceDAO.insertService(new Service(0, name, type, price));
            }
            // For service categories: list, add, update services
            ServiceDAO dao = new ServiceDAO();
            ArrayList<Service> services = dao.getAllServices();
            request.setAttribute("services", services);

            // Add new service
            if ("addService".equals(request.getParameter("action"))) {
                String name = request.getParameter("serviceName");
                String type = request.getParameter("serviceType");
                double price = Double.parseDouble(request.getParameter("price"));
                // Implement insertService in ServiceDAO
                // dao.insertService(new Service(0, name, type, price));
            }

            // For tax rates: assume a config, but skip for now or add DAO
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
