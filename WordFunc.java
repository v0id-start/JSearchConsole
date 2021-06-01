package com.company;

import java.util.HashMap;

public class WordFunc {
    /**
     * Stores how a chosen direction would affect the x and y position of a word
     * index 0 of the int array value stores wither the x would increase by 1, stay the same, or decrease by 1
     * index 1 of the int array value does the same but for y
     *
     * When the string has two directions, the values are just a combination of the values of the 4 cardinal directions
     *
     * Example: direction 'n' changes the x by 0, and the y by -1 in order to place the next letter North of the current letter
     *
     * @return HashMap with direction key and int array value
     */
    public static HashMap<String, int[]> getDIRS()
    {
        HashMap<String, int[]> DIRS = new HashMap<String, int[]>();
        DIRS.put("n", new int[]{0,-1});
        DIRS.put("s", new int[]{0,1});

        DIRS.put("e", new int[]{1,0});
        DIRS.put("w", new int[]{-1,0});

        DIRS.put("ne", new int[]{1,-1});
        DIRS.put("se", new int[]{1,1});
        DIRS.put("sw", new int[]{-1,1});
        DIRS.put("nw", new int[]{-1,-1});

        return DIRS;
    }

    /**
     * @return random capital letter from A-Z
     */
    public static char getRandomChar()
    {
        char[] letters = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        return letters[ (int) (Math.random()*(26)) ];
    }
}
