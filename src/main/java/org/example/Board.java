package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Board {
    static Random rand = new Random();
    public static final String HOUSE = "🏠";
    public static final String TREE = "🌳";
    public static final String FUGITIVE = "🏃";
    public static final String COPS = "🚓";
    public static final String EVIDENCE = "💰";
    public static final String RESTRICTION = "⛔";

    String[] board;
    FileWriter out;
    int fugitivePos;
    int copsPos;
    int treePos;

    public void test() {
//        for (int i = 0; i < 100; i++) {
//            int roll = diceRoll(6);
//            System.out.print(roll + " ");
//        }
    }

    public Board(String fileName, int boardSize) throws IOException {
        out = new FileWriter(fileName);
        board = new String[boardSize];

        // fill with houses
        for (int i = 0; i < boardSize; i++) {
            board[i] = HOUSE;
        }

        // place alejandro at the 6th index
        fugitivePos = 5;
        board[fugitivePos] = FUGITIVE;

        // place the cops at the 1st index
        copsPos = 0;
        board[copsPos] = COPS;

        // spawn a tree at any random location other than cop/fugitive
        do {
            treePos = rand.nextInt(boardSize);
        } while (treePos == 0 || treePos == 5);
        board[treePos] = TREE;
    }

    public static int diceRoll(int amount) {
        return rand.nextInt(1, amount + 1);
    }

    void moveFugitive(int amount) {
        fugitivePos += amount;
        fugitivePos %= board.length;
    }

    void moveCops(int amount) {
        copsPos += amount;
        copsPos %= board.length;
    }

    int modularDistance(int a, int b) {
        int rawDiff = a - b;
        int modularDiff = (rawDiff + board.length) % board.length;
        return Math.abs(modularDiff);
    }

    void write() throws IOException {
        for (int i = 0; i < board.length; i++) {
            out.write(String.format("[%s]", board[i]));
        }
        out.write("\n");
    }

    public void run(int loopCount) throws IOException {
        write();
        boolean hiddenBuff = false;
        boolean disadvantage = false;
        boolean captured = false;
        for (int i = 0; i < loopCount; i++) {
            int fugitiveRoll = diceRoll(disadvantage ? 3 : 6);
            int copsRoll = diceRoll(6);

            board[fugitivePos] = hiddenBuff ? TREE : EVIDENCE;
            moveFugitive(fugitiveRoll + (hiddenBuff ? 1 : 0));

            hiddenBuff = fugitivePos == treePos;
            disadvantage = board[fugitivePos].equals(RESTRICTION);

            board[copsPos] = copsPos == treePos ? TREE : RESTRICTION;
            moveCops(copsRoll);
            boolean copsEvidence = board[copsPos].equals(EVIDENCE);
            int captureRadius = copsEvidence ? 2 : 1;

            board[fugitivePos] = FUGITIVE;
            board[copsPos] = COPS;
            write();
            if (modularDistance(fugitivePos, copsPos) <= captureRadius && !hiddenBuff) {
                captured = true;
                break;
            }
        }
        if (captured) {
            out.write("Alejandro was captured. Success.");
        } else {
            out.write("Alejandro escaped.");
        }
        out.close();
    }
}
