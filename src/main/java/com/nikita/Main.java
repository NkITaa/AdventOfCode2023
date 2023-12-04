package com.nikita;

import com.nikita.day2.DayTwo;
import com.nikita.day3.DayThree;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        DayThree dayThree = new DayThree();

        try {
            System.out.print(dayThree.adjacentStar());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}