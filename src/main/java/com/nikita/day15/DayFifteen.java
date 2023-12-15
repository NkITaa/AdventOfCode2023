package com.nikita.day15;

import com.nikita.day12.DayTwelve;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DayFifteen {

    private String[] getData() throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader("inputs/day15.txt"));

        String line = bf.readLine();
        bf.close();
        return line.split(",");
    }

    public int hashResult() throws IOException {

        int result = 0;
        String[] inputs = getData();

        for (String input : inputs)
            result += stringToHash(input);


        return result;
    }

    private int stringToHash(String input) {

        int currentValue = 0;

        for (char charInInput : input.toCharArray()) {
            currentValue = currentValue + charInInput;
            currentValue *= 17;
            currentValue = currentValue % 256;
        }

        return currentValue;
    }


    // part two

    static private class Lense {

        String label;
        char operation;
        Integer lenseWeight;

        Lense(String label, char operation, Integer lenseWeight) {
            this.label = label;
            this.operation = operation;
            this.lenseWeight = lenseWeight;
        }
    }

        private ArrayList<Lense> getDataPartTwo() throws IOException {
            BufferedReader bf = new BufferedReader(new FileReader("inputs/day15.txt"));

            ArrayList<Lense> lenses = new ArrayList<>();
            String line = bf.readLine();

            for (String entree : line.split(",")) {

                if (entree.contains("-")) {
                    String[] entreeSplit = entree.split("-");
                    lenses.add(new Lense(entreeSplit[0], '-', null));
                }

                else {
                    String[] entreeSplit = entree.split("=");
                    lenses.add(new Lense(entreeSplit[0], '=', Integer.parseInt(entreeSplit[1].trim())));}

            }

            bf.close();
            return lenses;
        }


    public int getLenses() throws IOException {

        int sum = 0;

        ArrayList<Lense> lenses = getDataPartTwo();
        Map<Integer, Map<String, Integer>> lenseBoxes = new LinkedHashMap<>();


        for (Lense lense : lenses) {

            int boxKey = stringToHash(lense.label);

            if (lenseBoxes.containsKey(boxKey)) {

                Map<String, Integer> box = lenseBoxes.get(boxKey);

                if (lense.operation == '-')
                    box.remove(lense.label);
                else box.put(lense.label, lense.lenseWeight);

            } else {

                lenseBoxes.put(boxKey, new LinkedHashMap<>());

                Map<String, Integer> box = lenseBoxes.get(boxKey);
                box.put(lense.label, lense.lenseWeight);

            }
        }

        for (Integer boxNum : lenseBoxes.keySet()) {

            Map<String, Integer> currentBox = lenseBoxes.get(boxNum);
            int slot = 0;

            for (Integer lenseWeight : currentBox.values()) {
                ++slot;
                sum += (boxNum + 1) * slot * lenseWeight;
            }

        }


        return sum;
    }

}
