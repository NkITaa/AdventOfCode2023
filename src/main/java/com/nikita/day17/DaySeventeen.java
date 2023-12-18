package com.nikita.day17;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DaySeventeen {

    int height;
    int width;

    public DaySeventeen(int height, int width) {
        this.height = height;
        this.width = width;
    }

    private int[][] getData() throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader("inputs/day17.txt"));

        String line = bf.readLine();
        int[][] city = new int[height][width];

        int y = 0;
        while (line != null) {

            for (int x = 0; x < width; x++)
                city[y][x] = Integer.parseInt(line.charAt(x) + "");

            ++y;
            line = bf.readLine();
        }

        bf.close();
        return city;
    }


    public Integer getShortestPath() throws IOException {

        int[][] city = getData();

        Path[][] path = new Path[height][width];
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                path[y][x] = new Path(Integer.MAX_VALUE);
        path[0][0].cost = 0;

        PriorityQueue<Block> orderedBlocks = new PriorityQueue<>(Comparator.comparingInt(block -> block.cost));
        orderedBlocks.add(new Block(0, 0, 0, '.', 0));

        while (!orderedBlocks.isEmpty()) {

            Block block = orderedBlocks.poll();
            System.out.println(block.x + "," + block.y + " " + block.cost + " " + block.direction + " " + block.lenDirection);

            if (block.y == height - 1 && block.x == width - 1) {

                for (Path[] r : path) {
                    for (Path s : r) {
                        if (s.cost < 10)
                            System.out.print("00" + s.cost + " ");
                        else if (s.cost < 100)
                            System.out.print("0" + s.cost + " ");
                        else if (s.cost == Integer.MAX_VALUE)
                            System.out.print("000 ");
                        else
                            System.out.print(s.cost + " ");
                    }
                    System.out.println();
                }

                return block.cost;
            }

            for (Block potentialBlock : move(block, path, city))
                if (potentialBlock != null) orderedBlocks.add(potentialBlock);
        }

        return null;
    }

    private List<Block> move(Block block, Path[][] path, int[][] city) {

        List<Block> relevant = new ArrayList<>();

        if (block.direction == '>') {
            relevant.add(goRight(block, path, city));
            relevant.add(goDown(block, path, city));
            relevant.add(goUp(block, path, city));
        } else if (block.direction == '<') {
            relevant.add(goLeft(block, path, city));
            relevant.add(goDown(block, path, city));
            relevant.add(goUp(block, path, city));
        } else if (block.direction == 'v') {
            relevant.add(goLeft(block, path, city));
            relevant.add(goRight(block, path, city));
            relevant.add(goDown(block, path, city));
        } else if (block.direction == '^') {
            relevant.add(goLeft(block, path, city));
            relevant.add(goRight(block, path, city));
            relevant.add(goUp(block, path, city));
        } else {
            relevant.add(goRight(block, path, city));
            relevant.add(goDown(block, path, city));
        }

        return relevant;
    }


    private Block goUp(Block block, Path[][] path, int[][] city) {
        if (block.y > 0)
            return relevant(block.x, block.y - 1, '^', block, path, city);
        return null;
    }

    private Block goDown(Block block, Path[][] path, int[][] city) {
        if (block.y < height - 1)
            return relevant(block.x, block.y + 1, 'v', block, path, city);
        return null;
    }

    private Block goLeft(Block block, Path[][] path, int[][] city) {
        if (block.x > 0)
            return relevant(block.x - 1, block.y, '<', block, path, city);
        return null;
    }

    private Block goRight(Block block, Path[][] path, int[][] city) {
        if (block.x < width - 1)
            return relevant(block.x + 1, block.y, '>', block, path, city);
        return null;
    }

    private Block relevant(int x, int y, char nextDirection, Block oldBlock, Path[][] path, int[][] city) {

        int leavingLen = oldBlock.direction == nextDirection ? oldBlock.lenDirection + 1 : 1;
        // toggle for day one & two
        if (!moreEfficient(x, y, oldBlock, path) || !isValidMin(nextDirection, oldBlock) || !isValidMax(nextDirection, oldBlock))
            return null;
        else {
            if (path[oldBlock.y][oldBlock.x].leavers.get(nextDirection) > leavingLen) {
                path[y][x].cost = oldBlock.cost + city[y][x];
                path[oldBlock.y][oldBlock.x].leavers.put(nextDirection, leavingLen);
                return new Block(path[y][x].cost, x, y, nextDirection, leavingLen);
            }
        }
        return null;
    }

    private boolean isValidMax(char nextDirection, Block oldBlock) {

        if (oldBlock.direction == nextDirection) {
            return oldBlock.lenDirection < 10;
        }
        return true;
    }

    private boolean isValidMin(char nextDirection, Block oldBlock) {
        if (nextDirection != oldBlock.direction) {
            if (oldBlock.lenDirection >= 4)
                return true;
            return oldBlock.direction == '.';
        }
        return true;
    }

    private boolean moreEfficient(int x, int y, Block oldBlock, Path[][] path) {

        if (oldBlock.direction == '>')
            return path[y][x].leavers.get('v') > 1 || path[y][x].leavers.get('^') > 1 || path[y][x].leavers.get('>') > oldBlock.lenDirection;
        else if (oldBlock.direction == '<')
            return path[y][x].leavers.get('v') > 1 || path[y][x].leavers.get('^') > 1 || path[y][x].leavers.get('<') > oldBlock.lenDirection;
        else if (oldBlock.direction == 'v')
            return path[y][x].leavers.get('<') > 1 || path[y][x].leavers.get('>') > 1 || path[y][x].leavers.get('v') > oldBlock.lenDirection;
        else if (oldBlock.direction == '^')
            return path[y][x].leavers.get('<') > 1 || path[y][x].leavers.get('>') > 1 || path[y][x].leavers.get('^') > oldBlock.lenDirection;
        else return oldBlock.direction == '.';
    }

    static private class Block {

        int cost;
        int x;
        int y;
        char direction;
        int lenDirection;

        Block(int cost,
              int x,
              int y,
              char direction,
              int lenDirection) {
            this.cost = cost;
            this.x = x;
            this.y = y;
            this.direction = direction;
            this.lenDirection = lenDirection;
        }
    }

    static private class Path {

        int cost;
        HashMap<Character, Integer> leavers;

        Path(int cost) {
            this.cost = cost;
            leavers = new HashMap<>();
            leavers.put('<', Integer.MAX_VALUE);
            leavers.put('>', Integer.MAX_VALUE);
            leavers.put('v', Integer.MAX_VALUE);
            leavers.put('^', Integer.MAX_VALUE);
        }
    }
}
