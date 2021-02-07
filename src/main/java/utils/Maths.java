package utils;

public class Maths {

    public static int getLow(int x, int y) {
        return ((x < y) ? x : y);
    }

    public static int getHigh(int x, int y) {
        return ((x < y) ? y : x);
    }


}
