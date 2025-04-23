package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws IOException {
        String BOARD_FILE = "board.txt";
        int LOOP_COUNT = 5;
        int BOARD_SIZE = 10;

        Board b = new Board(BOARD_FILE, BOARD_SIZE);
        b.run(LOOP_COUNT);
    }
}