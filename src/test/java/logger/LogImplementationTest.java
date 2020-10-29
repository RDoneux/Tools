package logger;

import org.junit.Test;

public class LogImplementationTest {

    @Test
    public void shouldTestLogImplementation(){

        Log.out("This is the first line of the log");
        Log.newLine();
        Log.out("there should be a line between the last line and this");

        Exception e = new NullPointerException("there is nothing here!");

        Log.newLine();
        Log.out(e);

        Log.showLog();

    }

}
