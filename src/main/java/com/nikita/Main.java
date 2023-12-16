package com.nikita;

import com.nikita.day16.DaySixteen;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        DaySixteen daySixteen = new DaySixteen(110, 110);

        try {
            System.out.println(daySixteen.maxConfig());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}