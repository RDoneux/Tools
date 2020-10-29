package logger;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


public class LogTest {

    @Test
    public void shouldWriteStringLogToLogFile() throws FileNotFoundException {

        Log.clearLog();
        Log.out("test line");
        assertThat(Log.getLogFile()).exists();

        Scanner scan = new Scanner(Log.getLogFile());
        assertThat(scan.nextLine()).isEqualTo("test line");
    }

    @Test
    public void shouldWriteErrorLogToLogFile() throws FileNotFoundException {

        Log.clearLog();
        Log.out(new IllegalAccessException("test error"));

        assertThat(Log.getLogFile()).exists();

        Scanner scan = new Scanner(Log.getLogFile());
        assertThat(scan.nextLine()).isEqualTo("test error");
    }

    @Test
    public void shouldWriteIntLogToLogFile() throws FileNotFoundException {

        Log.clearLog();
        Log.out(4);
        assertThat(Log.getLogFile()).exists();

        Scanner scan = new Scanner(Log.getLogFile());
        assertThat(scan.nextLine()).isEqualTo("4");

    }

    @Test
    public void shouldWriteBooleanLogToLogFile() throws FileNotFoundException {

        Log.clearLog();
        Log.out(true);

        assertThat(Log.getLogFile()).exists();

        Scanner scan = new Scanner(Log.getLogFile());
        assertThat(scan.nextLine()).isEqualTo("true");

    }

    @Test
    public void shouldWriteLongLogToLogFile() throws FileNotFoundException {

        Log.clearLog();
        long test = 999999999999999999L;
        Log.out(test);

        assertThat(Log.getLogFile()).exists();

        Scanner scan = new Scanner(Log.getLogFile());
        assertThat(scan.nextLine()).isEqualTo("999999999999999999");

    }

    @Test
    public void shouldWriteCharLogToLogFile() throws FileNotFoundException {

        Log.clearLog();
        char test = 'c';
        Log.out(test);

        assertThat(Log.getLogFile()).exists();

        Scanner scan = new Scanner(Log.getLogFile());
        assertThat(scan.nextLine()).isEqualTo("c");

    }

    @Test
    public void shouldSetLogPriority() {

    }

    @Test
    public void shouldShowLogFile() {
        Log.showLog();
    }

}
