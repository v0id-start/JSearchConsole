package com.example.wordsearch;

public class WordFunc {

    public static char getRandomChar()
    {
        char[] letters = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        return letters[ (int) (Math.random()*(26)) ];
    }
}
