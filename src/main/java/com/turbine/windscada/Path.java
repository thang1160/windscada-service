package com.turbine.windscada;

import java.util.HashSet;
import java.util.Set;

public enum Path {
    PERFORMANCE_TREND("/performance-trend"),
    NGUYEN("/nguyen")
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
        return result;
    }
}
