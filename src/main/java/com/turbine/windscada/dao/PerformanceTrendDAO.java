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

public class PerformanceTrendDAO extends Db {
    private static final Logger LOGGER = Logger.getLogger(PerformanceTrendDAO.class.getName());

    public static List<Map<String, Object>> lastestPerformanceTrend() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            con = getConnection();
            ps = con.prepareStatement("exec lastest_performance_trend;");
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

    public static List<Map<String, Object>> historyPerformanceTrend(double day) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            con = getConnection();
            ps = con.prepareStatement("select * from [Site_Log] where log_time > GETDATE() - ?");
            ps.setDouble(1, day);
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
}
