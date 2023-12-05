package com.nikita.day5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DayFive {

    private void getData(ArrayList<Double> seeds, ArrayList<double[]> seedToSoil,  ArrayList<double[]> soilToFertilizer,  ArrayList<double[]> fertilizerToWater,  ArrayList<double[]> waterToLight,  ArrayList<double[]> lightToTemperature,  ArrayList<double[]> temperatureToHumidity,  ArrayList<double[]> humidityToLocation) throws IOException {

        BufferedReader bf = new BufferedReader(new FileReader("inputs/day5.txt"));

        String line = bf.readLine();
        String mode = "seeds";

        while (line != null) {

            // switch mode when char
            if (!line.isEmpty() && line.charAt(0) - 'a' >= 0 && line.charAt(0) - 'z' <= 0) {
                if (line.contains("seed-to-soil map")) mode = "toSoil";
                else if (line.contains("soil-to-fertilizer map")) mode = "toFertilizer";
                else if (line.contains("fertilizer-to-water map")) mode = "toWater";
                else if (line.contains("water-to-light map")) mode = "toLight";
                else if (line.contains("light-to-temperature map")) mode = "toTemperature";
                else if (line.contains("temperature-to-humidity map")) mode = "toHumidity";
                else if (line.contains("humidity-to-location map")) mode = "toLocation";
            }

            // execute mode routine
            if ((!line.isEmpty() && line.charAt(0) - '0' >= 0 && line.charAt(0) - '9' <= 0) || mode.equals("seeds")) {
                switch (mode) {
                    case "seeds" -> {
                        String[] parts = line.split("[:|\\\\ ]");
                        for (int i = 1; i < parts.length; i++) {
                            String current = parts[i].trim();
                            if (!current.isEmpty()) seeds.add(Double.parseDouble(current));
                        }
                        mode = "";
                    }
                    case "toSoil" -> seedToSoil.add(getRelevant(line.split(" ")));
                    case "toFertilizer" -> soilToFertilizer.add(getRelevant(line.split(" ")));
                    case "toWater" -> fertilizerToWater.add(getRelevant(line.split(" ")));
                    case "toLight" -> waterToLight.add(getRelevant(line.split(" ")));
                    case "toTemperature" -> lightToTemperature.add(getRelevant(line.split(" ")));
                    case "toHumidity" -> temperatureToHumidity.add(getRelevant(line.split(" ")));
                    case "toLocation" -> humidityToLocation.add(getRelevant(line.split(" ")));
                }
            }

            line = bf.readLine();
        }

        bf.close();
    }

    private double[] getRelevant(String[] parts) {
        double minBorder = Double.parseDouble(parts[1]);
        double maxBorder = minBorder + Double.parseDouble(parts[2]) - 1;
        double recalculate = minBorder - Double.parseDouble(parts[0]);
        return new double[]{minBorder, maxBorder, recalculate};
    }

    public double getLocation() throws IOException {
        ArrayList<Double> seeds = new ArrayList<>();
        ArrayList<double[]> seedToSoil = new ArrayList<>();
        ArrayList<double[]> soilToFertilizer = new ArrayList<>();
        ArrayList<double[]> fertilizerToWater = new ArrayList<>();
        ArrayList<double[]> waterToLight = new ArrayList<>();
        ArrayList<double[]> lightToTemperature = new ArrayList<>();
        ArrayList<double[]> temperatureToHumidity = new ArrayList<>();
        ArrayList<double[]> humidityToLocation = new ArrayList<>();
        double sum = Double.MAX_VALUE;
        getData(seeds, seedToSoil, soilToFertilizer, fertilizerToWater, waterToLight, lightToTemperature, temperatureToHumidity, humidityToLocation);

        for (Double seed : seeds) {
            double currentVal = seed;

            for (double[] toSoil : seedToSoil)
                if (currentVal >= toSoil[0] && currentVal <= toSoil[1]) {
                    currentVal -= toSoil[2];
                    break;
                }

            for (double[] toFertilizer : soilToFertilizer)
                if (currentVal >= toFertilizer[0] && currentVal <= toFertilizer[1]) {
                    currentVal -= toFertilizer[2];
                    break;
                }

            for (double[] toWater : fertilizerToWater)
                if (currentVal >= toWater[0] && currentVal <= toWater[1]) {
                    currentVal -= toWater[2];
                    break;
                }

            for (double[] toLight : waterToLight)
                if (currentVal >= toLight[0] && currentVal <= toLight[1]) {
                    currentVal -= toLight[2];
                    break;
                }

            for (double[] toTemperature : lightToTemperature)
                if (currentVal >= toTemperature[0] && currentVal <= toTemperature[1]) {
                    currentVal -= toTemperature[2];
                    break;
                }

            for (double[] toHumidity : temperatureToHumidity)
                if (currentVal >= toHumidity[0] && currentVal <= toHumidity[1]) {
                    currentVal -= toHumidity[2];
                    break;
                }

            for (double[] toLocation : humidityToLocation)
                if (currentVal >= toLocation[0] && currentVal <= toLocation[1]) {
                    currentVal -= toLocation[2];
                    break;
                }

            sum = Math.min(currentVal, sum);
        }


        return sum;
    }


    public double getLocationTwo() throws IOException {
        ArrayList<Double> seeds = new ArrayList<>();
        ArrayList<double[]> seedToSoil = new ArrayList<>();
        ArrayList<double[]> soilToFertilizer = new ArrayList<>();
        ArrayList<double[]> fertilizerToWater = new ArrayList<>();
        ArrayList<double[]> waterToLight = new ArrayList<>();
        ArrayList<double[]> lightToTemperature = new ArrayList<>();
        ArrayList<double[]> temperatureToHumidity = new ArrayList<>();
        ArrayList<double[]> humidityToLocation = new ArrayList<>();
        double smallestNum = Double.MAX_VALUE;
        getData(seeds, seedToSoil, soilToFertilizer, fertilizerToWater, waterToLight, lightToTemperature, temperatureToHumidity, humidityToLocation);

        ExecutorService executorService = Executors.newFixedThreadPool(10);


        for (int i = 0; i + 1 < seeds.size(); i+= 2) {
            double start = seeds.get(i);
            double length = seeds.get(i+1);

            executorService.submit(new SecondThread(
                    start,
                    length,
                    seedToSoil,
                    soilToFertilizer,
                    fertilizerToWater,
                    waterToLight,
                    lightToTemperature,
                    temperatureToHumidity,
                    humidityToLocation
            ));
        }


        return smallestNum;
    }
}
