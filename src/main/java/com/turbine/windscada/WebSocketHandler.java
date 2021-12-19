package com.turbine.windscada;

import java.util.Set;
import com.turbine.windscada.service.socket.WebSocketService;
import com.turbine.windscada.service.socket.impl.PerformanceTrendService;
import io.vertx.core.http.ServerWebSocket;

public class WebSocketHandler {
    private static Set<String> registedPaths = Util.getPath();

    public static void handler(ServerWebSocket socket) {
        String path = socket.path();
        if (!registedPaths.contains(path)) {
            socket.reject();
        }
        socket.accept();
        WebSocketService service = null;
        if (path.equals("/performance-trend")) {
            service = new PerformanceTrendService();
        }
        if (service != null) {
            service.start(socket);
        }
    }
}
