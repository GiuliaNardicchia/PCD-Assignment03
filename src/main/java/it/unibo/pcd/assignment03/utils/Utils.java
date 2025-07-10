package it.unibo.pcd.assignment03.utils;

import java.util.Random;

public class Utils {
    public static int randomColor() {
        Random rand = new Random();
        return rand.nextInt(256 * 256 * 256);
    }

}
