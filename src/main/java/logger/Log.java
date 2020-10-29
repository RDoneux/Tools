package logger;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

public class Log {

    private static File logFile;
    private static Level level;

    private Log() {
    }

    public enum Level {
        WARNING, ERROR, DEBUG, LOG
    }

    public static void showLog() {
        Desktop desktop = Desktop.getDesktop();
        try {
            if (logFile != null) {
                desktop.open(logFile);
            } else {
                createLogFile();
                showLog();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void out(String log) {
        write(log);
    }

    public static void out(Exception log) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        log.printStackTrace(pw);
        write(sw.toString());
    }

    public static void out(int log) {
        write(String.valueOf(log));
    }

    public static void out(boolean log) {
        write(String.valueOf(log));
    }

    public static void out(long log) {
        write(String.valueOf(log));
    }

    public static void out(char log) {
        write(String.valueOf(log));
    }

    public static void newLine() {
        write("");
    }

    private static File createLogFile() throws IOException {
        File file = new File("log.txt");
        if (!file.createNewFile()) {
            Files.delete(Path.of(file.getAbsolutePath()));
            createLogFile();
        }
        logFile = file;
        return logFile;
    }

    private static String createTimeStamp() {
        LocalDateTime time = LocalDateTime.now();
        return new StringBuilder()
                .append(time.getHour()).append(":")
                .append(time.getMinute()).append(":")
                .append(time.getSecond()).append(":")
                .append(time.getNano()).toString();
    }

    private static void write(String log) {

        if (logFile == null || !logFile.exists()) {
            try {
                createLogFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (level == null) {
            level = Level.LOG;
        }

        try {
            BufferedWriter write = new BufferedWriter(new FileWriter(logFile, true));
            if (!log.isEmpty()) write.write("[" + level + ":" + createTimeStamp() + "]: ");
            write.write(log);
            write.newLine();
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        level = null;
    }

    public static File getLogFile() {
        return logFile;
    }
    public static void level(Level newLevel){
       level = newLevel;
    }

    public static void clear() {
        if (logFile != null) {
            try {
                BufferedWriter clear = new BufferedWriter(new FileWriter(logFile));
                clear.write("");
                clear.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
