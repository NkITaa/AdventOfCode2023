package com.nikita.day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DayTwo {

    private void getData(String[] quantities) throws IOException {

        BufferedReader bf = new BufferedReader(new FileReader("inputs/day2.txt"));

        String line = bf.readLine();
        int i = 0;

        while (line != null) {
            quantities[i++] = line;
            line = bf.readLine();
        }

        bf.close();
    }

    // Part One
    public int getFalty() throws IOException {

        String[] quantities = new String[100];
        int sol = 0;
        getData(quantities);

        for (int i = 0; i < quantities.length; i++) {

            String[] parts = quantities[i].split("[,|\\\\:|\\\\;]");
            boolean invalid = false;

            for (int j = 1; j < parts.length && !invalid; j++) {
                int currentNum = Integer.parseInt(parts[j].substring(0,3).trim());

                if (parts[j].contains("red") && currentNum > 12) invalid = true;
                else if (parts[j].contains("blue") && currentNum > 14)  invalid = true;
                else if (parts[j].contains("green") && currentNum > 13)  invalid = true;
            }

            if (!invalid) sol += i+1;
        }

        return sol;
    }

    // Part Two
    public int getPower() throws IOException {

        String[] quantities = new String[100];
        int sol = 0;
        getData(quantities);

        for (String quantity : quantities) {

            String[] parts = quantity.split("[,|\\\\:|\\\\;]");
            int red = 0;
            int green = 0;
            int blue = 0;

            for (int j = 1; j < parts.length; j++) {

                int currentNum = Integer.parseInt(parts[j].substring(0, 3).trim());

                if (parts[j].contains("red")) red = Math.max(red, currentNum);
                else if (parts[j].contains("blue")) blue = Math.max(blue, currentNum);
                else if (parts[j].contains("green")) green = Math.max(green, currentNum);
            }

            sol += (red * green * blue);
        }

        return sol;
    }
}
