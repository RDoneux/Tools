package logger;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class LogTest {

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerWhenLogFileIsEmpty() throws IOException {
        File file = Log.getLogFile();
        Files.delete(file.toPath());

        Log.showLog();
    }

    @Test
    public void shouldWriteStringLogToLogFile() throws FileNotFoundException {

        String line = "Test line";

        Log.clear();
        Log.out(line);
        assertThat(Log.getLogFile()).exists();

        Scanner scan = new Scanner(Log.getLogFile());
        assertThat(scan.nextLine()).contains(line);
        scan.close();

        // clean up
        assertThat(Log.delete()).isTrue();

    }

    @Test
    public void shouldWriteErrorLogToLogFile() throws FileNotFoundException {

        Log.clear();
        Log.out(new IllegalAccessException("test error").getMessage());

        assertThat(Log.getLogFile()).exists();

        Scanner scan = new Scanner(Log.getLogFile());
        assertThat(scan.nextLine()).contains("test error");
        scan.close();

        // clean up
        assertThat(Log.delete()).isTrue();
    }

    @Test
    public void shouldWriteIntLogToLogFile() throws FileNotFoundException {

        Log.clear();
        Log.out(4);
        assertThat(Log.getLogFile()).exists();

        Scanner scan = new Scanner(Log.getLogFile());
        assertThat(scan.nextLine()).contains("4");
        scan.close();

        // clean up
        assertThat(Log.delete()).isTrue();

    }

    @Test
    public void shouldWriteBooleanLogToLogFile() throws FileNotFoundException {

        Log.clear();
        Log.out(true);

        assertThat(Log.getLogFile()).exists();

        Scanner scan = new Scanner(Log.getLogFile());
        assertThat(scan.nextLine()).contains("true");
        scan.close();

        // clean up
        assertThat(Log.delete()).isTrue();

    }

    @Test
    public void shouldWriteLongLogToLogFile() throws FileNotFoundException {

        Log.clear();
        long test = 999999999999999999L;
        Log.out(test);

        assertThat(Log.getLogFile()).exists();

        Scanner scan = new Scanner(Log.getLogFile());
        assertThat(scan.nextLine()).contains("999999999999999999");
        scan.close();

        // clean up
        assertThat(Log.delete()).isTrue();

    }

    @Test
    public void shouldWriteCharLogToLogFile() throws FileNotFoundException {

        Log.clear();
        char test = 'c';
        Log.out(test);

        assertThat(Log.getLogFile()).exists();

        Scanner scan = new Scanner(Log.getLogFile());
        assertThat(scan.nextLine()).contains("c");
        scan.close();

        // clean up
        assertThat(Log.delete()).isTrue();

    }

    @Test
    public void shouldSetLogPriorityDEBUG() throws FileNotFoundException {

        Log.clear();
        Log.level(Level.DEBUG);
        Log.out("test");

        Scanner scan = new Scanner(Log.getLogFile());
        assertThat(scan.nextLine()).contains("DEBUG");
        scan.close();

        // clean up
        assertThat(Log.delete()).isTrue();
    }

    @Test
    public void shouldSetLogPriorityWARNING() throws FileNotFoundException {

        Log.clear();
        Log.level(Level.WARNING);
        Log.out("test");

        Scanner scan = new Scanner(Log.getLogFile());
        assertThat(scan.nextLine()).contains("WARNING");
        scan.close();

        // clean up
        assertThat(Log.delete()).isTrue();

    }

    @Test
    public void shouldSetLogPriorityINFO() throws FileNotFoundException {

        Log.clear();
        Log.out("test");

        Scanner scan = new Scanner(Log.getLogFile());
        assertThat(scan.nextLine()).contains("INFO");
        scan.close();

        // clean up
        assertThat(Log.delete()).isTrue();

    }

    @Test
    public void shouldSetLogPriorityERROR() throws FileNotFoundException {

        Log.clear();
        Log.level(Level.ERROR);
        Log.out("test");

        Scanner scan = new Scanner(Log.getLogFile());
        assertThat(scan.nextLine()).contains("ERROR");
        scan.close();

        // clean up
        assertThat(Log.delete()).isTrue();

    }

    @Test
    public void shouldUpdateLogLocation() throws FileNotFoundException {

        Log.clear();
        Log.location("testLogOne");

        Log.out("this is the test line");

        assertThat(Log.getLogFile()).exists();
        Scanner scan = new Scanner(new File(Log.getLocation()));

        assertThat(scan.nextLine()).contains("this is the test line");
        scan.close();

        // clean up
        assertThat(Log.delete()).isTrue();

    }

    @Test
    public void shouldUpdateLogName() throws FileNotFoundException {

        Log.name("second log");

        Log.out("this is the test line for the second log location");

        assertThat(Log.getLogFile()).exists();

        Scanner scan = new Scanner(new File(Log.getLocation()));
        assertThat(scan.nextLine()).contains("this is the test line for the second log location");
        scan.close();

        // clean up
        assertThat(Log.delete()).isTrue();
    }

    @Test
    public void shouldDeleteLogFile() {
        Log.out("test line");
        assertThat(Log.getLogFile()).exists();

        assertThat(Log.delete()).isTrue();
        assertThat(Log.getLogFile()).doesNotExist();
    }

    @Test
    public void shouldSearchLogFile() {

        Log.out("this is the first line");
        Log.level(Level.WARNING);
        Log.out("this should only be searched on the second search call");
        Log.out("this is the second line");
        Log.level(Level.ERROR);
        Log.out("this is the line that should be searched for");
        Log.level(Level.ERROR);
        Log.out("this line should also be searched for");

        String searchLine = Log.search("ERROR");

        assertThat(searchLine).contains("this is the line that should be searched for")
                .contains("this line should also be searched for");

        searchLine = Log.search("ERROR, WARNING");

        assertThat(searchLine).contains("this should only be searched on the second search call");

        // clean up
        assertThat(Log.delete()).isTrue();
    }

    @Test
    public void shouldReturnAllLinesFromError() {

        Log.out("first line should not return");
        Log.out(new IllegalArgumentException("this is the line that should be returned"));
        Log.out("second line should not return");
        Log.out("third line of the test");
        Log.out(new NullPointerException("this line should also be returned"));

        assertThat(Log.search("ERROR"))
                .contains("this is the line that should be returned")
                .contains("this line should also be returned");

        // clean up
        assertThat(Log.delete()).isTrue();
    }
}
