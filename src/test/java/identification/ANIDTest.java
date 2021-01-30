package identification;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ANIDTest {

    @Test
    public void shouldCreateId() {
        String id = new ANID().getToken();

        assertThat(id).hasSize(24);
    }

    @Test
    public void shouldCreateUniqueID() {
        ANID[] id = new ANID[10];

        for (int i = 0; i < id.length; i++) {
            id[i] = ANID.createToken();
        }

        for (int j = 0; j < id.length; j++) {
            for (int i = id.length - 1; i > 0; i--) {
                if(j != i) {
                    assertThat(id[j]).isNotEqualTo(id[i]);
                }
            }
        }
    }

    @Test
    public void shouldCompareUniqueId() {

        ANID id = new ANID();
        String token = id.getToken();

        assertThat(id.compare(id)).isTrue();
        assertThat(id.compare(token)).isTrue();

    }

    @Test
    public void shouldClearAllTokens(){

        ANID.clearTokens();

        ANID[] id = new ANID[3];

        for (int i = 0; i < id.length; i++) {
            id[i] = ANID.createToken();
        }

        assertThat(ANID.getTokensLength()).isEqualTo(3);

        ANID.clearTokens();

        assertThat(ANID.getTokensLength()).isZero();
    }

    @Test
    public void shouldRemoveToken(){

        ANID id = ANID.createToken();

        assertThat(ANID.exists(id)).isTrue();

        ANID.removeToken(id);

        assertThat(ANID.exists(id)).isFalse();

        // test string token as well
        id = ANID.createToken();
        String token = id.getToken();

        assertThat(ANID.exists(token)).isTrue();

        ANID.removeToken(token);

        assertThat(ANID.exists(token)).isFalse();
    }


}
