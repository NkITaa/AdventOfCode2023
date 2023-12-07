package com.nikita.day7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class DaySeven {

    private void getData(HashMap<String, Integer> cards) throws IOException {


        BufferedReader bf = new BufferedReader(new FileReader("inputs/day7.txt"));

        String line = bf.readLine();

        while (line != null) {

            String card = line.substring(0, 5);
            int value = Integer.parseInt(line.substring(5).trim());

            cards.put(card, value);

            line = bf.readLine();
        }

        bf.close();
    }

    private void sortCards(HashMap<String, Integer> cards, HashMap<String, Integer>[] sortedCards) {

        for (String card : cards.keySet()) {

            int distinctChars = (int) card.chars().distinct().count();
            StringBuilder newCard = new StringBuilder();

            int mostChar = 0;
            int secondMostChar = 0;
            ArrayList<Character> chars = new ArrayList<Character>();

            for (char character : card.toCharArray()) {

                if (chars.isEmpty() || chars.get(0) == character) {
                    if (chars.isEmpty()) chars.add(character);
                    ++mostChar;
                }

                if (chars.size() == 1 || chars.get(1) == character) {
                    if (chars.size() == 1 && !chars.contains(character)) chars.add(character);
                    if (chars.size() == 2) ++secondMostChar;
                }


                if (character == 'A') newCard.append('M');
                else if (character == 'K') newCard.append('L');
                else if (character == 'Q') newCard.append('K');
                else if (character == 'J') newCard.append('J');
                else if (character == 'T') newCard.append('I');
                else newCard.append(character);
            }

            if (mostChar < secondMostChar) mostChar = secondMostChar;

            if (distinctChars == 5) {
                if (sortedCards[0] == null) sortedCards[0] = new HashMap<>();
                sortedCards[0].put(newCard.toString(), cards.get(card));
            }
            else if (distinctChars == 4) {
                if (sortedCards[1] == null) sortedCards[1] = new HashMap<>();
                sortedCards[1].put(newCard.toString(), cards.get(card));
            }
            else if (distinctChars == 3 && mostChar == 2) {
                if (sortedCards[2] == null) sortedCards[2] = new HashMap<>();
                sortedCards[2].put(newCard.toString(), cards.get(card));

            }
            else if (distinctChars == 3) {
                if (sortedCards[3] == null) sortedCards[3] = new HashMap<>();
                sortedCards[3].put(newCard.toString(), cards.get(card));
            }
            else if (distinctChars == 2 && mostChar == 3) {
                if (sortedCards[4] == null) sortedCards[4] = new HashMap<>();
                sortedCards[4].put(newCard.toString(), cards.get(card));
            }
            else if (distinctChars == 2) {
                if (sortedCards[5] == null) sortedCards[5] = new HashMap<>();
                sortedCards[5].put(newCard.toString(), cards.get(card));
            }
            else {
                if (sortedCards[6] == null) sortedCards[6] = new HashMap<>();
                sortedCards[6].put(newCard.toString(), cards.get(card));
            }
        }
    }

    private void sortCardsJMethod(HashMap<String, Integer> cards, HashMap<String, Integer>[] sortedCards) {

        for (String card : cards.keySet()) {

            int distinctChars = (int) card.chars().distinct().count();
            StringBuilder newCard = new StringBuilder();

            int quantityJ = 0;
            HashMap<Character, Integer> chars = new HashMap<Character, Integer>();

            for (char character : card.toCharArray()) {

                chars.put(character, chars.getOrDefault(character, 0) + 1);
                if (character == 'J') ++quantityJ;


                if (character == 'A') newCard.append('M');
                else if (character == 'K') newCard.append('L');
                else if (character == 'Q') newCard.append('K');
                else if (character == 'J') newCard.append('1');
                else if (character == 'T') newCard.append('I');
                else newCard.append(character);
            }


            ArrayList<Integer> sortedQuantities = new ArrayList<>(chars.values().stream().sorted(Comparator.reverseOrder()).toList() ) ;


            int mostChar = sortedQuantities.get(0);

            if (quantityJ > 0 && quantityJ != 5) {
                --distinctChars;
                if (quantityJ == mostChar) mostChar += sortedQuantities.get(1);
                else mostChar += quantityJ;
            }


            if (distinctChars == 5) {
                if (sortedCards[0] == null) sortedCards[0] = new HashMap<>();
                sortedCards[0].put(newCard.toString(), cards.get(card));
            }
            else if (distinctChars == 4) {
                if (sortedCards[1] == null) sortedCards[1] = new HashMap<>();
                sortedCards[1].put(newCard.toString(), cards.get(card));
            }
            else if (distinctChars == 3 && mostChar == 2) {
                if (sortedCards[2] == null) sortedCards[2] = new HashMap<>();
                sortedCards[2].put(newCard.toString(), cards.get(card));
            }
            else if (distinctChars == 3) {
                if (sortedCards[3] == null) sortedCards[3] = new HashMap<>();
                sortedCards[3].put(newCard.toString(), cards.get(card));
            }
            else if (distinctChars == 2 && mostChar == 3) {
                if (sortedCards[4] == null) sortedCards[4] = new HashMap<>();
                sortedCards[4].put(newCard.toString(), cards.get(card));
            }
            else if (distinctChars == 2) {
                if (sortedCards[5] == null) sortedCards[5] = new HashMap<>();
                sortedCards[5].put(newCard.toString(), cards.get(card));
            }
            else {
                if (sortedCards[6] == null) sortedCards[6] = new HashMap<>();
                sortedCards[6].put(newCard.toString(), cards.get(card));
            }
        }
    }

    public int getDeckValue() throws IOException {

        HashMap<String, Integer> cards = new HashMap<String, Integer>();
        int sol = 0;
        getData(cards);
        HashMap<String, Integer> sortedCards[] = new HashMap[7];

        // part One
        //sortCards(cards, sortedCards);

        // part Two
        sortCardsJMethod(cards, sortedCards);

        int pos = 1;

        for (HashMap<String, Integer> sortedCard : sortedCards) {

            if (sortedCard == null) continue;

            ArrayList<String> sortedStaples = new ArrayList<>(sortedCard.keySet());
            sortedStaples.sort(Comparator.naturalOrder());

            for (String card : sortedStaples) {
                sol += pos * sortedCard.get(card);
                pos++;
            }
        }

        return sol;
    }

}
