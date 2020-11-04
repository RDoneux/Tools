package logger;

import java.util.HashMap;
import java.util.Map;

public class LogStore {

    public static Map<String, Log> logs = new HashMap<>();

    public static Log add(Log log) {
        logs.putIfAbsent(log.getLocation(), log);
        return logs.get(log.getLocation());
    }

}
