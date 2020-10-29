package logger;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Log {

    private static File logFile;

    private Log() {
    }

    public static void showLog() {
        Desktop desktop = Desktop.getDesktop();
        try {
            if(logFile != null) {
                desktop.open(logFile);
            } else {
                throw new NullPointerException("Log file is empty");
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

    public static void newLine(){
        write("");
    }

    private static File createLogFile() throws IOException {
        File file = new File("log.txt");
        if (!file.createNewFile()) {
            Files.delete(Path.of(file.getAbsolutePath()));
            createLogFile();
        }
        return file;
    }

    private static void write(String log) {

        if (logFile == null || !logFile.exists()) {
            try {
                logFile = createLogFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            BufferedWriter write = new BufferedWriter(new FileWriter(logFile, true));
            write.write(log);
            write.newLine();
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File getLogFile() {
        return logFile;
    }

    public static void clearLog() {
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
