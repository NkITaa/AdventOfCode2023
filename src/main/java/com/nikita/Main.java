package com.nikita;

import com.nikita.day19.DayNineteen;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        DayNineteen dayNineteen = new DayNineteen();

        try {
            System.out.println(dayNineteen.partTwo());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}