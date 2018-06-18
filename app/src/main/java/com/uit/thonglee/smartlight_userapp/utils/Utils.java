package com.uit.thonglee.smartlight_userapp.utils;

public class Utils {
    public static int getRed(String rbg){
        char[] red = {'0','0','0'};
        char[] color = rbg.toCharArray();
        for (int i = 0; i < 3; i++){
            red[i] = color[i];
        }
        return Integer.parseInt(String.valueOf(red));
    }

    public static int getGreen(String rbg){
        char[] green = {'0','0','0'};
        char[] color = rbg.toCharArray();
        for (int i = 0; i < 3; i++){
            green[i] = color[i+3];
        }
        return Integer.parseInt(String.valueOf(green));
    }

    public static int getBlue(String rbg){
        char[] blue = {'0','0','0'};
        char[] color = rbg.toCharArray();
        for (int i = 0; i < 3; i++){
            blue[i] = color[i+6];
        }
        return Integer.parseInt(String.valueOf(blue));
    }
}
