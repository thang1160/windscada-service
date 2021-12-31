package com.turbine.windscada.service.socket.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.turbine.windscada.Util;
import com.turbine.windscada.dao.AlarmDAO;
import com.turbine.windscada.service.socket.WebSocketService;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.ServerWebSocket;

public class AlarmService implements WebSocketService {

    private static final Logger LOGGER = Logger.getLogger(AlarmService.class.getName());
    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    @Override
    public void start(ServerWebSocket socket) {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(5000);
                        if (socket.isClosed()) {
                            LOGGER.info("Socket closed by client");
                            break;
                        }
                        List<Map<String, Object>> result = AlarmDAO.lastestAlarm();
                        Buffer buffer = Buffer.buffer().appendString(Util.toJson(result));
                        socket.write(buffer);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        LOGGER.log(Level.WARNING, "Thread is interrupted", e);
                    } catch (Exception e) {
                        LOGGER.log(Level.SEVERE, "", e);
                        socket.close();
                        break;
                    }
                }
            }
        }.start();
    }
}
