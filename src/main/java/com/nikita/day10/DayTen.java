package com.nikita.day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Coordinate {
    int i;
    int j;

    public Coordinate(int i, int j) {
        this.i = i;
        this.j = j;
    }
}

public class DayTen {
    List<String> verticalOpenings = new ArrayList<>(Arrays.asList("J|", "7|", "||", "|L", "|F", "JL", "JF", "7L", "7F"));
    List<String>  horizontalOpenings = new ArrayList<>(Arrays.asList("J-", "L-", "--", "-7", "-F", "J7", "JF", "L7", "LF"));

    private Coordinate getData(char[][] maze) throws IOException {

        BufferedReader bf = new BufferedReader(new FileReader("inputs/day10.txt"));

        Coordinate coordinate = new Coordinate(0, 0);

        String line = bf.readLine();
        int i = 0;

        while (line != null) {
            int j = 0;
            for (char item : line.toCharArray()) {
                if (item == 'S') {
                    coordinate.i = i;
                    coordinate.j = j;
                }
                maze[i][j++] = item;
            }
            i++;
            line = bf.readLine();
        }

        bf.close();
        return coordinate;
    }

    private int processTop(Queue<Coordinate> toExplore, char[][] maze,int[][] paths, Coordinate current) {
        if (current.i > 0)
            switch (maze[current.i - 1][current.j]) {
                case '7', '|', 'F' -> {
                    if (paths[current.i - 1][current.j] > paths[current.i][current.j] + 1)
                        return setData(new Coordinate(current.i - 1, current.j), current, paths, toExplore);
                }
            }
        return 0;
    }
    private int processBottom(Queue<Coordinate> toExplore, char[][] maze,int[][] paths, Coordinate current) {
        if (current.i < maze.length - 1)
            switch (maze[current.i + 1][current.j]) {
                case 'J', '|', 'L' -> {
                    if (paths[current.i + 1][current.j] > paths[current.i][current.j] + 1)
                        return setData(new Coordinate(current.i + 1, current.j), current, paths, toExplore);
                }
            }
        return 0;
    }
    private int processLeft(Queue<Coordinate> toExplore, char[][] maze,int[][] paths, Coordinate current) {
        if (current.j > 0)
            switch (maze[current.i][current.j - 1]) {
                case '-', 'L', 'F' -> {
                    if (paths[current.i][current.j - 1] > paths[current.i][current.j] + 1)
                        return setData(new Coordinate(current.i, current.j - 1), current, paths, toExplore);
                }
            }
        return 0;
    }
    private int processRight(Queue<Coordinate> toExplore, char[][] maze,int[][] paths, Coordinate current) {
        if (current.j < maze[0].length - 1)
            switch (maze[current.i][current.j+1]) {
                case '7', 'J', '-'-> {
                    if (paths[current.i][current.j+1] > paths[current.i][current.j] + 1)
                        return setData(new Coordinate(current.i, current.j+1), current, paths, toExplore);
                }
            }
        return 0;
    }

    private int setData(Coordinate todoCoordinate, Coordinate current, int[][] paths, Queue<Coordinate> toExplore) {
        toExplore.add(todoCoordinate);
        int length = paths[current.i][current.j] + 1;

        // part One
        //paths[todoCoordinate.i][todoCoordinate.j] = length;

        // part Two
        paths[todoCoordinate.i][todoCoordinate.j] = 0;
        return length;
    }

    private int toExplore(Queue<Coordinate> toExplore, Coordinate current, char[][] maze, int[][] paths) {


        int longestLocal = 0;

        switch (maze[current.i][current.j]) {
            case ('J') -> {
                longestLocal = Math.max(longestLocal, processTop(toExplore, maze, paths, current));
                longestLocal = Math.max(longestLocal, processLeft(toExplore, maze, paths, current));
            }
            case ('|') -> {
                longestLocal = Math.max(longestLocal, processTop(toExplore, maze, paths, current));
                longestLocal = Math.max(longestLocal, processBottom(toExplore, maze, paths, current));
            }
            case ('-') -> {
                longestLocal = Math.max(longestLocal, processLeft(toExplore, maze, paths, current));
                longestLocal = Math.max(longestLocal, processRight(toExplore, maze, paths, current));
            }
            case ('L') -> {
                longestLocal = Math.max(longestLocal, processTop(toExplore, maze, paths, current));
                longestLocal = Math.max(longestLocal, processRight(toExplore, maze, paths, current));
            }
            case ('7') -> {
                longestLocal = Math.max(longestLocal, processBottom(toExplore, maze, paths, current));
                longestLocal = Math.max(longestLocal, processLeft(toExplore, maze, paths, current));
            }
            case ('F') -> {
                longestLocal = Math.max(longestLocal, processBottom(toExplore, maze, paths, current));
                longestLocal = Math.max(longestLocal, processRight(toExplore, maze, paths, current));
            }
        }

        return longestLocal;
    }

    private int initialQueueSetting(Queue<Coordinate> toExplore, Coordinate initial, char[][] maze, int[][] paths) {

        paths[initial.i][initial.j] = 0;
        int longestLocal = 0;

        longestLocal = Math.max(longestLocal, processTop(toExplore, maze, paths, initial));
        longestLocal = Math.max(longestLocal, processBottom(toExplore, maze, paths, initial));
        longestLocal = Math.max(longestLocal, processLeft(toExplore, maze, paths, initial));
        longestLocal = Math.max(longestLocal, processRight(toExplore, maze, paths, initial));

        return longestLocal;
    }

