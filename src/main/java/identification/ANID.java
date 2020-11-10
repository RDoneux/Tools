package identification;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class ANID {

    private final String token;

    public ANID() {
        token = buildToken();
    }

    private String buildToken() {
        try {
            Random r = SecureRandom.getInstanceStrong();
            StringBuilder build = new StringBuilder();
            for (int i = 0; i < 24; i++) {
                if (r.nextDouble() < 0.5) {
                    build.append((char) (r.nextInt(26) + 'a'));
                } else {
                    build.append(r.nextInt(9));
                }
            }
            return build.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean compare(ANID identifier) {
        return identifier.getToken().equals(this.token);
    }

    public boolean compare(String token) {
        return token.equals(this.token);
    }

    public String getToken() {
        return token;
    }

}
