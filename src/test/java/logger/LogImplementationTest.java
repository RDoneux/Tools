package logger;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.Random;
import java.util.Scanner;

public class LogImplementationTest {

    @Test
    public void shouldTestLogImplementation() {

        Log.name("test log");
        Log.out("Generating random number between 1 - 10");
        int number = new Random().nextInt(10);
        Log.out("Random number generated: " + number);

        Log.out("While loop created with argument to repeat until correct number guessed");
        boolean guessed = false;
        Log.out("boolean controlling while loop created and set to: false");
        while (!guessed) {
            System.setIn(new ByteArrayInputStream(String.valueOf(new Random().nextInt(15)).getBytes()));
            Log.out("input stream created");
            Log.out("Creating Scanner to receive user input");
            Scanner scan = new Scanner(System.in);
            if (scan != null) {
                Log.out("Scanner class created... waiting for user input");
                Log.newLine();
            } else {
                Log.level(Level.WARNING);
                Log.out("Error creating scanner class. Scan not created");
                Log.level(Level.ERROR);
                Log.out(new NullPointerException());
            }
            int guess = Integer.valueOf(scan.nextLine());
            Log.out(guess);
            if (guess < 11 && guess > 0) {
                if (guess == number) {
                    Log.out("correct number chosen: " + guess);
                    guessed = true;
                    Log.out("boolean controlling while loop updated to: true");
                } else {
                    Log.out("incorrect number chosen: " + guess);
                }
            } else {
                Log.level(Level.WARNING);
                Log.out("invalid number chosen: " + guess);
                Log.out(new IllegalArgumentException());
            }
        }

        Log.out("while loop terminated");

        Log.showLog();
        Log.search("ERROR");

    }

}
