package com.company;

import java.util.HashMap;

import java.util.*;
import java.io.File;

public class WordSearchBoard {

    private HashMap<String, int[]> DIRS = WordFunc.getDIRS();

    private ArrayList<String> words = new ArrayList<String>();
    private ArrayList<String> placedWords = new ArrayList<String>();

    private int numRows;
    private int numCols;

    private char[][] answerBoard;
    private char[][] board;

    // Number of tries to randomly place word on board before skipping
    private int threshold;

    public WordSearchBoard(int rows, int cols, int thresholdNum)
    {
        this.numRows = rows;
        this.numCols = cols;

        this.threshold = thresholdNum;

        // Board will be filled in with random chars while answer board only has the words
        this.board = new char[rows][cols];
        this.answerBoard = new char[rows][cols];

        // Read in list of words from file
        this.words = getWordsFromFile("./t.txt");

        this.resetBoard();

        // Attempt to place each word with random orientation on board
        this.populateBoard();

        this.answerBoard = this.copyArray(this.board, this.answerBoard);

        this.fillBoardRandom();
    }


    /**
     * Attempts to place each word on board with random orientation
     * Will skip word if it is longer than horizontal/vertical bounds
     */
    public void populateBoard()
    {

        // Attempt to place each word on board randomly
        // Will skip words that are  too long
        for (String currentWord : this.words)
        {
            if (currentWord.length() <= numCols || currentWord.length() <= numRows)
            {
                int result = placeWord(currentWord);
                if (result == 0)
                    this.placedWords.add(currentWord);
            }

            else
            {
                System.out.println("The word " + currentWord + " is too long, skipping...");
            }

        }

    }

    /**
     * Attempts to place word on board with random orientation
     * @return 0 if word is successfully placed, 1 if number of attempts exceeds threshold
     */
    int placeWord(String word)
    {
        boolean placed = false;
        int attempts = 0;

        while (!placed)
        {
            // Get random coordinates for row & col
            int xPos = (int) (Math.random()*numCols);
            int yPos = (int) (Math.random()*numRows);

            // Get random direction out of 8 including diagonals
            String[] directions = {"n","ne","e","se","s","sw","w","nw"};
            String direction = directions[(int) (Math.random()*directions.length)];

            // If word stays within bounds of board and overlaps properly with other letters, place word
            boolean wordIsValid = checkPlacement(xPos, yPos, direction, word);
            if (wordIsValid)
            {
                for (int i = 0; i < word.length(); i++)
                {
                    board[yPos][xPos] = word.charAt(i);

                    // Access direction hashmap that stores positive/negative int on x and y based on direction
                    xPos += DIRS.get(direction)[0];
                    yPos += DIRS.get(direction)[1];
                }

                placed = true;
            }

            else
                attempts++;

            // Return exit value of 1 if number of attempts exceeds threshold
            if (attempts > this.threshold)
            {
                System.out.println(word + " could not be placed within the number of attempts allowed.");
                return 1;
            }
        }

        return 0;

    }

    /**
     * Validates if word placed at coordinates x and y with orientation dir will stay within bounds of board,
     * as well as overlaps with other words with same letter at that location
     *
     * @return true if word can be placed at location, false if illegal
     */
    boolean checkPlacement(int x, int y, String dir, String word)
    {
        // N E S W directional board bounds
        if (dir.equals("n") && y+1 - word.length() < 0) {return false;}
        else if (dir.equals("e") && x + word.length() > numCols) { return false; }
        else if (dir.equals("s") && y + word.length() > numRows) {return false;}
        else if (dir.equals("w") && x+1 - word.length() < 0) { return false; }

        // NE SE SW NW directional board bounds
        else if (dir.equals("ne") && (y+1 - word.length() < 0 || x + word.length() > numCols)) { return false; }
        else if (dir.equals("se") && (y + word.length() > numRows || x + word.length() > numCols)) { return false; }
        else if (dir.equals("sw") && (y + word.length() > numRows || x+1 - word.length() < 0)) { return false; }
        else if (dir.equals("nw") && (y+1 - word.length() < 0 || x+1 - word.length() < 0)) { return false; }

        // Since word fits within bounds, now step through each letter to ensure each space is either blank
        // or the same letter as a different word's letter at that location
        for (int i = 0; i < word.length(); i++)
        {
            if (board[y][x] != '.' && board[y][x] != word.charAt(i))
            {
                return false;
            }
            x += DIRS.get(dir)[0];
            y += DIRS.get(dir)[1];
        }

        // Passed checks
        return true;
    }

    /**
     * Reads input line by line from file at path
     * @return ArrayList of words for the word search
     */
    ArrayList<String> getWordsFromFile(String path)
    {
        try
        {
            Scanner s = new Scanner(new File(path));

            while (s.hasNext()) {
                this.words.add(s.next().toUpperCase());
            }
            s.close();

        }

        catch (Exception e)
        {
            System.out.println(e);
        }

        return words;
    }

    /**
     * Prints list of words to search for to console
     */
    public void printWords()
    {
        System.out.println("**** WORD LIST ****");
        System.out.println("-------------------");
        for (String word : this.placedWords)
        {
            System.out.println(word);
        }
    }

    /**
     * Changes each value in 2d array of board to be a char e.g. period  .
     */
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

    /**
     * Changes each empty value in 2d array board to be a random capital A-Z letter
     */
    public void fillBoardRandom()
    {
        for (int r = 0; r < this.numRows; r++)
        {
            for (int c = 0; c < this.numCols; c++)
            {
                if (this.board[r][c] == '.')
                {
                    this.board[r][c] = WordFunc.getRandomChar();
                }

            }
        }
    }

    /**
     * Prints the 2d char array word search board to console
     * @param isAnswer controls whether the answer board is printed, or the word search board with random letters in it
     */
    public void printBoard(boolean isAnswer)
    {
        for (int r = 0; r < this.numRows; r++)
        {
            for (int c = 0; c < this.numCols; c++)
            {
                if (isAnswer)
                    System.out.print(this.answerBoard[r][c] + " ");
                else
                    System.out.print(this.board[r][c] + " ");
            }
            System.out.print("\n");
        }
    }

    /**
     * Copies each value from 2d char array arrayA to arrayB
     * @return 2d char array with values copied from arrayA
     */
    char[][] copyArray(char[][] arrayA, char[][] arrayB)
    {
        for (int r = 0; r < arrayA.length; r++)
        {
            for (int c = 0; c < arrayA[0].length; c++)
            {
                arrayB[r][c] = arrayA[r][c];
            }
        }
        return arrayB;
    }


}
