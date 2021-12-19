package com.turbine.windscada.service.socket;

import io.vertx.core.http.ServerWebSocket;

public interface WebSocketService {
    public void start(ServerWebSocket socket);
}
