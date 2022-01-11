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

public class TurbineDAO extends Db {
    private static final Logger LOGGER = Logger.getLogger(TurbineDAO.class.getName());

    public static List<Map<String, Object>> getTurbineStatus() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            con = getConnection();
            ps = con.prepareStatement("exec get_turbine_status;");
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

    public static List<Map<String, Object>> getOverview() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            con = getConnection();
            ps = con.prepareStatement("exec get_overview;");
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
