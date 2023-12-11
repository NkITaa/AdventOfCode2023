package com.nikita.day11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DayEleven {

    static class Coordinate {
        int i;
        int j;

        public Coordinate(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

    private void getData(int[][] space, List<Integer> doubleI, List<Integer> doubleJ, Queue<Coordinate> galaxies) throws IOException {

        BufferedReader bf = new BufferedReader(new FileReader("inputs/day11.txt"));

        String line = bf.readLine();
        PriorityQueue<Integer> jPosGalaxies = new PriorityQueue<>();
        int i = 0;

        while (line != null) {
            int j = 0;
            boolean empty = true;
            char[] items = line.toCharArray();
            for (char item : items) {

                if (item == '#') {
                    if (empty) empty = false;
                    jPosGalaxies.add(j);
                    galaxies.add(new Coordinate(i, j));
                }
                space[i][j++] = item;
            }
            if (empty) doubleI.add(i);
            i++;
            line = bf.readLine();
        }

        for (int j = 0; j < space[0].length && !jPosGalaxies.isEmpty(); j++) {
            if (j != jPosGalaxies.peek()) doubleJ.add(j);
            while (!jPosGalaxies.isEmpty() && j == jPosGalaxies.peek()) jPosGalaxies.poll();
        }

        bf.close();
    }

    private double doubledInI (List<Integer> doubleI, int i1, int i2) {
        double occurrences = 0;
        while (++i1 < i2) if (doubleI.contains(i1)) ++occurrences;
        return occurrences;
    }

    private double doubledInJ (List<Integer> doubleJ, int j1, int j2) {
        double occurrences = 0;

        if (j1 > j2) {
            int temp = j1;
            j1 = j2;
            j2 = temp;
        }

        while (++j1 < j2) {
            if (doubleJ.contains(j1)) ++occurrences;
        };
        return occurrences;
    }


    public double getDistances() throws IOException {

        double distances = 0;

        int[][] space = new int[140][140];
        List<Integer> doubleI = new ArrayList<>();
        List<Integer> doubleJ = new ArrayList<>();
        Queue<Coordinate> galaxies = new LinkedList<>();

        getData(space, doubleI, doubleJ, galaxies);

        while (!galaxies.isEmpty()) {

            Coordinate currentGalaxy = galaxies.poll();

            for (Coordinate nextGalaxy : galaxies) {


                double doubledI = doubledInI(doubleI, currentGalaxy.i, nextGalaxy.i);
                double doubledJ = doubledInJ(doubleJ, currentGalaxy.j, nextGalaxy.j);

                // Part One and Two only deffer on the multiplikator
                double distanceI = Math.abs(currentGalaxy.i - nextGalaxy.i) - doubledI + doubledI * 1000000;
                double distanceJ = Math.abs(currentGalaxy.j - nextGalaxy.j) - doubledJ + doubledJ * 1000000;

                distances += distanceI + distanceJ;
            }
        }


        return distances;
    }
}
