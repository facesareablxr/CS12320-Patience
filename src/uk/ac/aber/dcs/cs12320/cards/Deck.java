package uk.ac.aber.dcs.cs12320.cards;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


/**
 * Deck is the class, which provides the blueprint as to how the deck is built up. It provides the methods which allow the
 * ArrayList called deck to be built up through the reading of the file given to it in the Game class, and where the deck is
 * shuffled for when the game is to be played.
 */
public class Deck{
    public ArrayList<Card> deck = new ArrayList<Card>();
    public int sizeOfDeck;
    public String cardValue;
    public String cardSuit;
    public int shuffle = 0;

    /**
     * This is where the deck file is read in and the deck array list of cards is built up through reding the values
     * @param fileName is the name of the file which stores the list of cards
     */
    public Deck(File fileName) {
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            Scanner deckFile = new Scanner(br);
            sizeOfDeck = deckFile.nextInt();
            for (int i = 0; i < sizeOfDeck; i++) {
                cardValue = deckFile.next();
                cardSuit = deckFile.next();
                Card newCard = new Card(cardValue, cardSuit);
                deck.add(newCard);
                sizeOfDeck = getSizeOfDeck();
            }
            deckFile.close();
        } catch (FileNotFoundException e) {
            Game gameApp = new Game();
            System.out.println("Please try again, the file you have input has not been found!");
            gameApp.initialiseMenu();
            gameApp.initialise();
            gameApp.runMenu();
        }
    }

    /**
     * This gets the cards by their index from the ArrayList deck
     * @param index i
     * @return the card placeholder value
     */
    public Card getCardByIndex(int index) {
        Card cardPlaceHolder = deck.get(index);
        return cardPlaceHolder;
    }

    /**
     * FR2 - This function will shuffle the deck into a random order, this will be able to be selected in the main
     * Game class and the changes can be seen in the "Show the pack"
     * @return
     */
    public int shuffleDeck() {
        Collections.shuffle(deck);
        System.out.println("The deck has been shuffled!");
        shuffle = 1;
        return shuffle;
    }

    /**
     * Gives the size of the deck.
     * @return sizeOfDeck
     */
    public int getSizeOfDeck() {
        return sizeOfDeck;
    }

    /**
     * Creates a string of the deck with each individual card in
     * @return sb
     */
    @Override
    public String toString() {
        Card cardHolder;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sizeOfDeck; i++) {
            cardHolder = deck.get(i);
            sb.append(cardHolder.toString());
            sb.append(" ");
        }

        return sb.toString();
    }
}
