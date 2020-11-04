package logger;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LogStoreTest {

    @Test
    public void shouldAddNewLogToLogStore(){

        Log log = Log.get("location");

        assertThat(log).isNotNull();
        assertThat(log.getLocation()).isEqualTo("location.log");

    }

    @Test
    public void shouldGetExistingLogFromLogStore(){
        Log.get("location");
        Log.get("Second Log Location");

        assertThat(Log.get("location")).isNotNull();
        assertThat(Log.get("Second Log Location")).isNotNull();

        Log location = Log.get("location");
        Log secondLog = Log.get("Second Log Location");

        assertThat(location).isNotNull();
        assertThat(secondLog).isNotNull();

    }

}
