package com.nikita;

import com.nikita.day17.DaySeventeen;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        DaySeventeen daySeventeen = new DaySeventeen(141, 141);

        try {
            System.out.println(daySeventeen.getShortestPath());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}