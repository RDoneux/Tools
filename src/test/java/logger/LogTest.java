package logger;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class LogTest {

    private Log log = Log.get("test log");
    
    @Test(expected = NoSuchFileException.class)
    public void shouldThrowNoSuchFileExceptionWhenlogFileIsEmpty() throws IOException {
        File file = log.getLogFile();
        Files.delete(file.toPath());

        log.showLog();
    }

    @Test
    public void shouldWriteStringlogTologFile() throws FileNotFoundException {

        String line = "Test line";
        
        log.clear();
        log.out(line);
        assertThat(log.getLogFile()).exists();

        Scanner scan = new Scanner(log.getLogFile());
        assertThat(scan.nextLine()).contains(line);
        scan.close();

        // clean up
        assertThat(log.delete()).isTrue();

    }

    @Test
    public void shouldWriteErrorlogTologFile() throws FileNotFoundException {

        log.clear();
        log.out(new IllegalAccessException("test error").getMessage());

        assertThat(log.getLogFile()).exists();

        Scanner scan = new Scanner(log.getLogFile());
        assertThat(scan.nextLine()).contains("test error");
        scan.close();

        // clean up
        assertThat(log.delete()).isTrue();
    }

    @Test
    public void shouldWriteIntlogTologFile() throws FileNotFoundException {

        log.clear();
        log.out(4);
        assertThat(log.getLogFile()).exists();

        Scanner scan = new Scanner(log.getLogFile());
        assertThat(scan.nextLine()).contains("4");
        scan.close();

        // clean up
        assertThat(log.delete()).isTrue();

    }

    @Test
    public void shouldWriteBooleanlogTologFile() throws FileNotFoundException {

        log.clear();
        log.out(true);

        assertThat(log.getLogFile()).exists();

        Scanner scan = new Scanner(log.getLogFile());
        assertThat(scan.nextLine()).contains("true");
        scan.close();

        // clean up
        assertThat(log.delete()).isTrue();

    }

    @Test
    public void shouldWriteLonglogTologFile() throws FileNotFoundException {

        log.clear();
        long test = 999999999999999999L;
        log.out(test);

        assertThat(log.getLogFile()).exists();

        Scanner scan = new Scanner(log.getLogFile());
        assertThat(scan.nextLine()).contains("999999999999999999");
        scan.close();

        // clean up
        assertThat(log.delete()).isTrue();

    }

    @Test
    public void shouldWriteCharlogTologFile() throws FileNotFoundException {

        log.clear();
        char test = 'c';
        log.out(test);

        assertThat(log.getLogFile()).exists();

        Scanner scan = new Scanner(log.getLogFile());
        assertThat(scan.nextLine()).contains("c");
        scan.close();

        // clean up
        assertThat(log.delete()).isTrue();

    }

    @Test
    public void shouldSetlogPriorityDEBUG() throws FileNotFoundException {

        log.clear();
        log.level(Level.DEBUG);
        log.out("test");

        Scanner scan = new Scanner(log.getLogFile());
        assertThat(scan.nextLine()).contains("DEBUG");
        scan.close();

        // clean up
        assertThat(log.delete()).isTrue();
    }

    @Test
    public void shouldSetlogPriorityWARNING() throws FileNotFoundException {

        log.clear();
        log.level(Level.WARNING);
        log.out("test");

        Scanner scan = new Scanner(log.getLogFile());
        assertThat(scan.nextLine()).contains("WARNING");
        scan.close();

        // clean up
        assertThat(log.delete()).isTrue();

    }

    @Test
    public void shouldSetlogPriorityINFO() throws FileNotFoundException {

        log.clear();
        log.out("test");

        Scanner scan = new Scanner(log.getLogFile());
        assertThat(scan.nextLine()).contains("INFO");
        scan.close();

        // clean up
        assertThat(log.delete()).isTrue();

    }

    @Test
    public void shouldSetlogPriorityERROR() throws FileNotFoundException {

        log.clear();
        log.level(Level.ERROR);
        log.out("test");

        Scanner scan = new Scanner(log.getLogFile());
        assertThat(scan.nextLine()).contains("ERROR");
        scan.close();

        // clean up
        assertThat(log.delete()).isTrue();

    }

    @Test
    public void shouldUpdatelogLocation() throws FileNotFoundException {

        log.clear();
        log.location("testlogOne");

        log.out("this is the test line");

        assertThat(log.getLogFile()).exists();
        Scanner scan = new Scanner(new File(log.getLocation()));

        assertThat(scan.nextLine()).contains("this is the test line");
        scan.close();

        // clean up
        assertThat(log.delete()).isTrue();

    }

    @Test
    public void shouldUpdatelogName() throws FileNotFoundException {

        log.name("second log");

        log.out("this is the test line for the second log location");

        assertThat(log.getLogFile()).exists();

        Scanner scan = new Scanner(new File(log.getLocation()));
        assertThat(scan.nextLine()).contains("this is the test line for the second log location");
        scan.close();

        // clean up
        assertThat(log.delete()).isTrue();
    }

    @Test
    public void shouldDeletelogFile() {
        log.out("test line");
        assertThat(log.getLogFile()).exists();

        assertThat(log.delete()).isTrue();
        assertThat(log.getLogFile()).doesNotExist();
    }

    @Test
    public void shouldSearchlogFile() {

        log.out("this is the first line");
        log.level(Level.WARNING);
        log.out("this should only be searched on the second search call");
        log.out("this is the second line");
        log.level(Level.ERROR);
        log.out("this is the line that should be searched for");
        log.level(Level.ERROR);
        log.out("this line should also be searched for");

        String searchLine = log.search("ERROR");

        assertThat(searchLine).contains("this is the line that should be searched for")
                .contains("this line should also be searched for");

        searchLine = log.search("ERROR, WARNING");

        assertThat(searchLine).contains("this should only be searched on the second search call");

        // clean up
        assertThat(log.delete()).isTrue();
    }

    @Test
    public void shouldReturnAllLinesFromError() {

        log.out("first line should not return");
        log.out(new IllegalArgumentException("this is the line that should be returned"));
        log.out("second line should not return");
        log.out("third line of the test");
        log.out(new NullPointerException("this line should also be returned"));

        assertThat(log.search("ERROR"))
                .contains("this is the line that should be returned")
                .contains("this line should also be returned");

        // clean up
        assertThat(log.delete()).isTrue();
    }

    @Test
    public void shouldCreateAndAccessTwoSeperateLogs(){

        Log startup = Log.get("test/startup");

        Log initialisation = Log.get("test/initialisation");

        Log finalise = Log.get("finalise");

        startup.out("startup has begun");
        startup.level(Level.WARNING);
        startup.out("startup has encountered an error");
        startup.out(new NullPointerException());

        initialisation.out("initialisation has begun");
        initialisation.level(Level.DEBUG);
        initialisation.out("debugging has begun");

        finalise.out("finalisation has begun");
        finalise.out("process successful");

        assertThat(startup).isNotNull();
        assertThat(startup.getLogFile()).exists();

        assertThat(initialisation).isNotNull();
        assertThat(initialisation.getLogFile()).exists();

        assertThat(finalise).isNotNull();
        assertThat(finalise.getLogFile()).exists();

    }
}
