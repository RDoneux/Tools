package identification;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ANIDTest {

    @Test
    public void shouldCreateUniqueId(){
        String id = new ANID().getToken();

        assertThat(id).hasSize(24);

    }

    @Test
    public void shouldCompareUniqueId(){

        ANID id = new ANID();
        String token = id.getToken();

        assertThat(id.compare(id)).isTrue();
        assertThat(id.compare(token)).isTrue();

    }


}
