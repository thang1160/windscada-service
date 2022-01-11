package com.turbine.windscada;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import io.vertx.core.http.ServerWebSocket;
import static com.turbine.windscada.Path.*;

public class WebSocketHandler {
    private static Set<String> registedPaths = getSocketPaths();
    private static List<ServerWebSocket> performanceTrendSockets = new ArrayList<>();
    private static List<ServerWebSocket> alarmsSockets = new ArrayList<>();
    private static List<ServerWebSocket> alarmsWarningSockets = new ArrayList<>();
    private static List<ServerWebSocket> turbineStatusSockets = new ArrayList<>();
    private static List<ServerWebSocket> barGraphSockets = new ArrayList<>();
    private static List<ServerWebSocket> overviewSockets = new ArrayList<>();

    public static List<ServerWebSocket> getPerformanceTrendSockets() {
        return WebSocketHandler.performanceTrendSockets;
    }

    public static List<ServerWebSocket> getAlarmsSockets() {
        return WebSocketHandler.alarmsSockets;
    }

    public static List<ServerWebSocket> getAlarmsWarningSocket() {
        return WebSocketHandler.alarmsWarningSockets;
    }

    public static List<ServerWebSocket> getTurbineStatusSockets() {
        return WebSocketHandler.turbineStatusSockets;
    }

    public static List<ServerWebSocket> getBarGraphSockets() {
        return WebSocketHandler.barGraphSockets;
    }
    public static List<ServerWebSocket> getOverviewSockets() {
        return WebSocketHandler.overviewSockets;
    }

    public static void handler(ServerWebSocket socket) {
        String path = socket.path();
        try {
            if (!registedPaths.contains(path)) {
                socket.reject();
            }
            socket.accept();
            if (path.equals(PERFORMANCE_TREND.toString())) {
                performanceTrendSockets.add(socket);
            }
            if (path.equals(ALARMS.toString())) {
                alarmsSockets.add(socket);
            }
            if (path.equals(TURBINE_STATUS.toString())) {
                turbineStatusSockets.add(socket);
            }
            if (path.equals(BAR_GRAPH.toString())) {
                barGraphSockets.add(socket);
            }
            if (path.equals(OVERVIEW.toString())) {
                overviewSockets.add(socket);
            }
        } catch (IllegalStateException e) {
            // socket is already closed
        }
    }
}
