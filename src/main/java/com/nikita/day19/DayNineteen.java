package com.nikita.day19;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class DayNineteen {

    private void getData(HashMap<String, List<Condition>> workflows, List<HashMap<Character, Integer>> valuess) throws IOException {

        BufferedReader bf = new BufferedReader(new FileReader("inputs/day19.txt"));
        String line = bf.readLine();

        boolean isWorkflow = true;

        while (line != null) {

            if (line.trim().isEmpty()) {
                isWorkflow = false;
                line = bf.readLine();
                continue;
            }


            if (isWorkflow) {
                String[] allWorkflow = line.split("\\{");
                String key = allWorkflow[0];
                String[] conditions = allWorkflow[1].split("[,}]");
                List<Condition> toAdd = new ArrayList<>();

                for (String condition : conditions) {
                    if (!condition.contains(">") && !condition.contains("<"))
                        toAdd.add(new Condition(null, null, null, condition));
                    else {

                        String[] temp = condition.split(":");
                        toAdd.add(new Condition(condition.charAt(0), condition.charAt(1), Integer.parseInt(temp[0].substring(2)), temp[1]));
                    }
                }
                workflows.put(key, toAdd);

            } else {
                String relevant = line.substring(1, line.length() - 1);
                String[] values = relevant.split(",");
                HashMap<Character, Integer> temp = new HashMap<>();

                for (String value : values) {

                    int toParse = Integer.parseInt(value.substring(2).trim());

                    temp.put(value.charAt(0), toParse);
                }

                valuess.add(temp);
            }


            line = bf.readLine();

        }

        bf.close();
    }


    public int printData() throws IOException {

        int sum = 0;
        HashMap<String, List<Condition>> workflows = new HashMap<>();
        List<HashMap<Character, Integer>> values = new ArrayList<>();

        getData(workflows, values);

        for (HashMap<Character, Integer> value : values) {

            String next = "in";

            while (!next.equals("A") && !next.equals("R")) {

                for (Condition condition : workflows.get(next)) {

                    if (condition.operation == null) {
                        next = condition.next;
                        break;
                    } else if (condition.operation == '<') {
                        if (value.get(condition.character) < condition.value) {
                            next = condition.next;
                            break;
                        }
                    } else if (condition.operation == '>')
                        if (value.get(condition.character) > condition.value) {
                            next = condition.next;
                            break;
                        }
                }

            }

            if (next.equals("A"))
                for (Integer worth : value.values())
                    sum += worth;

        }


        return sum;
    }


    public double partTwo() throws IOException {

        double sum = 0;
        HashMap<String, List<Condition>> workflows = new HashMap<>();
        List<HashMap<Character, Integer>> values = new ArrayList<>();
        getData(workflows, values);

        Stack<PossibleInstance> stack = new Stack<>();
        stack.add(new PossibleInstance("in"));


        while (!stack.isEmpty()) {

            PossibleInstance carry = stack.pop();

            List<Condition> conditions = workflows.get(carry.next);

            for (Condition condition : conditions) {

                if (condition.operation == null) {
                    if (condition.next.equals("A"))
                        sum += getPossibilities(carry);
                    else if (!condition.next.equals("R")) {
                        carry.next = condition.next;
                        stack.add(carry);
                    }
                } else if (condition.operation == '<') {

                    int top = condition.value - 1;
                    int bottom = condition.value;

                    if (carry.boundaries.get(condition.character).get(0) <= top) {

                        PossibleInstance newI = new PossibleInstance();
                        newI.next = condition.next;
                        for (Character key : carry.boundaries.keySet())
                            newI.boundaries.put(key, new ArrayList<>(carry.boundaries.get(key)));

                        if (carry.boundaries.get(condition.character).get(1) > top)
                            newI.boundaries.get(condition.character).set(1, top);

                        if (newI.next.equals("A"))
                            sum += getPossibilities(newI);
                        else if (!condition.next.equals("R"))
                            stack.add(newI);
                    }

                    if (carry.boundaries.get(condition.character).get(1) >= bottom) {
                        if (carry.boundaries.get(condition.character).get(0) < bottom)
                            carry.boundaries.get(condition.character).set(0, bottom);
                    } else break;

                } else if (condition.operation == '>') {

                    int bottom = condition.value + 1;
                    int top = condition.value;

                    if (carry.boundaries.get(condition.character).get(1) >= bottom) {

                        PossibleInstance newI = new PossibleInstance();
                        newI.next = condition.next;
                        for (Character key : carry.boundaries.keySet())
                            newI.boundaries.put(key, new ArrayList<>(carry.boundaries.get(key)));

                        if (carry.boundaries.get(condition.character).get(0) < bottom)
                            newI.boundaries.get(condition.character).set(0, bottom);

                        if (newI.next.equals("A"))
                            sum += getPossibilities(newI);
                        else if (!condition.next.equals("R"))
                            stack.add(newI);
                    }

                    if (carry.boundaries.get(condition.character).get(0) <= top) {
                        if (carry.boundaries.get(condition.character).get(1) > top)
                            carry.boundaries.get(condition.character).set(1, top);
                    } else break;
                }

            }
        }


        return sum;
    }

    private double getPossibilities(PossibleInstance possibleInstance) {

        double possibilities = 1;

        for (List<Integer> boundaries : possibleInstance.boundaries.values())
            possibilities *= (boundaries.get(1) - boundaries.get(0) + 1);

        return possibilities;
    }

    static private class PossibleInstance {


        HashMap<Character, List<Integer>> boundaries;
        String next;


        PossibleInstance(String next) {
            this.boundaries = new HashMap<>();
            boundaries.put('x', new ArrayList<>());
            boundaries.get('x').add(1);
            boundaries.get('x').add(4000);
            boundaries.put('m', new ArrayList<>());
            boundaries.get('m').add(1);
            boundaries.get('m').add(4000);
            boundaries.put('a', new ArrayList<>());
            boundaries.get('a').add(1);
            boundaries.get('a').add(4000);
            boundaries.put('s', new ArrayList<>());
            boundaries.get('s').add(1);
            boundaries.get('s').add(4000);
            this.next = next;
        }

        PossibleInstance() {
            this.boundaries = new HashMap<>();
            boundaries.put('x', new ArrayList<>());
            boundaries.get('x').add(1);
            boundaries.get('x').add(4000);
            boundaries.put('m', new ArrayList<>());
            boundaries.get('m').add(1);
            boundaries.get('m').add(4000);
            boundaries.put('a', new ArrayList<>());
            boundaries.get('a').add(1);
            boundaries.get('a').add(4000);
            boundaries.put('s', new ArrayList<>());
            boundaries.get('s').add(1);
            boundaries.get('s').add(4000);
            this.next = "";
        }
    }


    static private class Condition {

        Character character;
        Character operation;
        Integer value;
        String next;

        Condition(Character character, Character operation, Integer value, String next) {
            this.character = character;
            this.operation = operation;
            this.value = value;
            this.next = next;
        }
    }
}
