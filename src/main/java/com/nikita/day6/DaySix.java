package com.nikita.day6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class DaySix {

    private void getData(ArrayList<Double> times, ArrayList<Double> records) throws IOException {

        // part 1
        // BufferedReader bf = new BufferedReader(new FileReader("inputs/day6.txt"));

        // part 2
        BufferedReader bf = new BufferedReader(new FileReader("inputs/day6input2.txt"));

        String line = bf.readLine();

        while (line != null) {

            String[] parts = line.split("[:|\\\\ ]");

            for (int i = 1; i < parts.length; i++) {

                String currentPart = parts[i].trim();
                if (!currentPart.isEmpty()) {
                    if (line.startsWith("Time")) times.add(Double.parseDouble(currentPart));
                    else records.add(Double.parseDouble(currentPart));
                }
            }

            line = bf.readLine();
        }

        bf.close();
    }

    public double calculateTime(double startTime, double totalTime) {
        return (totalTime - startTime) * startTime;
    }

    // part one
    public int productSums() throws IOException {

        int product = 1;
        ArrayList<Double> times = new ArrayList<>();
        ArrayList<Double> records = new ArrayList<>();
        getData(times, records);

        for (int i = 0; i < times.size(); i++) {

            double max = times.get(i);
            double record = records.get(i);

            double l = 0;
            double r = max;

            double currentTimeL = calculateTime(l, max);
            double currentTimeR = calculateTime(r, max);


            while (currentTimeL <= record || currentTimeR <= record) {
                if (currentTimeL <= record) currentTimeL = calculateTime(++l, max);
                if (currentTimeR <= record) currentTimeR = calculateTime(--r, max);
            }

            product *= (r - l + 1);
        }


        return product;
    }




    // part two
    public double howManyDifferentSolutions() throws IOException {

        ArrayList<Double> times = new ArrayList<>();
        ArrayList<Double> records = new ArrayList<>();
        double wins = 0;
        getData(times, records);


        for (int i = 0; i < times.size(); i++) {

            double max = times.get(i);
            double l = 0;
            double r = max;
            double record = records.get(i);

            double currentTimeL = calculateTime(l, max);
            double currentTimeR = calculateTime(r, max);


            while (currentTimeL <= record || currentTimeR <= record) {
                if (currentTimeL <= record) currentTimeL = calculateTime(++l, max);
                if (currentTimeR <= record) currentTimeR = calculateTime(--r, max);
            }

            wins = r - l + 1;
        }


        return wins;
    }

}
