package com.nikita.day8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DayEight {

    // part one
    private char[] getData(HashMap<String, String[]> nodes) throws IOException {

        BufferedReader bf = new BufferedReader(new FileReader("inputs/day8.txt"));

        String line = bf.readLine();
        char[] instructions = line.toCharArray();
        line = bf.readLine();

        while (line != null) {
            if (line.length() == 16) {
                String node = line.substring(0, 3);
                String[] nextNodes = new String[]{line.substring(7, 10), line.substring(12, 15)};
                nodes.put(node, nextNodes);
            }

            line = bf.readLine();
        }

        bf.close();
        return instructions;
    }

    // part two
    private char[] getDataPart2(HashMap<String, String[]> nodes, ArrayList<String> starters) throws IOException {

        BufferedReader bf = new BufferedReader(new FileReader("inputs/day8.txt"));

        String line = bf.readLine();
        char[] instructions = line.toCharArray();
        line = bf.readLine();

        while (line != null) {
            if (line.length() == 16) {
                String node = line.substring(0, 3);
                String[] nextNodes = new String[]{line.substring(7, 10), line.substring(12, 15)};
                nodes.put(node, nextNodes);

                if (node.endsWith("A")) starters.add(node);
            }

            line = bf.readLine();
        }

        bf.close();
        return instructions;
    }

    // part one
    public int getSteps() throws IOException {

        HashMap<String, String[]> nodes = new HashMap<>();
        char[] instructions = getData(nodes);

        int steps = 0;
        String current = "AAA";

        while (!Objects.equals(current, "ZZZ")) {

            char instruction = instructions[steps % instructions.length];
            current = nodes.get(current)[instruction == 'L' ? 0 : 1];
            ++steps;
        }


        return steps;
    }


    private Double getLeastZ(HashMap<String, String[]> nodes, char[] instructions, String current) {

        double steps = 0;

        while (!current.endsWith("Z")) {

            char instruction = instructions[(int) (steps % instructions.length)];
            current = nodes.get(current)[instruction == 'L' ? 0 : 1];
            ++steps;
        }

        return steps;
    }

    static double lcm(ArrayList<Double> specificDistances)
    {
        return specificDistances.stream().reduce(
                1.0, (x, y) -> (x * y) / gcd(x, y));
    }

    static double gcd(double a, double b)
    {
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }


    // part two
    public double getAllZ() throws IOException {

        HashMap<String, String[]> nodes = new HashMap<>();
        ArrayList<String> starters = new ArrayList<>();
        char[] instructions = getDataPart2(nodes, starters);
        ArrayList<Double> specificDistances = new ArrayList<>();

        for (String starter : starters) specificDistances.add(getLeastZ(nodes, instructions, starter));

        return lcm(specificDistances);
    }
}
