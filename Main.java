package com.company;

public class Main {

    public static void main(String[] args)
    {
        WordSearchBoard a = new WordSearchBoard(12,18, 1000);

        a.printBoard(true);

        System.out.println();

        a.printBoard(false);

        System.out.println();

        a.printWords();
    }
}
