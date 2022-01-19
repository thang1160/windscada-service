package com.turbine.windscada.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.turbine.windscada.Util;

public class AlarmDAO extends Db {

    private static final Logger LOGGER = Logger.getLogger(AlarmDAO.class.getName());

    public static List<Map<String, Object>> lastestAlarm() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            con = getConnection();
            ps = con.prepareStatement("exec get_alarm_list_new;");
            rs = ps.executeQuery();
            if (rs != null) {
                result = Util.convertResultSetToList(rs);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "", ex);
        } finally {
            close(rs, ps, con);
        }
        return result;
    }

    public static List<Map<String, Object>> historyAlarm(String name, int turbineId, int page, int pageSize) {
        Connection con = null;
        PreparedStatement cs = null;
        ResultSet rs = null;
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            con = getConnection();
            cs = con.prepareCall("exec get_alarm_list ?, ?, ?, ?, ?;");
            cs.setString(1, name == null ? "" : name);            
            cs.setInt(2, turbineId);
            cs.setInt(3, page);
            cs.setInt(4, pageSize);
            cs.setInt(5, 0);
            rs = cs.executeQuery();
            if (rs != null) {
                result = Util.convertResultSetToList(rs);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "", ex);
        } finally {
            close(rs, cs, con);
        }
        return result;
    }

    public static List<Map<String, Object>> alarmsWarning(double day) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            con = getConnection();
            ps = con.prepareStatement("exec get_alarms_warning ? ;");
            ps.setDouble(1, day);
            rs = ps.executeQuery();
            if (rs != null) {
                result = Util.convertResultSetToList(rs);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "", ex);
        } finally {
            close(rs, ps, con);
        }
        return result;
    }

    public static void setAlarmsOff(String ids, int accountId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            ps = con.prepareStatement("exec set_alarm_off ?, ? ;");
            ps.setString(1, ids == null ? "" : ids);
            ps.setInt(2, accountId);
            ps.execute();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "", ex);
        } finally {
            close(rs, ps, con);
        }
    }
}
