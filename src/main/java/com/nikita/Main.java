package com.nikita;

import com.nikita.day1.DayOne;
import com.nikita.day1.DayTwo;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        DayTwo dayTwo = new DayTwo();

        try {
            System.out.print(dayTwo.getPower());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}