package com.turbine.windscada;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import io.vertx.core.http.ServerWebSocket;
import static com.turbine.windscada.Path.*;

public class WebSocketHandler {
    private static Set<String> registedPaths = getSocketPaths();
    private static List<ServerWebSocket> performanceTrendSockets= new ArrayList<>();

    public static List<ServerWebSocket> getPerformanceTrendSockets() {
        return WebSocketHandler.performanceTrendSockets;
    }

    public static void handler(ServerWebSocket socket) {
        String path = socket.path();
        if (!registedPaths.contains(path)) {
            socket.reject();
        }
        socket.accept();
        if (path.equals(PERFORMANCE_TREND.toString())) {
            performanceTrendSockets.add(socket);
        }
    }
}
