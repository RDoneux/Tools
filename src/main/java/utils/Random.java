package utils;

public class Random {

    private static java.util.Random r = new java.util.Random();

    public static Integer getInt(int low, int high) {
        if (low == high) return low;
        return r.nextInt(high - low) + low;
    }

    public static double getDouble(double low, double high){
        if(low == high) return low;
        return r.nextDouble() * (high - low) + low;
        //double result = Math.random() * (high - low) + low;
    }

    public static Integer getInt() {
        return r.nextInt();
    }

}
