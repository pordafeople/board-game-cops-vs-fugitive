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
    // for convenience, we store the positions of the fugitive and the cops
    int fugitivePos;
    int copsPos;
    int treePos;

    public Board(String fileName, int boardSize) throws IOException {
        out = new FileWriter(fileName);
        board = new String[boardSize];

        // fill with houses
        for (int i = 0; i < boardSize; i++) {
            board[i] = HOUSE;
        }

        // place alejandro at the 6th index
        fugitivePos = 5;
        board[fugitivePos] += FUGITIVE;

        // place the cops at the 1st index
        copsPos = 0;
        board[copsPos] += COPS;

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
        int diff = a - b;
        int modularDiff = (diff + board.length) % board.length;
        int half = board.length / 2;
        int signedDiff = (modularDiff + half) % board.length - half;
        return Math.abs(signedDiff);
    }

    void write() throws IOException {
        for (int i = 0; i < board.length; i++) {
            out.write(String.format("[%s]\t", board[i]));
        }
        out.write("\n");
    }

    public void run(int loopCount) throws IOException {
        write();
        boolean fugitiveHiddenBuff = false;
        boolean fugitiveRestrictionDebuff = false;
        boolean captured = false;
        for (int i = 0; i < loopCount; i++) {
            // first we roll the dice
            int fugitiveRoll = diceRoll(fugitiveRestrictionDebuff ? 3 : 6);
            int copsRoll = diceRoll(6);

            // then both of them leave the board and leave their traces
            board[fugitivePos] = fugitivePos == treePos ? TREE : EVIDENCE;
            board[copsPos] = copsPos == treePos ? TREE : RESTRICTION;
            // then they move to their next positions
            moveFugitive(fugitiveRoll + (fugitiveHiddenBuff ? 1 : 0));
            moveCops(copsRoll);

            // then we check for buffs and debuffs on the landing positions
            fugitiveHiddenBuff = fugitivePos == treePos;
            fugitiveRestrictionDebuff = board[fugitivePos].equals(RESTRICTION);
            boolean copsEvidenceBuff = board[copsPos].equals(EVIDENCE);
            int captureRadius = copsEvidenceBuff ? 2 : 1;

            // then we place them back on the board
            board[fugitivePos] += FUGITIVE;
            board[copsPos] += COPS;
            // then we display the board
            write();

            // then check if the cops can capture the fugitive
            if (modularDistance(fugitivePos, copsPos) <= captureRadius && !fugitiveHiddenBuff) {
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
