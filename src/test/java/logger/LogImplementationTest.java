package logger;

import org.junit.Test;

public class LogImplementationTest {

    @Test
    public void shouldTestLogImplementation(){

        Log.showLog();
        Log.out("This is the first line of the log");
        Log.newLine();
        Log.out("there should be a line between the last line and this");

        Exception e = new NullPointerException("there is nothing here!");
        Log.out(e);

        Log.level(Log.Level.WARNING);
        Log.out("this should be a warning");
        Log.out("this should not be a warning");

    }

}
