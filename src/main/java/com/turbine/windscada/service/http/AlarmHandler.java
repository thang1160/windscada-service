package com.turbine.windscada.service.http;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.turbine.windscada.Util;
import com.turbine.windscada.dao.AlarmDAO;
import org.apache.commons.lang3.math.NumberUtils;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class AlarmHandler {
    private static Logger logger = Logger.getLogger(AlarmHandler.class.getName());

    public static void getLastestAlarm(RoutingContext rc) {
        rc.vertx().executeBlocking(future -> {
            try {
                String tagName = rc.request().getParam("name");
                logger.info("tagName: " + tagName);
                List<Map<String, Object>> result = AlarmDAO.lastestAlarm();
                logger.info("result" + result.size());
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
                String tagName = rc.request().getParam("name");
                int turbineId = Integer.parseInt(rc.request().getParam("turbine_id") != null ? rc.request().getParam("turbine_id") : "0");
                logger.info("tagName: " + tagName);
                List<Map<String, Object>> result = AlarmDAO.historyAlarm(tagName, NumberUtils.toDouble("5", 5), turbineId);
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
