package com.nikita.day9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DayNine {

    private void getData(ArrayList<ArrayList<Integer>> ranges) throws IOException {

        BufferedReader bf = new BufferedReader(new FileReader("inputs/day9.txt"));

        String line = bf.readLine();

        while (line != null) {

            String[] parts = line.split(" ");
            ArrayList<Integer> range = new ArrayList<>();

            for (String part : parts) range.add(Integer.parseInt(part.trim()));

            ranges.add(range);
            line = bf.readLine();
        }

        bf.close();
    }


    // part one
    public int completeRangeBack() throws IOException {

        ArrayList<ArrayList<Integer>> ranges = new ArrayList<>();
        int sum = 0;
        getData(ranges);

        for (ArrayList<Integer> range : ranges) {

            boolean nullLine = false;
            ArrayList<Integer> lastInts = new ArrayList<>();
            lastInts.add(range.get(range.size()-1));

            while (!nullLine) {

                int quantityZero = 0;
                int n = range.size() - 1;

                for (int i = 0; i < n; i++) {

                    int posterior = range.get(i+1);
                    int current = range.get(i);

                    range.set(i, posterior - current);
                    if (posterior - current == 0) ++quantityZero;
                }

                range.remove(n);
                lastInts.add(range.get(n-1));

                if (quantityZero == n) nullLine = true;
            }

            for (int lastInt : lastInts) sum += lastInt;

        }

        return sum;
    }

    // part two
    public int completeRangeFront() throws IOException {

        ArrayList<ArrayList<Integer>> ranges = new ArrayList<>();
        int sum = 0;
        getData(ranges);

        for (ArrayList<Integer> range : ranges) {

            boolean nullLine = false;
            ArrayList<Integer> firstInts = new ArrayList<>();
            firstInts.add(range.get(0));

            while (!nullLine) {

                int quantityZero = 0;
                int n = range.size() - 1;

                for (int i = 0; i < n; i++) {

                    int posterior = range.get(i+1);
                    int current = range.get(i);

                    range.set(i, posterior - current);
                    if (i == 0) firstInts.add(range.get(i));
                    if (posterior - current == 0) ++quantityZero;
                }

                range.remove(n);

                if (quantityZero == n) nullLine = true;
            }

            int pred = firstInts.get(firstInts.size() - 1);

            for (int i = firstInts.size() - 2; i >= 0; i--) pred = firstInts.get(i) - pred;
            sum += pred;
        }

        return sum;
    }

}
