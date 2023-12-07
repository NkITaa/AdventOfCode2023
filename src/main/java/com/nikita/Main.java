package com.nikita;
import com.nikita.day4.DayFour;
import com.nikita.day5.DayFive;
import com.nikita.day6.DaySix;
import com.nikita.day7.DaySeven;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        DaySeven daySeven = new DaySeven();

        try {
            System.out.println(daySeven.getDeckValue());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}