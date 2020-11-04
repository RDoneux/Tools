package logger;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Log {

    private File logFile;
    private String location;
    private Level level;

    private Log(String location) {
        this.location(location);
    }

    public static Log get(String name) {
        return LogStore.add(new Log(name));
    }

    public void createLogFile() throws IOException {
        File file = new File(location);
        if (file.getParentFile() != null && !file.getParentFile().mkdirs()) {
            // EMPTY
        }
        if (!file.createNewFile()) {
            Files.delete(file.toPath());
            createLogFile();
        }
        logFile = file;
    }

    public void showLog() {
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

    public void out(String log) {
        write(log);
    }

    public void out(Exception log) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        log.printStackTrace(pw);
        level = Level.ERROR;
        write(sw.toString());
    }

    public void out(int log) {
        write(String.valueOf(log));
    }

    public void out(boolean log) {
        write(String.valueOf(log));
    }

    public void out(long log) {
        write(String.valueOf(log));
    }

    public void out(char log) {
        write(String.valueOf(log));
    }

    public void newLine() {
        write("");
    }

    private void write(String log) {

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

    private String createTimeStamp() {
        LocalDateTime time = LocalDateTime.now();
        return time.getHour() + ":" +
                time.getMinute() + ":" +
                time.getSecond() + ":" +
                time.getNano();
    }

    public File getLogFile() {
        return logFile;
    }

    public void location(String newLocation) {
        location = newLocation + ".log";
        logFile = null;
    }

    public void name(String name) {
        if (logFile != null && logFile.getParentFile() != null) {
            location = logFile.getParentFile().getAbsolutePath() + File.separator + name + ".log";
        } else {
            location = name + ".log";
        }
        logFile = null;
    }

    public void level(Level newLevel) {
        level = newLevel;
    }

    public String getLocation() {
        return location;
    }

    public void clear() {
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

    public boolean delete() {
        if (logFile == null) {
            return false;
        } else {
            return logFile.delete();
        }
    }

    public String search(String searchCase) {

        String[] keywords = searchCase.split(", ");
        StringBuilder build = new StringBuilder();

        try {
            File searchLog = new File("searchLog.log");
            BufferedWriter write = new BufferedWriter(new FileWriter(searchLog));

            for (String keyWord : keywords) {
                Scanner scan = new Scanner(logFile);
                write.newLine();
                write.write("** " + keyWord.toUpperCase() + " **");
                write.newLine();
                while (scan.hasNext()) {
                    String line = scan.nextLine();
                    if (!contains(line, "[:::::]")) {
                        build.append(line);
                        write.write(line);
                        write.newLine();
                    } else {
                        if (line.toLowerCase().contains(keyWord.toLowerCase())) {
                            build.append(line).append("-");
                            write.write(line);
                            write.newLine();
                        }
                    }
                }
                scan.close();
            }
            write.close();

            Desktop.getDesktop().open(searchLog);
            Thread.sleep(500);

            if (!searchLog.delete()) {
                throw new NullPointerException("search log could not be deleted");
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return build.toString();

    }

    private boolean contains(String input, String sequence) {

        boolean[] checkList = new boolean[sequence.length()];

        for (int i = 0; i < sequence.length(); i++) {
            String sequenceSubstring = sequence.substring(i, i + 1);
            for (int j = 0; j < input.length(); j++) {
                String inputSubstring = input.substring(j, j + 1);
                if (inputSubstring.equals(sequenceSubstring)) {
                    checkList[i] = true;
                    break;
                }
            }
        }
        for (boolean check : checkList) {
            if (!check) {
                return false;
            }
        }
        return true;
    }
}
