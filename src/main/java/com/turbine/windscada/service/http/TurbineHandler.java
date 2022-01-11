package com.turbine.windscada.service.http;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.turbine.windscada.Util;
import com.turbine.windscada.dao.TurbineDAO;
import io.vertx.ext.web.RoutingContext;

public class TurbineHandler {
    private static Logger logger = Logger.getLogger(TurbineHandler.class.getName());

    public static void getTurbineStatus(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                List<Map<String, Object>> result = TurbineDAO.getTurbineStatus();
                logger.info("result" + result.size());
                Util.sendResponse(rc, 200, result);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "", e);
                rc.fail(e);
            }
        }, false, null);
    }

    public static void getOverview(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                List<Map<String, Object>> result = TurbineDAO.getOverview();
                logger.info("result" + result.size());
                Util.sendResponse(rc, 200, result);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "", e);
                rc.fail(e);
            }
        }, false, null);
    }


}
