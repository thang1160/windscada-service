package com.turbine.windscada;

import java.util.HashSet;
import java.util.Set;

public class Util {
    public static Set<String> getPath() {
        Set<String> result = new HashSet<>();
        result.add("/performance-trend");
        return result;
    }
}
