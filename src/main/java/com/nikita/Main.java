package com.nikita;
import com.nikita.day4.DayFour;
import com.nikita.day5.DayFive;
import com.nikita.day6.DaySix;
import com.nikita.day7.DaySeven;
import com.nikita.day8.DayEight;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        DayEight dayEight = new DayEight();

        try {
            System.out.println(dayEight.getAllZ());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}