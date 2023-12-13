package com.nikita.day13;

import com.nikita.day12.DayTwelve;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DayThirteen {

    static private class Coordinate {

        int start;
        int finish;

        Coordinate(int start, int finish) {
            this.start = start;
            this.finish = finish;
        }
    }



    private void getData(ArrayList<ArrayList<char[]>> maps) throws IOException {

        BufferedReader bf = new BufferedReader(new FileReader("inputs/day13.txt"));
        String line = bf.readLine();

        ArrayList<char[]> map = new ArrayList<>();

        while (line != null) {

            if (line.trim().isEmpty()) {
                maps.add(new ArrayList<>(map));
                map.clear();
                line = bf.readLine();
                continue;
            }

            map.add(line.toCharArray());

            line = bf.readLine();
        }


        maps.add(new ArrayList<>(map));
        map.clear();

        bf.close();
    }

    private int equalCol(int l, int r, ArrayList<char[]> map) {
        int differences = 0;
        for (char[] row : map)
            if (row[l] != row[r] && ++differences > 1)
                return differences;
        return differences;
    }

    private int equalRow(int down, int up, ArrayList<char[]> map) {
        char[] downRow = map.get(down);
        char[] upRow = map.get(up);
        int differences = 0;
        for (int i = 0; i < downRow.length; i++)
            if (downRow[i] != upRow[i] && ++differences > 1) return differences;


        return differences;
    }

    private boolean foundInCol(Stack<Coordinate> relevant, ArrayList<char[]> map) {

        int length = map.get(0).length;

        if (length % 2 == 0) {
            for (int i = 1; i < length; i++)
                if (equalCol(0, i, map) <= 1) relevant.add(new Coordinate(0, i));
        }
        else {
            for (int i = 1; i < length - 1; i++)
                if (equalCol(0, i, map) <= 1) relevant.add(new Coordinate(0, i));

            for (int i = length - 2; i > 0; i--)
                if (equalCol(i, length - 1, map) <= 1) relevant.add(new Coordinate(i, length - 1));
        }

        return !relevant.isEmpty();
    }

    private boolean foundInRow(Stack<Coordinate> relevant, ArrayList<char[]> map) {

        int length = map.size();
        int dif = 0;

        if (length % 2 == 0) {
            for (int i = 1; i < length; i++)
                if (equalRow(0, i, map) <= 1) relevant.add(new Coordinate(0, i));
        }
        else {
            for (int i = 1; i < length - 1; i++)
                if (equalRow(0, i, map) <= 1) relevant.add(new Coordinate(0, i));

            for (int i = length - 2; i > 0; i--) {
                if (equalRow(i, length - 1, map) <= 1) relevant.add(new Coordinate(i, length - 1));
            }
        }

        return !relevant.isEmpty();
    }

    // for part one you just have to change the diff
    public int mirroredOperations() throws IOException {

        int columnsToLeft = 0;
        int rowsAbove = 0;
        ArrayList<ArrayList<char[]>> maps = new ArrayList<>();
        getData(maps);

        Stack<Coordinate> relevant = new Stack<>();

        for (ArrayList<char[]> map : maps) {

            int max = 0;

            if (foundInCol(relevant, map)) {

                while (!relevant.isEmpty()) {
                    Coordinate current = relevant.pop();
                    int dif = 0;
                    int l = current.start;
                    int r = current.finish;

                    boolean valid = true;

                    while (l < r && valid)
                        if ((dif += equalCol(l++, r--, map)) > 1) valid = false;

                    if ((r - l) % 2 == 0 || dif == 0) continue;
                    if (valid) max = Math.max(l, max);
                }

                columnsToLeft += max;
            }
            if (max == 0 && foundInRow(relevant, map)) {

                while (!relevant.isEmpty()) {
                    Coordinate current = relevant.pop();
                    int dif = 0;
                    int down = current.start;
                    int up = current.finish;

                    boolean valid = true;

                    while (down < up && valid)
                        if ((dif += equalRow(down++, up--, map)) > 1) valid = false;

                    if ((up - down) % 2 == 0 || dif == 0) continue;

                    if (valid) max = Math.max(down, max);
                }

                rowsAbove += max;
            }
        }

        return rowsAbove * 100 + columnsToLeft ;
    }
}

