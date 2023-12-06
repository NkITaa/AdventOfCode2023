package com.nikita;
import com.nikita.day4.DayFour;
import com.nikita.day5.DayFive;
import com.nikita.day6.DaySix;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        DaySix daySix = new DaySix();

        try {
            System.out.println(daySix.howManyDifferentSolutions());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}