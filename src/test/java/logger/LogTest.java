package logger;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class LogTest {

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerWhenLogFileIsEmpty() {
        File file = Log.getLogFile();
        if (file.delete()) {
            Log.showLog();
        }
    }

    @Test
    public void shouldWriteStringLogToLogFile() throws FileNotFoundException {

        Log.clear();
        Log.out("test line");
        assertThat(Log.getLogFile()).exists();

        Scanner scan = new Scanner(Log.getLogFile());
        assertThat(scan.nextLine()).contains("test line");
    }

    @Test
    public void shouldWriteErrorLogToLogFile() throws FileNotFoundException {

        Log.clear();
        Log.out(new IllegalAccessException("test error").getMessage());

        assertThat(Log.getLogFile()).exists();

        Scanner scan = new Scanner(Log.getLogFile());
        assertThat(scan.nextLine()).contains("test error");
    }

    @Test
    public void shouldWriteIntLogToLogFile() throws FileNotFoundException {

        Log.clear();
        Log.out(4);
        assertThat(Log.getLogFile()).exists();

        Scanner scan = new Scanner(Log.getLogFile());
        assertThat(scan.nextLine()).contains("4");

    }

    @Test
    public void shouldWriteBooleanLogToLogFile() throws FileNotFoundException {

        Log.clear();
        Log.out(true);

        assertThat(Log.getLogFile()).exists();

        Scanner scan = new Scanner(Log.getLogFile());
        assertThat(scan.nextLine()).contains("true");

    }

    @Test
    public void shouldWriteLongLogToLogFile() throws FileNotFoundException {

        Log.clear();
        long test = 999999999999999999L;
        Log.out(test);

        assertThat(Log.getLogFile()).exists();

        Scanner scan = new Scanner(Log.getLogFile());
        assertThat(scan.nextLine()).contains("999999999999999999");

    }

    @Test
    public void shouldWriteCharLogToLogFile() throws FileNotFoundException {

        Log.clear();
        char test = 'c';
        Log.out(test);

        assertThat(Log.getLogFile()).exists();

        Scanner scan = new Scanner(Log.getLogFile());
        assertThat(scan.nextLine()).contains("c");

    }

    @Test
    public void shouldSetLogPriorityDEBUG() throws FileNotFoundException {

        Log.clear();
        Log.level(Log.Level.DEBUG);
        Log.out("test");

        Scanner scan = new Scanner(Log.getLogFile());
        assertThat(scan.nextLine()).contains("DEBUG");
    }

    @Test
    public void shouldSetLogPriorityWARNING() throws FileNotFoundException {

        Log.clear();
        Log.level(Log.Level.WARNING);
        Log.out("test");

        Scanner scan = new Scanner(Log.getLogFile());
        assertThat(scan.nextLine()).contains("WARNING");
    }

    @Test
    public void shouldSetLogPriorityLOG() throws FileNotFoundException {

        Log.clear();
        Log.out("test");

        Scanner scan = new Scanner(Log.getLogFile());
        assertThat(scan.nextLine()).contains("LOG");
    }
    @Test
    public void shouldSetLogPriorityERROR() throws FileNotFoundException {

        Log.clear();
        Log.level(Log.Level.ERROR);
        Log.out("test");

        Scanner scan = new Scanner(Log.getLogFile());
        assertThat(scan.nextLine()).contains("ERROR");
    }
}
