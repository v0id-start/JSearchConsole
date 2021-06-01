package com.example.wordsearch;

import java.util.HashMap;

import java.util.*;
import java.io.File;

public class WordSearchBoard {

    public static HashMap<String, int[]> DIRS = new HashMap<String, int[]>();

    private int numRows;
    private int numCols;

    private char[][] board;

    public WordSearchBoard(int rows, int cols)
    {
        this.numRows = rows;
        this.numCols = cols;

        this.board = new char[rows][cols];
        this.resetBoard();
        this.populateBoard();

        DIRS.put("n", new int[]{0,-1});
        DIRS.put("s", new int[]{0,1});

        DIRS.put("e", new int[]{1,0});
        DIRS.put("w", new int[]{-1,0});

        DIRS.put("ne", new int[]{1,-1});
        DIRS.put("se", new int[]{1,1});
        DIRS.put("sw", new int[]{-1,1});
        DIRS.put("nw", new int[]{-1,-1});


    }
/*
x y
2 0
len 3 - 0 = 3

. . p .
. . . .
. . . .

 */
    boolean checkPlacement(int x, int y, String dir, String word)
    {
        System.out.println(x + " " + y + " " + dir);

        if (dir.equals("n") && y+1 - word.length() < 0) {return false;}
        if (dir.equals("e") && x + word.length() > numCols) { return false; }
        /*if (dir.equals("e") && word.length() + x > numCols) { return false; }
        if (dir.equals("e") && word.length() + x > numCols) { return false; }
        if (dir.equals("e") && word.length() + x > numCols) { return false; }
        if (dir.equals("e") && word.length() + x > numCols) { return false; }
        if (dir.equals("e") && word.length() + x > numCols) { return false; }
        if (dir.equals("e") && word.length() + x > numCols) { return false; }*/

        return true;
    }


    void placeWord(String word)
    {
        boolean placed = false;

        while (!placed)
        {
            // Get random x,y coordinates (starts at 0,0)
            int xPos = (int) (Math.random()*numCols);
            int yPos = (int) (Math.random()*numRows);

            // Get random direction out of 8 including diagonals
            //String[] directions = {"n","ne","e","se","s","sw","w","nw"};
            String[] directions = {"e"};
            String direction = directions[(int) (Math.random()*directions.length)];

            // If word fits and overlaps properly, place word
            if (checkPlacement(xPos,yPos,direction, word))
            {
                for (int i = 0; i < word.length(); i++) {

                    board[yPos][xPos] = word.charAt(i);
                    System.out.println(DIRS.get(direction)[0]);
                    System.out.println(DIRS.get(direction)[1]);
                    // Access hashmap that inc/dec x and y based on direction
                    xPos += DIRS.get(direction)[0];
                    yPos += DIRS.get(direction)[1];

                }

                placed = true;
            }
        }

    }


    public void populateBoard()
    {
        try {
            // Read in words from file
            String filePath = "./t.txt";
            Scanner s = new Scanner(new File(filePath));
            ArrayList<String> words = new ArrayList<String>();

            while (s.hasNext()) {
                words.add(s.next());
            }

            s.close();

            // Try to place each word in randomly if valid
            for (String currentWord : words)
            {
                if (currentWord.length() <= numCols || currentWord.length() <= numRows)
                {
                    boolean placed = false;

                    while (!placed) {
                        placeWord(currentWord);
                        placed = true;
                    }


                }
                else
                    System.out.println("The word " + currentWord + " is too long, skipping...");

            }


        }
        catch (Exception e)
        {
            System.out.println(e);
        }



    }

    public void resetBoard()
    {
        for (int r = 0; r < this.numRows; r++)
        {
            for (int c = 0; c < this.numCols; c++)
            {
                this.board[r][c] = '.';
            }
        }
    }

    public void printBoard()
    {
        for (int r = 0; r < this.numRows; r++)
        {
            for (int c = 0; c < this.numCols; c++)
            {
                System.out.print(this.board[r][c] + " ");
            }
            System.out.print("\n");
        }
    }
}
