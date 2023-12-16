package com.nikita.day16;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class DaySixteen {

    int height;
    int width;

    public DaySixteen(int height, int width) {
        this.height = height;
        this.width = width;
    }

    private Field[][] getData() throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader("inputs/day16.txt"));

        String line = bf.readLine();
        Field[][] map = new Field[height][width];

        int y = 0;
        while (line != null) {

            for (int x = 0; x < width; x++)
                map[y][x] = new Field(line.charAt(x));

            ++y;
            line = bf.readLine();
        }

        bf.close();
        return map;
    }

    private ArrayList<Beam> move(Beam beam, Field[][] map) {

        int x = beam.x;
        int y = beam.y;
        char direction = beam.direction;
        char type = map[y][x].type;

        ArrayList<Beam> beams = new ArrayList<>();


        if (direction == '^') {
            if (type == '-') {
                beams.add(goRight(x, y, map));
                beams.add(goLeft(x, y, map));
            } else if (type == '/')
                beams.add(goRight(x, y, map));
            else if (type == '\\')
                beams.add(goLeft(x, y, map));
            else
                beams.add(goUp(x, y, map));
        } else if (direction == 'v') {
            if (type == '-') {
                beams.add(goRight(x, y, map));
                beams.add(goLeft(x, y, map));
            } else if (type == '/')
                beams.add(goLeft(x, y, map));
            else if (type == '\\')
                beams.add(goRight(x, y, map));
            else
                beams.add(goDown(x, y, map));
        } else if (direction == '<') {
            if (type == '|') {
                beams.add(goUp(x, y, map));
                beams.add(goDown(x, y, map));
            } else if (type == '/')
                beams.add(goDown(x, y, map));
            else if (type == '\\')
                beams.add(goUp(x, y, map));
            else
                beams.add(goLeft(x, y, map));
        } else if (direction == '>') {
            if (type == '|') {
                beams.add(goUp(x, y, map));
                beams.add(goDown(x, y, map));
            } else if (type == '/')
                beams.add(goUp(x, y, map));
            else if (type == '\\')
                beams.add(goDown(x, y, map));
            else
                beams.add(goRight(x, y, map));
        }


        return beams;
    }

    private Beam goUp(int x, int y, Field[][] map) {

        if (y > 0)
            return alreadyVisited(x, y - 1, map, '^');

        return null;

    }

    private Beam goDown(int x, int y, Field[][] map) {

        if (y < height - 1)
            return alreadyVisited(x, y + 1, map, 'v');

        return null;
    }

    private Beam goLeft(int x, int y, Field[][] map) {

        if (x > 0)
            return alreadyVisited(x - 1, y, map, '<');

        return null;
    }

    private Beam goRight(int x, int y, Field[][] map) {

        if (x < width - 1)
            return alreadyVisited(x + 1, y, map, '>');

        return null;
    }

    private Beam alreadyVisited(int x, int y, Field[][] map, char beamDirection) {
        if (map[y][x].visitedChars.contains(beamDirection))
            return null;
        else {
            map[y][x].visitedChars.add(beamDirection);
            return new Beam(x, y, beamDirection);
        }
    }

    public int crossedFields(int x, int y, char direction) throws IOException {

        int crossedFields = 0;
        Field[][] map = getData();
        Stack<Beam> beams = new Stack<>();

        Beam rootBeam = new Beam(x, y, direction);
        beams.add(rootBeam);
        map[rootBeam.y][rootBeam.x].visitedChars.add(rootBeam.direction);

        while (!beams.isEmpty()) {
            ArrayList<Beam> toAdd = move(beams.pop(), map);
            for (Beam beam : toAdd)
                if (beam != null) beams.add(beam);
        }

        for (Field[] row : map)
            for (Field field : row)
                if (!field.visitedChars.isEmpty())
                    ++crossedFields;

        return crossedFields;
    }

    // part Two
    public int maxConfig() throws IOException {

        int maxConfig = 0;

        for (int x = 0; x < width; x++) {
            maxConfig = Math.max(maxConfig, crossedFields(x, 0, 'v'));
            maxConfig = Math.max(maxConfig, crossedFields(x, height - 1, '^'));
        }

        for (int y = 0; y < height; y++) {
            maxConfig = Math.max(maxConfig, crossedFields(0, y, '>'));
            maxConfig = Math.max(maxConfig, crossedFields(width - 1, y, '<'));
        }

        return maxConfig;
    }

    static class Field {

        char type;
        Set<Character> visitedChars;

        Field(char type) {
            this.type = type;
            visitedChars = new HashSet<>();
        }

    }

    static class Beam {

        int x;
        int y;

        char direction;

        Beam(int x, int y, char direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }

    }
}
