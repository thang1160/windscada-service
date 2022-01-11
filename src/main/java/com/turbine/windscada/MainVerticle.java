package com.turbine.windscada;

import java.util.logging.Logger;
import com.turbine.windscada.service.http.AlarmHandler;
import com.turbine.windscada.service.http.PerformanceTrendHandler;
import com.turbine.windscada.service.http.TurbineHandler;
import com.turbine.windscada.service.socket.AlarmsThread;
import com.turbine.windscada.service.socket.AlarmsWarningThread;
import com.turbine.windscada.service.socket.BarGraphThread;
import com.turbine.windscada.service.socket.OverviewThread;
import com.turbine.windscada.service.socket.PerformanceTrendThread;
import com.turbine.windscada.service.socket.TurbineStatusThread;
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

    router.put(PREFIX + ALARMS_OFF.toString()).produces(JSON).handler(AlarmHandler::setAlarmOff);

    router.get(PREFIX + OVERVIEW.toString()).produces(JSON).handler(TurbineHandler::getOverview);

    // Create the HTTP server
    vertx.createHttpServer()
        .requestHandler(router)
        .webSocketHandler(WebSocketHandler::handler)
        .listen(8888)
        .onSuccess(server -> {
          logger.info("HTTP server started on port " + server.actualPort());
          new PerformanceTrendThread().start();
          new AlarmsThread().start();
          new TurbineStatusThread().start();
          new AlarmsWarningThread().start();
          new BarGraphThread().start();
          new OverviewThread().start();
        });
  }
}
