package com.turbine.windscada;

import java.util.HashSet;
import java.util.Set;

public enum Path {
    PERFORMANCE_TREND("/performance-trend"),
    ALARMS("/alarms"),
    ALARMS_WARNING("/alarms-warning"),
    ALARMS_OFF("/alarms-off"),
    TURBINE_STATUS("/turbine-status"),
    BAR_GRAPH("/bar-graph"),
    LOGIN("/login"),
    LOGOUT("/logout"),
    PROFILE("/profile"),
    OVERVIEW("/overview");
    ;

    private final String stringPath;

    Path(final String path) {
        this.stringPath = path;
    }

    @Override
    public String toString() {
        return stringPath;
    }
    
    public static Set<String> getSocketPaths() {
        Set<String> result = new HashSet<>();
        result.add(PERFORMANCE_TREND.toString());
        result.add(ALARMS.toString());
        result.add(TURBINE_STATUS.toString());
        result.add(BAR_GRAPH.toString());
        result.add(ALARMS_WARNING.toString());
        result.add(OVERVIEW.toString());
        return result;
    }
}
