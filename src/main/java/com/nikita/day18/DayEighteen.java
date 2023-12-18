package com.nikita.day18;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class DayEighteen {


    private List<Instruction> getData() throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader("inputs/day18.txt"));

        String line = bf.readLine();
        List<Instruction> instructions = new ArrayList<>();

        while (line != null) {

            String[] parts = line.split(" ");
            instructions.add(new Instruction(parts[0].charAt(0), Integer.parseInt(parts[1].trim()), parts[2]));
            line = bf.readLine();

        }

        bf.close();
        return instructions;
    }


    private void goDown(Instruction instruction, int x, int y, List<List<String>> lagoon) {
        int top = y + instruction.distance;
        while (y < top) {
            if (top > lagoon.size() - 1)
                lagoon.add(new ArrayList<>());
            int toFillStart = lagoon.get(y + 1).size();
            for (int i = toFillStart; i <= x; i++)
                lagoon.get(y + 1).add(i, ".");
            lagoon.get(++y).set(x, "#");
        }
    }

    private void goRight(Instruction instruction, int x, int y, List<List<String>> lagoon) {
        int top = x + instruction.distance;

        while (x < top) {
            if (++x == lagoon.get(y).size())
                lagoon.get(y).add(x, ".");
            lagoon.get(y).set(x, "#");
        }
    }

    private void goLeft(Instruction instruction, int x, int y, List<List<String>> lagoon) {
        int bottom = x - instruction.distance;

        while (x > bottom) {
            --x;
            if (x < 0) {
                lagoon.get(y).add(0, "#");
                int absoluteBottom = 0;
                while (absoluteBottom < lagoon.size()) {
                    if (absoluteBottom == y) {
                        absoluteBottom++;
                    } else {
                        lagoon.get(absoluteBottom++).add(0, ".");
                    }
                }
            } else
                lagoon.get(y).set(x, "#");
        }
    }


    private void goUp(Instruction instruction, int x, int y, List<List<String>> lagoon) {
        int bottom = y - instruction.distance;
        while (y > bottom) {
            --y;
            if (y < 0)
                lagoon.add(0, new ArrayList<>());
            for (int i = lagoon.get(Math.max(y, 0)).size(); i <= x; i++)
                lagoon.get(Math.max(y, 0)).add(i, ".");
            lagoon.get(Math.max(y, 0)).set(x, "#");
        }
    }

    private void fillInBetween(List<List<String>> lagoon) {

        int x = 0;
        int y = 0;

        while (!lagoon.get(0).get(x).equals("#"))
            ++x;
        while (lagoon.get(y).get(x).equals("#"))
            ++y;

        setHashtag(lagoon, new Coordinate(x, y));
    }

    private void setHashtag(List<List<String>> lagoon, Coordinate coordinate) {

        Stack<Coordinate> toDiscover = new Stack<>();
        toDiscover.push(coordinate);


        System.out.println(coordinate.x);
        System.out.println(coordinate.y);

        while (!toDiscover.isEmpty()) {

            Coordinate current = toDiscover.pop();
            int x = current.x;
            int y = current.y;

            if (!Objects.equals(lagoon.get(y).get(x + 1), "#")) {
                lagoon.get(y).set(x + 1, "#");
                toDiscover.add(new Coordinate(x + 1, y));
            }
            if (!Objects.equals(lagoon.get(y).get(x - 1), "#")) {
                lagoon.get(y).set(x - 1, "#");
                toDiscover.add(new Coordinate(x - 1, y));
            }
            if (!Objects.equals(lagoon.get(y - 1).get(x), "#")) {
                lagoon.get(y - 1).set(x, "#");
                toDiscover.add(new Coordinate(x, y - 1));
            }
            if (!Objects.equals(lagoon.get(y + 1).get(x), "#")) {
                lagoon.get(y + 1).set(x, "#");
                toDiscover.add(new Coordinate(x, y + 1));
            }
        }

    }

    public int getCapacity() throws IOException {

        int capacity = 0;
        List<Instruction> instructions = getData();

        int x = 0;
        int y = 0;
        List<List<String>> lagoon = new ArrayList<>();
        lagoon.add(new ArrayList<>());
        lagoon.get(y).add(x, "b");


        for (Instruction instruction : instructions) {
            if (instruction.direction == 'R') {
                goRight(instruction, x, y, lagoon);
                x = x + instruction.distance;
            } else if (instruction.direction == 'L') {
                goLeft(instruction, x, y, lagoon);
                x = x - instruction.distance;
                if (x < 0) x = 0;
            } else if (instruction.direction == 'U') {
                goUp(instruction, x, y, lagoon);
                y = y - instruction.distance;
                if (y < 0) y = 0;
            } else if (instruction.direction == 'D') {
                goDown(instruction, x, y, lagoon);
                y = y + instruction.distance;
            }
        }

        fillInBetween(lagoon);

        for (int i = 0; i < lagoon.size(); i++)
            for (int j = 0; j < lagoon.get(i).size(); j++)
                if (lagoon.get(i).get(j).equals("#"))
                    ++capacity;


        for (int i = 0; i < lagoon.size(); i++) {
            for (int j = 0; j < lagoon.get(i).size(); j++)
                System.out.print(lagoon.get(i).get(j));
            System.out.println();
        }


        return capacity;
    }


    // part two

    public double getCapacityTwo() throws IOException {

        double capacity = 0;
        double b = 0;
        double x = 0;
        double y = 0;
        List<InstructionTwo> instructions = getHex();
        List<CoordinateTwo> coordinates = new ArrayList<>();
        coordinates.add(new CoordinateTwo(0, 0));


        // solved by
        // i = A - b/2 + 1 -- combining picks theorem and shoelace formula
        // - picks theorem being A = b/2 - 1 + i  --- also die formel umgestellt und für a das aus shoelace
        // shoelace formula being (xi*yi-1) - (xi*yi+1) - Betrag davon und für alle ecken
        for (InstructionTwo instruction : instructions) {
            b += instruction.distance;
            if (instruction.direction == 'R') {
                x += instruction.distance;
                coordinates.add(new CoordinateTwo(x, y));
            } else if (instruction.direction == 'L') {
                x -= instruction.distance;
                coordinates.add(new CoordinateTwo(x, y));
            } else if (instruction.direction == 'U') {
                y -= instruction.distance;
                coordinates.add(new CoordinateTwo(x, y));
            } else if (instruction.direction == 'D') {
                y += instruction.distance;
                coordinates.add(new CoordinateTwo(x, y));
            }
        }

        double A = 0;

        for (int i = 1; i < coordinates.size(); i++) {
            if (i == coordinates.size() - 1) {
                A += coordinates.get(0).x * (coordinates.get(1).y);
                A += coordinates.get(i).x * (-coordinates.get(i - 1).y);
            } else {
                A += coordinates.get(i).x * (coordinates.get(i + 1).y - coordinates.get(i - 1).y);
            }
        }

        A = 0.5 * Math.abs(A);
        double i = A - (b / 2) + 1;
        return i + b;
    }

    private List<InstructionTwo> getHex() throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader("inputs/day18.txt"));

        String line = bf.readLine();
        List<InstructionTwo> instructions = new ArrayList<>();

        while (line != null) {

            String[] parts = line.split(" ");

            String relevant = parts[2];
            instructions.add(new InstructionTwo(getDirection(relevant.charAt(relevant.length() - 2)),
                    Integer.parseInt(relevant.substring(2, relevant.length() - 2), 16)

            ));
            line = bf.readLine();

        }

        bf.close();
        return instructions;
    }

    private char getDirection(char direction) {

        if (direction == '0')
            return 'R';
        if (direction == '1')
            return 'D';
        if (direction == '2')
            return 'L';
        else
            return 'U';
    }


    static private class InstructionTwo {

        char direction;
        double distance;

        InstructionTwo(char direction, double distance) {
            this.direction = direction;
            this.distance = distance;
        }
    }


    static private class Instruction {

        Character direction;
        Integer distance;
        String color;

        Instruction(Character direction, Integer distance, String color) {
            this.direction = direction;
            this.distance = distance;
            this.color = color;
        }
    }

    static private class CoordinateTwo {
        double x;
        double y;

        CoordinateTwo(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    static private class Coordinate {
        int x;
        int y;

        Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
