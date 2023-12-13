package com.nikita;

import com.nikita.day10.DayTen;
import com.nikita.day11.DayEleven;
import com.nikita.day12.DayTwelve;
import com.nikita.day13.DayThirteen;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        DayThirteen dayThirteen = new DayThirteen();

        try {
            System.out.println(dayThirteen.mirroredOperations());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}