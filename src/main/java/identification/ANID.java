package identification;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class ANID {

    private final String token;

    private static CopyOnWriteArrayList<ANID> tokens = new CopyOnWriteArrayList<>();

    public ANID() {
        token = buildToken();
    }

    private ANID(String token) {
        this.token = token;
    }


    public static ANID createToken() {
        ANID temp = new ANID();
        for (ANID target : tokens) {
            if (target.compare(temp)) {
                createToken();
            }
        }
        tokens.add(temp);
        return temp;
    }

    public static ANID createFromToken(String token) {
        ANID temp = new ANID(token);
        for (ANID target : tokens) {
            if (target.compare(temp)) {
                throw new IllegalArgumentException("token already exists");
            }
        }
        return temp;
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
