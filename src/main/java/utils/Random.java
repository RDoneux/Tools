package utils;

public class Random {

    private static java.util.Random r = new java.util.Random();

    public static Integer getInt(int low, int high) {
        return r.nextInt(high - low) + low;
    }

    public static Integer getInt() {
        return r.nextInt();
    }

}
