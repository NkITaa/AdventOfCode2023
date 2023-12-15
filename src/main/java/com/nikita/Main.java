package com.nikita;

import com.nikita.day10.DayTen;
import com.nikita.day11.DayEleven;
import com.nikita.day12.DayTwelve;
import com.nikita.day13.DayThirteen;
import com.nikita.day14.DayFourteen;
import com.nikita.day15.DayFifteen;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        DayFifteen dayFifteen = new DayFifteen();

        try {
            System.out.println(dayFifteen.getLenses());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}