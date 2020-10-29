package logger;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Log {

    private static File logFile;

    public static void showLog() {
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.open(logFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void out(String log) {
        write(log);
    }

    public static void out(Exception log) {
        write(log.getMessage());
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

    private static File createLogFile() throws IOException {
        File file = new File("log.txt");
        if (!file.createNewFile()) {
            file.delete();
            createLogFile();
        }
        return file;
    }

    private static boolean write(String log) {

        if (logFile == null || !logFile.exists()) {
            try {
                logFile = createLogFile();
            } catch (IOException e) {
                return false;
            }
        }

        try {
            BufferedWriter write = new BufferedWriter(new FileWriter(logFile, true));
            write.write(log);
            write.newLine();
            write.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static File getLogFile() {
        return logFile;
    }

    public static boolean clearLog(){
        if(logFile != null) {
            try {
                BufferedWriter clear = new BufferedWriter(new FileWriter(logFile));
                clear.write("");
                clear.close();
            } catch (IOException e){
                return false;
            }
        }
        return true;
    }

}
