package com.nikita.day5;

import java.util.ArrayList;

public class SecondThread implements Runnable {


    private double start;
    private double length;
    ArrayList<double[]> seedToSoil;
    ArrayList<double[]> soilToFertilizer;
    ArrayList<double[]> fertilizerToWater;
    ArrayList<double[]> waterToLight;
    ArrayList<double[]> lightToTemperature;
    ArrayList<double[]> temperatureToHumidity;
    ArrayList<double[]> humidityToLocation;

    public SecondThread(
            double start,
            double length,
            ArrayList<double[]> seedToSoil,
            ArrayList<double[]> soilToFertilizer,
            ArrayList<double[]> fertilizerToWater,
            ArrayList<double[]> waterToLight,
            ArrayList<double[]> lightToTemperature,
            ArrayList<double[]> temperatureToHumidity,
            ArrayList<double[]> humidityToLocation
    ) {
       this.start = start;
       this.length = length;
       this.seedToSoil = seedToSoil;
       this.soilToFertilizer = soilToFertilizer;
       this.fertilizerToWater = fertilizerToWater;
       this.waterToLight = waterToLight;
       this.lightToTemperature = lightToTemperature;
       this.temperatureToHumidity = temperatureToHumidity;
       this.humidityToLocation = humidityToLocation;
    }

    @Override
    public void run() {

        double smallestNum = Double.MAX_VALUE;

        for (double value = start; value < start + length; value++) {
            double currentVal = value;

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
            smallestNum = Math.min(currentVal, smallestNum);
        }
        System.out.println(smallestNum);
    }
}
