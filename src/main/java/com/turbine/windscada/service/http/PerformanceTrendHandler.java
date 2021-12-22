package com.turbine.windscada.service.http;

import java.util.logging.Level;
import java.util.logging.Logger;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class PerformanceTrendHandler {
    private static Logger logger = Logger.getLogger(PerformanceTrendHandler.class.getName());

    public static void getHistories(RoutingContext rc) {
        try {
            JsonObject body = rc.getBodyAsJson();
            double day = body != null && body.containsKey("day") ? body.getDouble("day") : 5;
            if (day == 0) rc.fail(401);
            rc.end();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "", e);
            rc.fail(e);
        }
    }
}
