package com.nikita.day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DayOne {

    // Part One
    public int getCalibration() throws IOException {

        String[] calibrationData = new String[1000];
        getCalibrationData(calibrationData);

        int sum = 0;

        for (String calibration : calibrationData) {

            int l = 0;
            int r = calibration.length() - 1;
            char[] sol = new char[2];

            while (sol[0] == '\u0000' || sol[1] == '\u0000') {

                char currentL = calibration.charAt(l++);
                char currentR = calibration.charAt(r--);

                if (currentL - ':' < 0 && sol[0] == '\u0000') sol[0] = currentL;
                if (currentR - ':' < 0 && sol[1] == '\u0000') sol[1] = currentR;

            }

            sum += Integer.parseInt(new String(sol));
        }

        return sum;
    }



    // Part Two
    public int getCalibrationPartTwo() throws IOException {

        String[] calibrationData = new String[1000];
        getCalibrationData(calibrationData);

        int sum = 0;


        for (String calibration : calibrationData) {

            int l = 0;
            int r = calibration.length() - 1;
            char[] sol = new char[2];


            while (sol[0] == '\u0000' || sol[1] == '\u0000') {

                if (sol[0] == '\u0000') {
                    if (calibration.charAt(l) - ':' < 0) sol[0] = calibration.charAt(l);
                    else sol[0] = containsNum(calibration.substring(0, ++l));
                }

                if (sol[1] == '\u0000') {
                    if (calibration.charAt(r) - ':' < 0) sol[1] = calibration.charAt(r);
                    else sol[1] = containsNum(calibration.substring(r--));
                }

            }
            sum += Integer.parseInt(new String(sol));
        }

        return sum;
    }


    private char containsNum(String substring) {

        if (substring.contains("one")) return '1';
        else if (substring.contains("two")) return '2';
        else if (substring.contains("three")) return '3';
        else if (substring.contains("four")) return '4';
        else if (substring.contains("five")) return '5';
        else if (substring.contains("six")) return '6';
        else if (substring.contains("seven")) return '7';
        else if (substring.contains("eight")) return '8';
        else if (substring.contains("nine")) return '9';
        else return '\u0000';

    }






    private void getCalibrationData(String[] calibrationData) throws IOException{

        BufferedReader bf = new BufferedReader(new FileReader("inputs/day1.txt"));
        String line = bf.readLine();
        int i = 0;

        while (line != null) {
            calibrationData[i++] = line;
            line = bf.readLine();
        }

        bf.close();
    }

}
