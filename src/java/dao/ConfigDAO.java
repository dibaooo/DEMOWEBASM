/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import dto.Config;
import dbutil.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
/**
 *
 * @author Asus
 */
public class ConfigDAO {
    // Get config value by key
public String getConfigValue(String key) {
    Connection cn = null;
    String value = null;
    try {
        cn = DBUtil.getConnection();
        if (cn != null) {
            String sql = "SELECT ConfigValue FROM CONFIG WHERE ConfigKey = ?";
            PreparedStatement st = cn.prepareStatement(sql);
            st.setString(1, key);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                value = rs.getString("ConfigValue");
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try { if (cn != null) cn.close(); } catch (Exception e) {}
    }
    return value;
}
    // Update config value
    public int updateConfig(String key, String value) {
        Connection cn = null;
        int result = 0;
        try {
            cn = DBUtil.getConnection();
            if (cn != null) {
                String sql = "UPDATE CONFIG SET ConfigValue = ? WHERE ConfigKey = ?";
                PreparedStatement st = cn.prepareStatement(sql);
                st.setString(1, value);
                st.setString(2, key);
                result = st.executeUpdate();
                if (result == 0) {
                    // Insert if not exists
                    sql = "INSERT INTO CONFIG (ConfigKey, ConfigValue) VALUES (?, ?)";
                    st = cn.prepareStatement(sql);
                    st.setString(1, key);
                    st.setString(2, value);
                    result = st.executeUpdate();
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

    // Get all configs
    public ArrayList<Config> getAllConfigs() {
        ArrayList<Config> result = new ArrayList<>();
        Connection cn = null;
        try {
            cn = DBUtil.getConnection();
            if (cn != null) {
                String sql = "SELECT ConfigKey, ConfigValue FROM CONFIG";
                PreparedStatement st = cn.prepareStatement(sql);
                ResultSet table = st.executeQuery();
                while (table.next()) {
                    result.add(new Config(table.getString("ConfigKey"), table.getString("ConfigValue")));
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
}
