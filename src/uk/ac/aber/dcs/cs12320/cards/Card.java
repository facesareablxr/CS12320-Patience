package uk.ac.aber.dcs.cs12320.cards;

/**
 * This "Card" class is the class which forms the blueprint that a card is based off, it includes their suit and their
 * value. It is referenced in deck to help with the card reading and building of the deck.
 */

public class Card {
    private String value;
    private String suit;

    /**
     *
     * @param cardValue is the value of the card - 1-9, T, A, K, Q, J
     * @param cardSuit is the suit of the card - S,C,H,D
     */
    public Card(String cardValue, String cardSuit){
        value = cardValue;
        suit = cardSuit;
    }

    /**
     * Gets the value of the card
     * @return value
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the card
     * @param value where it's the value of the card between 1-9, T, A, K, Q or J
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the suit of the card
     * @return suit
     */
    public String getSuit() {
        return suit;
    }

    /**
     * Sets the suit of the card
     * @param suit where it is the suit of the card from S, H, C, D
     */
    public void setSuit(String suit) {
        this.suit = suit;
    }

    /**
     * Builds up a string of a card with their value and suit, so Ace of Spades would be as
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(value);
        sb.append(suit);
        return sb.toString();
    }
}


