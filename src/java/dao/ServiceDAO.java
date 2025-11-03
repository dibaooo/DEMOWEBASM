/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import dto.Service;
import dbutil.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
/**
 *
 * @author Asus
 */
public class ServiceDAO {
// Get all services
public ArrayList<Service> getAllServices() {
    ArrayList<Service> list = new ArrayList<>();
    Connection cn = null;
    try {
        cn = DBUtil.getConnection();
        if (cn != null) {
            String sql = "SELECT ServiceID, ServiceName, ServiceType, Price FROM SERVICE";
            PreparedStatement st = cn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Service s = new Service(
                    rs.getInt("ServiceID"),
                    rs.getString("ServiceName"),     // Đảm bảo đúng tên cột
                    rs.getString("ServiceType"),
                    rs.getDouble("Price")
                );
                list.add(s);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try { if (cn != null) cn.close(); } catch (Exception e) {}
    }
    return list;
}
    
    public int insertService(Service service) {
    Connection cn = null;
    int result = 0;
    try {
        cn = DBUtil.getConnection();
        if (cn != null) {
            String sql = "INSERT INTO SERVICE (ServiceName, ServiceType, Price) VALUES (?, ?, ?)";
            PreparedStatement st = cn.prepareStatement(sql);
            st.setString(1, service.getServiceName());
            st.setString(2, service.getServiceType());
            st.setDouble(3, service.getPrice());
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