package com.turbine.windscada;

import java.util.logging.Logger;
import com.turbine.windscada.service.http.AlarmHandler;
import com.turbine.windscada.service.http.PerformanceTrendHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import static com.turbine.windscada.Path.*;

public class MainVerticle extends AbstractVerticle {
  private static Logger logger = Logger.getLogger(MainVerticle.class.getName());
  public static final String JSON = "application/json";
  private static final String PREFIX = "/windscada/api/v1";

  @Override
  public void start() throws Exception {
    // Create a Router
    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());
    router.get(PREFIX + PERFORMANCE_TREND.toString()).produces(JSON).handler(PerformanceTrendHandler::getHistory);

    router.get(PREFIX + ALARMS.toString()).produces(JSON).handler(AlarmHandler::getHistory);

    // Create the HTTP server
    vertx.createHttpServer()
        .requestHandler(router)
        .webSocketHandler(WebSocketHandler::handler)
        .listen(8888)
        .onSuccess(server -> logger.info("HTTP server started on port " + server.actualPort()));
  }
}
