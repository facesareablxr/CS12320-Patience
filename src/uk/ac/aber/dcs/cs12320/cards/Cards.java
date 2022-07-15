package uk.ac.aber.dcs.cs12320.cards;

import java.util.Objects;

/**
 * This is the blueprint for building the filename of the cards, this would be used for the GUI
 */
public class Cards extends Card{
    private String cardFile;

    /**
     * Creates instance of Cards using the superclass Card
     * @param value is from Card, value of the card
     * @param suit is from Card, suit of the card
     * @param cardFileNm is the file name of the card
     */
    public Cards(String value, String suit, String cardFileNm){
        super(value, suit);
        cardFile = cardFileNm;
    }

    /**
     * Gets the card file
     * @return cardFile
     */
    public String getCardFile(){
        return cardFile;
    }

    /**
     * Compares Cards to Card
     * @param o is the object
     * @return the result of the return function
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cards cards = (Cards) o;
        return Objects.equals(cardFile, cards.cardFile);
    }

}
