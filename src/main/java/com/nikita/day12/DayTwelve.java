package com.nikita.day12;

import com.nikita.day11.DayEleven;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DayTwelve {

    long possibilities = 0;
    HashMap<Integer, ArrayList<Integer>> used = new HashMap<>();

    private static class Spring {

        char[] fields;
        int[] springSizes;
        int sumSpringSizes;


        Spring(char[] fields, int[] springSizes, int sumSpringSizes) {

            this.fields = fields;
            this.springSizes = springSizes;
            this.sumSpringSizes = sumSpringSizes;

        }


    }

    private void getData(List<Spring> springs) throws IOException {

        BufferedReader bf = new BufferedReader(new FileReader("inputs/day12.txt"));
        String line = bf.readLine();

        while (line != null) {

            String[] parts = line.split(" ");
            String[] springSizesString  = parts[1].split(",");

            char[] fields = parts[0].toCharArray();
            char[] newFields = new char[fields.length*5+4];
            int[] springSizes = new int[springSizesString.length];
            int sumSpringSizes = 0;

            for (int i = 0; i < springSizesString.length; i++) {
                int current = Integer.parseInt(springSizesString[i]);
                springSizes[i] = current;
            }


            int[] newSpringSizes = new int[springSizes.length*5];

            int counterF = 0;
            int counterSS = 0;
            for (int i = 0; i < 5; i++) {
                if (i != 0) newFields[counterF++] ='?';
                for (char current: fields) {
                    newFields[counterF++] = current;
                }
                for (int current: springSizes) {
                    newSpringSizes[counterSS++] = current;
                    sumSpringSizes += current;
                }
            }

            // part one
            springs.add(new Spring(fields, springSizes, sumSpringSizes));

            // part two
            // springs.add(new Spring(newFields, newSpringSizes, sumSpringSizes));

            line = bf.readLine();
        }

        bf.close();
    }


    public long getPossibilities() throws IOException {

        List<Spring> springs = new ArrayList<>();
        getData(springs);
        System.out.println("have data");
        for (Spring spring : springs) backtrack(spring.fields, spring.springSizes, spring.sumSpringSizes, 0, new ArrayList<>());

        return possibilities;
    }


    private void backtrack(char[] fields, int[] springSizes, int sumSpringSizes, int i, List<Integer> found) {

        if (found.size() == springSizes.length) {
            int lastAdded = found.get(found.size()-1);
            while (lastAdded < fields.length) if (fields[lastAdded++] == '#') return;
            ++possibilities;
            return;
        }

        for (; i < fields.length && sumSpringSizes <= fields.length - i; i++) {

            if (fields[i] == '.') continue;

            int tempI = i;
            while (tempI < fields.length && fields[tempI] != '.' && tempI-i < springSizes[found.size()]) ++tempI;

            if (tempI-i == springSizes[found.size()]) {

                found.add(tempI);
                if (tempI < fields.length && fields[tempI] != '#') backtrack(fields, springSizes, sumSpringSizes-tempI-i, tempI+1, found);
                else if (tempI == fields.length) backtrack(fields, springSizes, sumSpringSizes-tempI-i, tempI, found);
                found.remove(found.size()-1);
            }

            if (fields[i] == '#') return;
        }
    }
}
