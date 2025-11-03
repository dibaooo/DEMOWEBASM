/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import dbutil.DBUtil;
import dto.Payment;
import java.sql.Connection;
import java.sql.PreparedStatement;
/**
 *
 * @author Asus
 */
public class PaymentDAO {

public int insertPayment(Payment payment) {
        Connection cn = null;
        int result = 0;
        try {
            cn = DBUtil.getConnection();
            if (cn != null) {
                String sql = "INSERT INTO PAYMENT (InvoiceID, PaymentDate, Amount, PaymentMethod, Status) "
                           + "VALUES (?, ?, ?, ?, ?)";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setInt(1, payment.getInvoiceID());
                st.setDate(2, payment.getPaymentDate());
                st.setDouble(3, payment.getAmount());
                st.setString(4, payment.getPaymentMethod());
                st.setString(5, payment.getStatus());
                result = st.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (cn != null) cn.close(); } catch (Exception e) {}
        }
        return result;
    }
}