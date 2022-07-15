package uk.ac.aber.dcs.cs12320.cards;

public class Scores implements Comparable<Scores> {
    private String name;
    private int score;

    /**
     * Creates an instance of scores
     * @param nM is the name of the player
     * @param sC is the score of the player
     */
    public Scores(String nM, int sC){
        name = nM;
        score = sC;
    }

    /**
     * Gets the name of the player
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the player
     * @param name of player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the score of the player
     * @return score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the score of the player
     * @param score of player
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Builds a string of the player and their score
     * @return sb
     */
    public String toString() {
        final StringBuilder sb = new StringBuilder("");
        sb.append(name);
        sb.append(" ").append(score);
        return sb.toString();
    }

    @Override
    public int compareTo(Scores o) {
        int compareScore= o.getScore();
        return compareScore-this.score;
    }
}
