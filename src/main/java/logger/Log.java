package logger;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Log {

    private static File logFile;
    private static String location = "log.txt";
    private static Level level;

    private Log() {
    }

    public enum Level {
        WARNING, ERROR, DEBUG, INFO
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
        level = Level.ERROR;
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

    private static void createLogFile() throws IOException {
        File file = new File(location);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
        if (!file.createNewFile()) {
            Files.delete(file.toPath());
            createLogFile();
        }
        logFile = file;
    }

    private static String createTimeStamp() {
        LocalDateTime time = LocalDateTime.now();
        return time.getHour() + ":" +
                time.getMinute() + ":" +
                time.getSecond() + ":" +
                time.getNano();
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
            level = Level.INFO;
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

    public static void location(String newLocation) {
        location = newLocation + ".txt";
        logFile = null;
    }

    public static void name(String name) {
        if (logFile != null) {
            location = logFile.getParentFile().getAbsolutePath() + File.separator + name + ".txt";
        } else {
            location = name + ".txt";
        }
        logFile = null;
    }

    public static void level(Level newLevel) {
        level = newLevel;
    }

    public static String getLocation() {
        return location;
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

    public static boolean delete() {
        if (logFile == null) {
            return false;
        }
        return logFile.delete();
    }

    public static String search(String keyWord) {

        StringBuilder build = new StringBuilder();

        try {
            Scanner scan = new Scanner(logFile);

            File searchLog = new File("searchLog.txt");
            BufferedWriter write = new BufferedWriter(new FileWriter(searchLog));

            while (scan.hasNext()) {
                String line = scan.nextLine();
                if (line.toLowerCase().contains(keyWord.toLowerCase())) {
                    build.append(line + "-");
                    write.write(line);
                    write.newLine();
                }
            }
            write.close();
            scan.close();

            Desktop.getDesktop().open(searchLog);
            searchLog.deleteOnExit();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return build.toString();

    }


}
