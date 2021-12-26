package com.turbine.windscada.service.http;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.turbine.windscada.Util;
import com.turbine.windscada.dao.PerformanceTrendDAO;
import org.apache.commons.lang3.math.NumberUtils;
import io.vertx.ext.web.RoutingContext;

public class PerformanceTrendHandler {
    private static Logger logger = Logger.getLogger(PerformanceTrendHandler.class.getName());
    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    public static void getHistory(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                String input = rc.request().getParam("day");
                List<Map<String, Object>> result = PerformanceTrendDAO.historyPerformanceTrend(NumberUtils.toDouble(input, 5));
                for (Map<String,Object> map : result) {
                    Timestamp logTime = (Timestamp) map.get("log_time");
                    map.put("log_time", sdf.format(logTime));
                }
                Util.sendResponse(rc, 200, result);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "", e);
                rc.fail(e);
            }
        }, false, null);
    }
}
