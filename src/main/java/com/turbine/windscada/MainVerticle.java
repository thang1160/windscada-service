package com.turbine.windscada;

import java.util.logging.Logger;
import com.turbine.windscada.service.http.PerformanceTrendHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import static com.turbine.windscada.Path.*;

public class MainVerticle extends AbstractVerticle {
  private static Logger logger = Logger.getLogger(MainVerticle.class.getName());

  @Override
  public void start() throws Exception {
    // Create a Router
    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());
    router.get(PERFORMANCE_TREND.toString()).handler(PerformanceTrendHandler::getHistories);

    // Create the HTTP server
    vertx.createHttpServer()
        .requestHandler(router)
        .webSocketHandler(WebSocketHandler::handler)
        .listen(8888)
        .onSuccess(server -> logger.info("HTTP server started on port " + server.actualPort()));
  }
}
