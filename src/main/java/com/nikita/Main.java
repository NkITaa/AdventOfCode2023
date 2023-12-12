package com.nikita;

import com.nikita.day10.DayTen;
import com.nikita.day11.DayEleven;
import com.nikita.day12.DayTwelve;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        DayTwelve dayTwelve = new DayTwelve();

        try {
            System.out.println(dayTwelve.getPossibilities());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}