package com.nikita.day4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class DayFour {
    private void getData(int[][] winningNums, int[][] myNums) throws IOException {

        BufferedReader bf = new BufferedReader(new FileReader("inputs/day4.txt"));

        String line = bf.readLine();
        int j = 0;

        while (line != null) {

            String[] parts = line.split("[:|\\\\|]");

            for(int i = 1; i < parts.length; i++) {

                int l = 0;
                int r = 3;
                int x = 0;
                String part = parts[i];

                while (r <= part.length()) {

                    int temp = Integer.parseInt(part.substring(l, r).trim());
                    l += 3;
                    r += 3;

                    if (i == 1) winningNums[j][x++] = temp;
                    if (i == 2) myNums[j][x++] = temp;
                }

                if (i == 1) Arrays.sort(winningNums[j]);
                if (i == 2) Arrays.sort(myNums[j]);
            }

            j++;
            line = bf.readLine();
        }

        bf.close();
    }

    // Part One
    public int getPoints() throws IOException {

        int[][] winningNums = new int[203][10];
        int[][] myNums = new int[203][25];

        int sum = 0;
        getData(winningNums, myNums);

        for (int i = 0; i < winningNums.length; i++) {

            int pointerWinning = 0;
            int pointerMine = 0;
            int hits = -1;

            while (pointerMine < myNums[0].length && pointerWinning < winningNums[0].length) {

                if (winningNums[i][pointerWinning] == myNums[i][pointerMine]) {
                    ++pointerMine;
                    ++pointerWinning;
                    ++hits;
                }

                else if (winningNums[i][pointerWinning] < myNums[i][pointerMine]) ++pointerWinning;
                else ++pointerMine;

            }

            if (hits >= 0) sum += (int) Math.pow(2, hits);
        }

        return sum;
    }

    public int getPointsNewRules() throws IOException {

        int[][] winningNums = new int[203][10];
        int[][] myNums = new int[203][25];
        int[] multipliers = new int[203];
        Arrays.fill(multipliers, 1);

        int sum = 0;
        getData(winningNums, myNums);

        for (int i = 0; i < winningNums.length; i++) {

            int pointerWinning = 0;
            int pointerMine = 0;
            int hits = 0;

            while (pointerMine < myNums[0].length && pointerWinning < winningNums[0].length) {

                if (winningNums[i][pointerWinning] == myNums[i][pointerMine]) {
                    ++pointerMine;
                    ++pointerWinning;
                    ++hits;
                }

                else if (winningNums[i][pointerWinning] < myNums[i][pointerMine]) ++pointerWinning;
                else ++pointerMine;

            }

            sum += multipliers[i];
            if (hits != 0) {
                for (int j = 1; j <= hits && i + j < winningNums.length; j++) multipliers[i+j] += multipliers[i];
            }
        }

        return sum;
    }
}
