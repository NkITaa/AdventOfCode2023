package com.nikita;

import com.nikita.day20.DayTwenty;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        DayTwenty dayTwenty = new DayTwenty();

        try {
            System.out.println(dayTwenty.minRx());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}