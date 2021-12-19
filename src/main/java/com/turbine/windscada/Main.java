package com.turbine.windscada;

import java.util.function.Consumer;
import java.util.logging.Logger;
import io.vertx.core.Vertx;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String... args) {
        LOGGER.info("Starting service...");
        System.setProperty("vertx.disableFileCPResolving", "true");
        Consumer<Vertx> runner = vertx ->
        vertx.deployVerticle(MainVerticle.class.getName());
        runner.accept(Vertx.vertx());
    }
}
