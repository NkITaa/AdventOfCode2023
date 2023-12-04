package com.nikita.day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DayThree {
    private void getData(char[][] motorMatrix) throws IOException {

        BufferedReader bf = new BufferedReader(new FileReader("inputs/day3.txt"));

        String line = bf.readLine();
        int i = 0;

        while (line != null) {
            motorMatrix[i++] = line.toCharArray();
            line = bf.readLine();
        }

        bf.close();
    }

    private boolean isSpecialChar(char current) {
        if (current == '.') return false;
        if (current == '0') return false;
        if (current == '1') return false;
        if (current == '2') return false;
        if (current == '3') return false;
        if (current == '4') return false;
        if (current == '5') return false;
        if (current == '6') return false;
        if (current == '7') return false;
        if (current == '8') return false;
        if (current == '9') return false;

        return true;
    }

    private boolean isNum(char current) {
        return current < ':' && current > '/';
    }

    // part One
    public int addRevelantMotorNums() throws IOException {

        char[][] motorMatrix = new char[140][140];
        int sum = 0;
        getData(motorMatrix);

        for (int i = 0; i < motorMatrix.length; i++) {


            for (int j = 0; j < motorMatrix[0].length; j++) {

                char currentChar = motorMatrix[i][j];
                boolean relevant = false;
                boolean isNum = currentChar < ':' && currentChar > '/';

                if (isNum) {

                    // check up down
                    if (i > 0 && isSpecialChar(motorMatrix[i-1][j])) relevant = true;
                    else if (i < motorMatrix.length - 1 && isSpecialChar(motorMatrix[i+1][j])) relevant = true;

                    // check diagonally
                    else if (i > 0 && j > 0 && isSpecialChar(motorMatrix[i-1][j-1])) relevant = true;
                    else if (i > 0 && j < motorMatrix.length - 1 && isSpecialChar(motorMatrix[i-1][j+1])) relevant = true;
                    else if (i < motorMatrix.length - 1 && j < motorMatrix.length - 1 && isSpecialChar(motorMatrix[i+1][j+1])) relevant = true;
                    else if (i < motorMatrix.length - 1 && j > 0 && isSpecialChar(motorMatrix[i+1][j-1])) relevant = true;

                    // check left right
                    else if (j > 0 && isSpecialChar(motorMatrix[i][j-1])) relevant = true;
                    else if (j < motorMatrix.length - 1 && isSpecialChar(motorMatrix[i][j+1])) relevant = true;

                }


                if (isNum && relevant) {
                    int[] numAndBorder = getFullNum(i, j, motorMatrix);
                    sum += numAndBorder[0];
                    j = numAndBorder[2] + 1;
                }
            }
        }
        return sum;
    }

    public int[] getFullNum(int i, int j, char[][] motorMatrix) {
        int l = j;
        int r = j;
        boolean completeNum = false;

        while (!completeNum) {

            if (l - 1 >= 0 && motorMatrix[i][l-1] < ':' && motorMatrix[i][l-1] > '/') --l;
            if (r + 1 <= motorMatrix.length - 1 && motorMatrix[i][r + 1] < ':' && motorMatrix[i][r + 1] > '/') ++r;

            if (l == 0 || (motorMatrix[i][l - 1] >= ':' || motorMatrix[i][l - 1] <= '/') && (r == motorMatrix.length - 1 || (motorMatrix[i][r + 1] >= ':' || motorMatrix[i][r + 1] <= '/')))
                completeNum = true;
        }

        StringBuilder num = new StringBuilder();
        while (l <= r) num.append(motorMatrix[i][l++]);
        return new int[]{Integer.parseInt(num.toString()), l, r};
    }

    public int adjacentStar() throws IOException {

        char[][] motorMatrix = new char[140][140];
        int sum = 0;
        getData(motorMatrix);

        for (int i = 0; i < motorMatrix.length; i++) {
            for (int j = 0; j < motorMatrix[0].length; j++) {

                if (motorMatrix[i][j] == '*') {

                    int k = 0;
                    int[][] coordinates = new int[2][3];

                    //check above
                    if (i < motorMatrix.length - 1) {
                        if (isNum(motorMatrix[i+1][j])) coordinates[k++] = getFullNum(i+1, j, motorMatrix);
                        if (j < motorMatrix.length - 1 && isNum(motorMatrix[i+1][j+1])) {
                            if (k == 1) {
                                int[] temp = getFullNum(i+1, j+1, motorMatrix);

                                if (temp[0] != coordinates[0][0] || temp[1] != coordinates[0][1] || temp[2] != coordinates[0][2]) coordinates[k++] = temp;
                            }
                            if (k == 0) coordinates[k++] = getFullNum(i+1, j+1, motorMatrix);
                        }
                        if (j > 0 && isNum(motorMatrix[i+1][j-1]) && k < 2) {
                            if (k == 1) {
                                int[] temp = getFullNum(i+1, j-1, motorMatrix);

                                if (temp[0] != coordinates[0][0] || temp[1] != coordinates[0][1] || temp[2] != coordinates[0][2]) coordinates[k++] = temp;
                            }
                            if (k == 0) coordinates[k++] = getFullNum(i+1, j-1, motorMatrix);
                        }
                    }



                    //check below
                    if (i > 0) {
                        if (isNum(motorMatrix[i-1][j]) && k < 2 ) {
                            if (k == 1) {
                                int[] temp = getFullNum(i-1, j, motorMatrix);

                                if (temp[0] != coordinates[0][0] || temp[1] != coordinates[0][1] || temp[2] != coordinates[0][2]) coordinates[k++] = temp;
                            }
                            if (k == 0) coordinates[k++] = getFullNum(i-1, j, motorMatrix);
                        };
                        if (j > 0 && isNum(motorMatrix[i-1][j-1]) && k < 2) {
                            if (k == 1) {
                                int[] temp = getFullNum(i-1, j-1, motorMatrix);

                                if (temp[0] != coordinates[0][0] || temp[1] != coordinates[0][1] || temp[2] != coordinates[0][2]) coordinates[k++] = temp;
                            }
                            if (k == 0) coordinates[k++] = getFullNum(i-1, j-1, motorMatrix);
                        }
                        if (j < motorMatrix.length - 1 && isNum(motorMatrix[i-1][j+1]) && k < 2) {
                            if (k == 1) {
                                int[] temp = getFullNum(i-1, j+1, motorMatrix);

                                if (temp[0] != coordinates[0][0] || temp[1] != coordinates[0][1] || temp[2] != coordinates[0][2]) coordinates[k++] = temp;
                            }
                            if (k == 0) coordinates[k++] = getFullNum(i-1, j+1, motorMatrix);
                        }
                    }


                    // check left right
                    if (j > 0 && isNum(motorMatrix[i][j-1]) && k < 2) {
                        if (k == 1) {
                            int[] temp = getFullNum(i, j-1, motorMatrix);

                            if (temp[0] != coordinates[0][0] || temp[1] != coordinates[0][1] || temp[2] != coordinates[0][2]) coordinates[k++] = temp;
                        }
                        if (k == 0) coordinates[k++] = getFullNum(i, j-1, motorMatrix);
                    }
                    if (j < motorMatrix.length - 1 && isNum(motorMatrix[i][j+1]) && k < 2) {
                        if (k == 1) {
                            int[] temp = getFullNum(i, j+1, motorMatrix);

                            if (temp[0] != coordinates[0][0] || temp[1] != coordinates[0][1] || temp[2] != coordinates[0][2]) coordinates[k++] = temp;
                        }
                        if (k == 0) coordinates[k++] = getFullNum(i, j+1, motorMatrix);
                    }

                    if (k == 2) {
                        int product = coordinates[0][0] * coordinates[1][0];
                        sum += product;
                    }
                }
            }
        }

        return sum;
    }
}
