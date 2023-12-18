package com.nikita;

import com.nikita.day18.DayEighteen;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        DayEighteen dayEighteen = new DayEighteen();

        try {
            System.out.println(dayEighteen.getCapacityTwo());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}