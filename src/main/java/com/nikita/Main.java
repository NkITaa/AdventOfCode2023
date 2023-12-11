package com.nikita;

import com.nikita.day10.DayTen;
import com.nikita.day11.DayEleven;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        DayEleven dayEleven = new DayEleven();

        try {
            System.out.println(dayEleven.getDistances());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}