package com.turbine.windscada.service.socket.impl;

import java.util.logging.Level;
import java.util.logging.Logger;
import com.turbine.windscada.service.socket.WebSocketService;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.ServerWebSocket;

public class PerformanceTrendService implements WebSocketService {
    private static final Logger LOGGER = Logger.getLogger(PerformanceTrendService.class.getName());

    @Override
    public void start(ServerWebSocket socket) {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (socket.isClosed()) {
                            LOGGER.info("Socket closed by client");
                            break;
                        }
                        Buffer buffer = Buffer.buffer().appendString("Test Performance Trend");
                        socket.write(buffer);
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        LOGGER.log(Level.WARNING, "Thread is interrupted", e);
                    }
                }
            }
        }.start();
    }

}
