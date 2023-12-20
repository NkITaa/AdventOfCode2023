package com.nikita.day20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DayTwenty {

    static double lcm(List<Double> specificDistances) {
        return specificDistances.stream().reduce(
                1.0, (x, y) -> (x * y) / gcd(x, y));
    }

    static double gcd(double a, double b) {
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }

    private HashMap<String, Module> getData(HashMap<String, Integer> quantites) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader("inputs/day20.txt"));

        String line = bf.readLine();
        HashMap<String, Module> modules = new HashMap<>();

        while (line != null) {

            String[] parts = line.split("->");

            String type = "";
            String key = parts[0].trim();
            if (key.equals("broadcaster"))
                type = "broadcaster";
            else {
                type = key.charAt(0) + "";
                key = key.substring(1);
            }

            String[] childrenUnformated = parts[1].split(",");
            List<String> children = new ArrayList<>();
            for (String child : childrenUnformated) {
                children.add(child.trim());
                quantites.put(child.trim(), quantites.getOrDefault(child.trim(), 0) + 1);
            }

            Module temp = new Module(type, key, children);

            if (Objects.equals(temp.type, "%"))
                temp.specificData.put(key, true);
            modules.put(key, temp);
            line = bf.readLine();

        }

        bf.close();
        return modules;
    }

    public double getPulses() throws IOException {

        double lowPulses = 0;
        double highPulses = 0;
        HashMap<String, Integer> quantites = new HashMap<>();
        HashMap<String, Module> modules = getData(quantites);
        Queue<Pulse> pulseQueue = new LinkedList<>();

        for (int i = 0; i < 1000; i++) {

            ++lowPulses;
            for (String child : modules.get("broadcaster").children)
                pulseQueue.add(new Pulse(true, child, "broadcaster"));


            while (!pulseQueue.isEmpty()) {

                Pulse currentPulse = pulseQueue.poll();
                Module currentModule = modules.get(currentPulse.receiver);

                if (currentPulse.low)
                    ++lowPulses;
                else
                    ++highPulses;

                if (currentModule == null)
                    continue;
                if (Objects.equals(currentModule.type, "%")) {
                    if (currentPulse.low) {
                        boolean nextVal = !currentModule.specificData.get(currentPulse.receiver);
                        currentModule.specificData.put(currentPulse.receiver, nextVal);
                        for (String child : currentModule.children)
                            pulseQueue.add(new Pulse(nextVal, child, currentModule.name));
                    }
                } else if (Objects.equals(currentModule.type, "&")) {

                    boolean prevValue = currentModule.specificData.getOrDefault(currentPulse.sender, true);

                    if (prevValue != currentPulse.low) {
                        if (currentPulse.low)
                            currentModule.deltaHighPulse--;
                        else
                            currentModule.deltaHighPulse++;
                    }

                    currentModule.specificData.put(currentPulse.sender, currentPulse.low);

                    if (Objects.equals(currentModule.deltaHighPulse, quantites.get(currentModule.name)))
                        for (String child : currentModule.children)
                            pulseQueue.add(new Pulse(true, child, currentModule.name));
                    else
                        for (String child : currentModule.children) {
                            pulseQueue.add(new Pulse(false, child, currentModule.name));
                        }

                }
            }
        }

        return lowPulses * highPulses;
    }

    public double minRx() throws IOException {

        HashMap<String, Integer> quantites1 = new HashMap<>();
        HashMap<String, Module> modules1 = getData(quantites1);
        Queue<Pulse> pulseQueue = new LinkedList<>();

        String key = "";
        List<String> parents = new ArrayList<>();
        List<Double> firstLowPulsesParents = new ArrayList<>();

        for (Module module : modules1.values())
            for (String child : module.children)
                if (Objects.equals(child, "rx")) {
                    key = module.name;
                    break;
                }

        for (Module module : modules1.values())
            for (String child : module.children)
                if (Objects.equals(child, key))
                    parents.add(module.name);

        for (String parent : parents) {

            boolean found = false;
            double i = 0;
            pulseQueue.clear();
            HashMap<String, Integer> quantites = new HashMap<>();
            HashMap<String, Module> modules = getData(quantites);

            while (!found) {

                for (String child : modules.get("broadcaster").children)
                    pulseQueue.add(new Pulse(true, child, "broadcaster"));


                while (!pulseQueue.isEmpty()) {

                    Pulse currentPulse = pulseQueue.poll();
                    Module currentModule = modules.get(currentPulse.receiver);

                    if (currentModule == null)
                        continue;
                    if (Objects.equals(currentModule.type, "%")) {
                        if (currentPulse.low) {
                            boolean nextVal = !currentModule.specificData.get(currentPulse.receiver);
                            currentModule.specificData.put(currentPulse.receiver, nextVal);
                            for (String child : currentModule.children)
                                pulseQueue.add(new Pulse(nextVal, child, currentModule.name));
                        }
                    } else if (Objects.equals(currentModule.type, "&")) {

                        boolean prevValue = currentModule.specificData.getOrDefault(currentPulse.sender, true);

                        if (prevValue != currentPulse.low) {
                            if (currentPulse.low)
                                currentModule.deltaHighPulse--;
                            else
                                currentModule.deltaHighPulse++;
                        }

                        currentModule.specificData.put(currentPulse.sender, currentPulse.low);

                        if (Objects.equals(currentModule.deltaHighPulse, quantites.get(currentModule.name)))
                            for (String child : currentModule.children)
                                pulseQueue.add(new Pulse(true, child, currentModule.name));
                        else
                            for (String child : currentModule.children) {
                                pulseQueue.add(new Pulse(false, child, currentModule.name));
                                if (Objects.equals(currentModule.name, parent))
                                    found = true;
                            }

                    }
                }
                i++;
            }

            firstLowPulsesParents.add(i);
        }

        return lcm(firstLowPulsesParents);


    }

    static private class Module {

        String type;
        String name;
        List<String> children;
        HashMap<String, Boolean> specificData;
        Integer deltaHighPulse;

        Module(String type,
               String name,
               List<String> children) {
            this.type = type;
            this.name = name;
            this.children = children;
            this.specificData = new HashMap<>();
            this.deltaHighPulse = 0;
        }
    }

    static private class Pulse {

        boolean low;
        String receiver;
        String sender;

        Pulse(boolean low, String receiver, String sender) {
            this.low = low;
            this.receiver = receiver;
            this.sender = sender;
        }
    }
}
