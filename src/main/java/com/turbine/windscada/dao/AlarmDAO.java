package com.turbine.windscada.dao;

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

    public static List<Map<String, Object>> lastestAlarm(String name) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            con = getConnection();
            ps = con.prepareStatement("exec get_alarm_list ? ;");
            ps.setString(1, name == null ? "" : name);
            rs = ps.executeQuery();
            if (rs != null) {
                result = Util.convertResultSetToList(rs);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.FINE, "", ex);
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
            LOGGER.log(Level.FINE, "", ex);
        } finally {
            close(rs, ps, con);
        }
    }
}
