package com.turbine.windscada.service.http;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.turbine.windscada.Util;
import com.turbine.windscada.dao.AlarmDAO;
import org.apache.commons.lang3.math.NumberUtils;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class AlarmHandler {
    private AlarmHandler() {}
    private static Logger logger = Logger.getLogger(AlarmHandler.class.getName());

    public static void getLastestAlarm(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                List<Map<String, Object>> result = AlarmDAO.lastestAlarm();
                Util.sendResponse(rc, 200, result);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "", e);
                rc.fail(e);
            }
        }, false, null);
    }

    public static void getHistory(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                HttpServerRequest request = rc.request();
                String tagName = request.getParam("name");
                int turbineId = Integer.parseInt(request.getParam("turbine_id", "0"));
                int page = Integer.parseInt(request.getParam("page", "0"));
                int pageSize = Integer.parseInt(request.getParam("page_size", "0"));
                List<Map<String, Object>> result = AlarmDAO.historyAlarm(tagName, turbineId, page, pageSize);
                logger.info("result" + result.size());
                Util.sendResponse(rc, 200, result);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "", e);
                rc.fail(e);
            }
        }, false, null);
    }

    public static void setAlarmOff(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                JsonObject jRequest = new JsonObject(rc.getBodyAsString());
                String ids = jRequest.getString("ids");
                int accountId = 1;
                logger.info("ids: " + ids);
                AlarmDAO.setAlarmsOff(ids, accountId);
                JsonObject result = new JsonObject().put("result", "Success");
                Util.sendResponse(rc, 200, result);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "", e);
                rc.fail(e);
            }
        }, false, null);
    }

    public static void getAlarmsWarning(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                List<Map<String, Object>> result = AlarmDAO.alarmsWarning(NumberUtils.toDouble("5", 5));
                Util.sendResponse(rc, 200, result);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "", e);
                rc.fail(e);
            }
        }, false, null);
    }
}
