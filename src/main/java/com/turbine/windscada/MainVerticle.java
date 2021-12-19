package com.turbine.windscada;

import java.util.logging.Logger;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractVerticle {
  private static Logger logger = Logger.getLogger(MainVerticle.class.getName());

  @Override
  public void start() throws Exception {
    // Create a Router
    Router router = Router.router(vertx);

    // Create the HTTP server
    vertx.createHttpServer()
      .requestHandler(router)
      .webSocketHandler(WebSocketHandler::handler)
      .listen(8888)
      .onSuccess(server ->
        logger.info("HTTP server started on port " + server.actualPort())
      );
  }
}