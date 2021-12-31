package com.turbine.windscada;

import java.util.Set;
import com.turbine.windscada.service.socket.WebSocketService;
import com.turbine.windscada.service.socket.impl.AlarmService;
import com.turbine.windscada.service.socket.impl.PerformanceTrendService;
import io.vertx.core.http.ServerWebSocket;
import static com.turbine.windscada.Path.*;

public class WebSocketHandler {
    private static Set<String> registedPaths = getSocketPaths();

    public static void handler(ServerWebSocket socket) {
        String path = socket.path();
        if (!registedPaths.contains(path)) {
            socket.reject();
        }
        socket.accept();
        WebSocketService service = null;
        if (path.equals(PERFORMANCE_TREND.toString())) {
            service = new PerformanceTrendService();
        }
        if (path.equals(ALARMS.toString())) {
            service = new AlarmService();
        }
        if (service != null) {
            service.start(socket);
        }
    }
}