    // part one
    public int getLongestDistance() throws IOException {

        char[][] maze = new char[140][140];
        int[][] paths = new int[140][140];
        for (int[] path : paths) Arrays.fill(path, Integer.MAX_VALUE);

        Queue<Coordinate> toExplore = new LinkedList<>();

        Coordinate sCoordinate = getData(maze);
        int longestPath = initialQueueSetting(toExplore, sCoordinate, maze, paths);

        while (!toExplore.isEmpty())
            longestPath = Math.max(longestPath, toExplore(toExplore, toExplore.poll(), maze, paths));

        return longestPath;
    }


    public void fillOutPaths(char[][] maze, int[][] paths) throws IOException {

        for (int[] path : paths) Arrays.fill(path, Integer.MAX_VALUE);

        Queue<Coordinate> toExplore = new LinkedList<>();

        Coordinate sCoordinate = getData(maze);
        initialQueueSetting(toExplore, sCoordinate, maze, paths);

        while (!toExplore.isEmpty())
            toExplore(toExplore, toExplore.poll(), maze, paths);
    }

    private void tryBottom(Coordinate current, Queue<Coordinate> toMark, int[][] paths, char[][] maze) {
        int i = current.i;
        int j = current.j;

        if (i > 0) {
            if (paths[i-1][j] == Integer.MAX_VALUE)
                toMark.add(new Coordinate(i - 1, j));
            else if (j < maze[0].length - 1 && verticalOpenings.contains(new String(new char[]{maze[i - 1][j], maze[i - 1][j + 1]})))
                tryBottom(new Coordinate(i-1, j), toMark, paths, maze);
        }
    }

    private void tryTop(Coordinate current, Queue<Coordinate> toMark, int[][] paths, char[][] maze) {
        int i = current.i;
        int j = current.j;

        if (i < maze.length - 1) {
            if (paths[i+1][j] == Integer.MAX_VALUE) toMark.add(new Coordinate(i+1, j));
            else if(j < maze[0].length - 1 && verticalOpenings.contains(new String(new char[]{maze[i + 1][j], maze[i + 1][j + 1]})))
                tryTop(new Coordinate(i+1, j), toMark, paths, maze);
        }
    }

    private void tryLeft(Coordinate current, Queue<Coordinate> toMark, int[][] paths, char[][] maze) {
        int i = current.i;
        int j = current.j;

        if (j > 0) {
            if (paths[i][j-1] == Integer.MAX_VALUE)
                toMark.add(new Coordinate(i, j-1));
            else if (i < maze.length - 1 && horizontalOpenings.contains(new String(new char[]{maze[i][j - 1], maze[i + 1][j - 1]})))
                tryLeft(new Coordinate(i, j-1), toMark, paths, maze);
        }
    }

    private void tryRight(Coordinate current, Queue<Coordinate> toMark, int[][] paths, char[][] maze) {
        int i = current.i;
        int j = current.j;

        if (j < maze[0].length - 1) {
            if (paths[i][j+1] == Integer.MAX_VALUE) toMark.add(new Coordinate(i, j+1));
            else if (i < maze.length - 1 && horizontalOpenings.contains(new String(new char[]{maze[i][j + 1], maze[i + 1][j + 1]})))
                tryRight(new Coordinate(i, j+1), toMark, paths, maze);
        }
    }

    // part two
    public int getEnclosedChars() throws IOException {

        int enclosed = 0;
        char[][] maze = new char[140][140];
        int[][] paths = new int[140][140];
        getData(maze);
        fillOutPaths(maze, paths);


        Queue<Coordinate> toMark = new LinkedList<>();

        for (int i = 0; i < maze.length; i++) {

            if (paths[i][0] == Integer.MAX_VALUE) {
                toMark.add(new Coordinate(i, 0));
                while (!toMark.isEmpty()) {
                    Coordinate current = toMark.poll();
                    if (paths[current.i][current.j] == 3) continue;
                    tryRight(current, toMark, paths, maze);
                    tryTop(current, toMark, paths, maze);
                    tryBottom(current, toMark, paths, maze);
                    tryLeft(current, toMark, paths, maze);
                    paths[current.i][current.j] = 3;
                }

            }

            if (paths[i][maze[0].length-1] == Integer.MAX_VALUE) {
                toMark.add(new Coordinate(i, maze[0].length-1));
                while (!toMark.isEmpty()) {
                    Coordinate current = toMark.poll();
                    if (paths[current.i][current.j] == 3) continue;
                    tryRight(current, toMark, paths, maze);
                    tryLeft(current, toMark, paths, maze);
                    tryTop(current, toMark, paths, maze);
                    tryBottom(current, toMark, paths, maze);
                    paths[current.i][current.j] = 3;
                }
            }
        }

        for (int j = 0; j < maze[0].length; j++) {

            if (paths[0][j] == Integer.MAX_VALUE) {
                toMark.add(new Coordinate(0, j));
                while (!toMark.isEmpty()) {
                    Coordinate current = toMark.poll();
                    if (paths[current.i][current.j] == 3) continue;
                    tryRight(current, toMark, paths, maze);
                    tryTop(current, toMark, paths, maze);
                    tryLeft(current, toMark, paths, maze);
                    tryBottom(current, toMark, paths, maze);
                    paths[current.i][current.j] = 3;
                }
            }

            if (paths[maze.length-1][j] == Integer.MAX_VALUE) {
                toMark.add(new Coordinate(maze.length-1, j));
                while (!toMark.isEmpty()) {
                    Coordinate current = toMark.poll();
                    if (paths[current.i][current.j] == 3) continue;
                    tryRight(current, toMark, paths, maze);
                    tryTop(current, toMark, paths, maze);
                    tryBottom(current, toMark, paths, maze);
                    tryLeft(current, toMark, paths, maze);
                    paths[current.i][current.j] = 3;
                }
            }
        }

        for (int[] row : paths) {
            for (int val : row) if (val == Integer.MAX_VALUE) ++enclosed;
        }

        return enclosed;

    }
}
