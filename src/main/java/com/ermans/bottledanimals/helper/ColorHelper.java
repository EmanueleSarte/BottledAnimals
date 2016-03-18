package com.ermans.bottledanimals.helper;

public class ColorHelper {
    public static int rgb(int red, int green, int blue) {
        int color = 0;
        color |= 0xFF000000;
        color |= (red & 0xFF) << 16;
        color |= (green & 0xFF) << 8;
        color |= blue & 0xFF;
        return color;
    }
}


