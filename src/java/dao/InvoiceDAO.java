/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import dbutil.DBUtil;
import dto.Invoice;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 * @author Asus
 */
public class InvoiceDAO {

public int insertInvoice(Invoice invoice) {
        Connection cn = null;
        int invoiceID = 0;
        try {
            cn = DBUtil.getConnection();
            if (cn != null) {
                String sql = "INSERT INTO INVOICE (BookingID, [Invoice-Date], Subtotal, TaxAmount, TotalAmount) "
                           + "VALUES (?, ?, ?, ?, ?); SELECT SCOPE_IDENTITY();";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setInt(1, invoice.getBookingID());
                st.setDate(2, invoice.getInvoiceDate());
                st.setDouble(3, invoice.getSubtotal());
                st.setDouble(4, invoice.getTaxAmount());
                st.setDouble(5, invoice.getTotalAmount());
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    invoiceID = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (cn != null) cn.close(); } catch (Exception e) {}
        }
        return invoiceID;
    }
}