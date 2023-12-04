package com.nikita;
import com.nikita.day4.DayFour;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        DayFour dayFour = new DayFour();

        try {
            System.out.print(dayFour.getPointsNewRules());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}