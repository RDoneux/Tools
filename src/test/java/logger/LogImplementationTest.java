package logger;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.Random;
import java.util.Scanner;

public class LogImplementationTest {

    @Test
    public void shouldTestLogImplementation() {

        Log log = Log.get("test log");

        log.out("Generating random number between 1 - 10");
        int number = new Random().nextInt(10);
        log.out("Random number generated: " + number);

        log.out("While loop created with argument to repeat until correct number guessed");
        boolean guessed = false;
        log.out("boolean controlling while loop created and set to: false");
        while (!guessed) {
            System.setIn(new ByteArrayInputStream(String.valueOf(new Random().nextInt(15)).getBytes()));
            log.out("input stream created");
            log.out("Creating Scanner to receive user input");
            Scanner scan = new Scanner(System.in);
            if (scan != null) {
                log.out("Scanner class created... waiting for user input");
                log.newLine();
            } else {
                log.level(Level.WARNING);
                log.out("Error creating scanner class. Scan not created");
                log.level(Level.ERROR);
                log.out(new NullPointerException());
            }
            int guess = Integer.valueOf(scan.nextLine());
            log.out(guess);
            if (guess < 11 && guess > 0) {
                if (guess == number) {
                    log.out("correct number chosen: " + guess);
                    guessed = true;
                    log.out("boolean controlling while loop updated to: true");
                } else {
                    log.out("incorrect number chosen: " + guess);
                }
            } else {
                log.level(Level.WARNING);
                log.out("invalid number chosen: " + guess);
                log.out(new IllegalArgumentException());
            }
        }

        log.out("while loop terminated");

        log.showLog();
        log.search("ERROR");

    }

}
