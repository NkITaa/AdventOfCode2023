package com.nikita.day14;

import com.nikita.day12.DayTwelve;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class DayFourteen {

    private void getData(double[][] field) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader("inputs/day14.txt"));

        String line = bf.readLine();
        int i = 0;

        while (line != null) {

            char[] lineCharArray = line.toCharArray();

            for (int j = 0; j < lineCharArray.length; j++) {

                char currentChar = lineCharArray[j];

                if (currentChar == '.') field[i][j] = -1;
                else if (currentChar == '#') field[i][j] = -2;
                else field[i][j] = 0;

            }
            i++;
            line = bf.readLine();
        }

        bf.close();
    }

    private void moveStones(double[][] field) {
        for (int i = field.length - 1; i > 0; i--)
            for (int j = 0; j < field[0].length; j++) {
                if (field[i][j] >= 0) {
                    if (field[i-1][j] >= -1) {
                        field[i-1][j] += ++field[i][j];
                        field[i][j] = -1;
                    }
                }
            }
    }

    private int getWeight(double[][] field) {
        int weight = 0;
        for (int i = 0; i < field.length; i++)
            for (int j = 0; j < field[0].length; j++) {
                if (field[i][j] >= 0) {
                    for (int k = 0; k <= field[i][j]; k++)
                        weight += (field.length - k - i);
                }
            }
        return weight;
    }

    public int totalWeight() throws IOException {

        double[][] field = new double[100][100];
        getData(field);
        moveStones(field);
        return getWeight(field);
    }
    
    


    // Part TWO

    public long totalWeightTwo() throws IOException {

        char[][] field = new char[100][100];
        getDataTwoInterchanged(field);

        HashMap<String, Long> index = new HashMap();

        for (long i=0;i<1000000000;i++) {
            
            field = cycle(field);
            String str = toString(field);
            
            if (index.containsKey(str)) {
                long delta = i - index.get(str);
                i += delta * ((1000000000-i) / delta);
            }
            index.put(str, i);
        }
        return computeLoad(field);
    }

    private void getDataTwoInterchanged(char[][] field) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader("inputs/day14.txt"));

        String line = bf.readLine();
        int i = 0;
        int j = 0;

        while (line != null) {
            char[] temp = line.toCharArray();

            for (char t : temp)
                field[i++][j] = t;

            i = 0;
            j++;
            line = bf.readLine();
        }

        bf.close();
    }
    private static long computeLoad(char[][] field) {
        long result = 0;
        for (int j = 0; j < field[0].length; j++) {
            for (int i = 0 ; i < field.length; i++) {
                if (field[j][i] == 'O') {
                    result += field.length - i;
                }
            }
        }
        return result;
    }

    public static String toString(char[][] field) {

        StringBuffer sb = new StringBuffer();

        for (char[] line : field) {
            sb.append(new String(line));
        }

        return sb.toString();
    }

    private static char[][] rotate(char[][] field) {
        char[][] result = new char[100][100];
        for (int j = 0; j < field[0].length; j++) {
            for (int i = 0; i < field.length; i++) {
                result[j][i] = field[i][field.length - j -1];
            }
        }
        return result;
    }

    private static char[][] cycle(char[][] field) {
        for (int i = 0; i < 4; i++) field = rotate(moveNorth(field));
        return field;
    }

    private static char[][] moveNorth(char[][] field) {
        for (int j=0; j < field[0].length; j++) {

            boolean move = true;

            while (move) {

                move = false;

                for (int i = 1; i < field.length; i++) {

                    if (field[j][i] == 'O' && field[j][i-1] == '.') {
                        field[j][i] = '.';
                        field[j][i-1] = 'O';
                        move = true;
                    }

                }
            }
        }
        return field;
    }
}
