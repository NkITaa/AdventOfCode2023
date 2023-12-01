package com.nikita;

import com.nikita.day1.DayOne;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        DayOne dayOne = new DayOne();

        try {
           System.out.println(dayOne.getCalibrationPartTwo());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}