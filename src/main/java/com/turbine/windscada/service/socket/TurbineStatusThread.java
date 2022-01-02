package com.turbine.windscada.service.socket;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.turbine.windscada.Util;
import com.turbine.windscada.WebSocketHandler;
import com.turbine.windscada.dao.TurbineDAO;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.ServerWebSocket;

public class TurbineStatusThread extends Thread {
    private static final Logger LOGGER = Logger.getLogger(TurbineStatusThread.class.getName());

    @Override
    public void run() {
        LOGGER.info("Turbine Status Thread is running");
        while (true) {
            try {
                Thread.sleep(5000);
                List<ServerWebSocket> closedList = new ArrayList<>();
                List<ServerWebSocket> currentList = WebSocketHandler.getTurbineStatusSockets();
                List<Map<String, Object>> result = TurbineDAO.getTurbineStatus();
                for (ServerWebSocket socket : currentList) {
                    if (socket.isClosed()) {
                        LOGGER.info("Socket closed by client");
                        closedList.add(socket);
                        continue;
                    }
                    Buffer buffer = Buffer.buffer().appendString(Util.toJson(result));
                    socket.write(buffer);
                }
                for (ServerWebSocket socket : closedList) {
                    currentList.remove(socket);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOGGER.log(Level.WARNING, "Thread is interrupted", e);
            }
        }
    }

}
