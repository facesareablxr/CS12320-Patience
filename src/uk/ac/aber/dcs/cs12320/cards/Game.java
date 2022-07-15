/**
 * The Game of patience main class
 *
 * @author Faisal Rezwan, Chris Loftus and Lynda Thomas
 * @version 3.0
 * <p>
 * Updated and added to by Lauren Davis
 */

package uk.ac.aber.dcs.cs12320.cards;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This is the Game class, this is the blueprint for the majority of methods required for functionality.
 */
public class Game {
    //private CardTable cardTable;
    private Scanner scan;
    private Deck deck;
    private File cardDeckFile;
    private int cardsDrawn;
    private int topCard;
    public ArrayList<String> gameTable;
    private final ArrayList<String> removedCards;
    private int cardPiles;
    private boolean indexValid = false;
    private Leaderboard leaderboard;
    public static File scoreFile;
    private int newScore;
    private boolean AIUsed;
    private boolean moveMade;
    private static boolean customDeck;

//    //@Override
//    public void start(Stage stage) {
//        cardTable = new CardTable(stage);
//
//        // The interaction with this game is from a command line
//        // menu. We need to create a separate non-GUI thread
//        // to run this in. DO NOT REMOVE THIS.
//        Runnable commandLineTask = () -> {
//            // REPLACE THE FOLLOWING EXAMPLE WITH YOUR CODE
//            ArrayList<String> cardStrings = new ArrayList<>();
//            cardStrings.add("3h.gif");
//            cardStrings.add("tc.gif");
//            cardStrings.add("js.gif");
//            cardStrings.add("4d.gif");
//            cardTable.cardDisplay(cardStrings);
//        };
//        Thread commandLineThread = new Thread(commandLineTask);
//        // This is how we start the thread.
//        // This causes the run method to execute.
//        commandLineThread.start();
//    }

    /**
     * This creates a new instance of the game with a new clear game table, the top card being at index 0
     */
    public Game() {
        gameTable = new ArrayList<>();
        removedCards = new ArrayList<>();
        topCard = 0;
        cardPiles = -1;
        cardsDrawn = 0;
        newScore = 0;
        AIUsed = false;
        customDeck = false;
    }

    /**
     * This is the menu used to initialise the game, presenting the users options
     */
    public static void initialiseMenu() {
        System.out.println("""
                Please choose a deck to load: 
                1 -  Standard deck configuration
                2 -  Custom deck configuration
                Q -  Quit""");
    }


    /**
     * I have added in the option to use a standard or custom deck configuration, the custom decks are called through their
     * file name and will disable scoring
     */
    public void initialise() {
        scan = new Scanner(System.in);
        String option = scan.next();
        switch (option) {
            case "1":
                cardDeckFile = new File("cards.txt");
                deck = new Deck(cardDeckFile);
                scoreFile = new File("scores.txt");
                leaderboard = new Leaderboard(scoreFile);
                break;
            case "2":
                System.out.println("WARNING: For a custom deck, you will not receive a score! ");
                System.out.println("Enter the name of the deck file you would like to use, include .txt at the end: ");
                String fileName = scan.next();
                cardDeckFile = new File(fileName);
                deck = new Deck(cardDeckFile);
                customDeck = true;
                break;
            case "Q":
                System.exit(0);
            default:
                System.out.println("Input not valid! Try again!");
                initialise();
                break;
        }
    }

    /**
     * This is NFR1 - The menu is printed in the command line for the user to select an option from
     */
    private void printMenu() {
        System.out.println("""
                1 -  Show the pack
                2 -  Shuffle the cards
                3 -  Deal a card
                4 -  Make a move: move last card onto previous pile
                5 -  Make a move: move last card onto the pile skipping over two piles
                6 -  Amalgamate all piles in the middle
                7 -  Play for me
                8 -  Show top 10 results
                9 -  Restart with a new deck
                Q -  Quit""");
    }

    /**
     * This is also NFR1 - This just accepts user input after printing the menu, and calls one of the functions listed
     * in the menu
     */
    public void runMenu() {
        printMenu();
        String input;
        do {
            System.out.println("What would you like to do: ");
            scan = new Scanner(System.in);
            input = scan.nextLine().toUpperCase();
            switch (input) {
                case "1": //FR1
                    if (deck.shuffle == 0) {
                        showPack();
                    } else {
                        System.out.println("You cannot view the deck once it has been shuffled!");
                    }
                    break;
                case "2": //FR2
                    if (deck.shuffle == 0) {
                        deck.shuffleDeck();
                    } else {
                        System.out.println("Deck can only be shuffled once per game!");
                    }
                    break;
                case "3": //FR3
                    if (deck.shuffle == 0) {
                        deck.shuffleDeck();
                        dealOneCard();
                    } else {
                        dealOneCard();
                    }
                    break;
                case "4": //FR4
                    moveBackOne();
                    break;
                case "5": //FR5
                    moveBackTwo();
                    break;
                case "6": //FR6
                    amalgamateChoices();
                    break;
                case "7": //FR8
                    howManyTimes();
                    break;
                case "8": //FR9
                    leaderboard.printLeaderboard();
                    break;
                case "9":
                    restart();
                    break;
                case "Q":
                    quitGame();
                    break;
                default:
                    System.out.println("Try again!");
            }
        } while (!(input.equals("Q")));
    }

    /**
     * FR1 - This function is to show the pack of cards in their current state, it uses the index to print within that range
     */
    private void showPack() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < deck.sizeOfDeck; i++) {
            Card card = deck.getCardByIndex(i);
            String cardName = card.toString();
            sb.append(cardName).append(" ");
        }
        System.out.println(sb);
    }

    /**
     * FR3 - This function is to deal the card from the top of the deck, adding it to a new array called the gameTable
     * this is the current set of cards on the table, and adds one to the topCard to ensure that if the option is chosen
     * multiple times, the card next to it is chosen
     */
    public Boolean dealOneCard() {
        if (topCard > (deck.sizeOfDeck - 1)) {
            System.out.println("There are no more cards in this deck! Choose another option.");
        } else {
            Card drawnCard;
            drawnCard = deck.getCardByIndex(topCard);
            gameTable.add(String.valueOf(drawnCard));
            topCard++;
            cardPiles++;
            cardsDrawn = cardsDrawn + 1;
            System.out.println("The drawn card is: " + drawnCard);
            System.out.println(("The current playing field: " + gameTable).replace("[", "").replace("]", ""));
            moveMade = true;
        }
        return moveMade;
    }

    /**
     * FR4 - This function is to move the last pile onto the previous one, it passes chosenPile, destinationPile and cardPilesMoved
     * through the makeMove function
     *
     * @return moveMade - If a move has been made or not
     */
    private void moveBackOne() {
        if (cardPiles > 0) {
            int chosenPile = (cardPiles);
            int destinationPile = (cardPiles - 1);
            int cardPilesMoved = 1;
            makeMove(chosenPile, destinationPile, cardPilesMoved);
        } else {
            System.out.println("Move cannot be made! Try again");
        }
    }

    /**
     * FR5 - This function is to move the last card onto the pile after skipping over two piles in-between, like above it passes
     * chosenPile, destinationPile and cardPilesMoved through the makeMove function
     */
    private void moveBackTwo() {
        if (cardPiles > 2) {
            int chosenPile = (cardPiles);
            int destinationPile = (cardPiles - 3);
            int cardPilesMoved = 3;
            makeMove(chosenPile, destinationPile, cardPilesMoved);
        } else {
            System.out.println("Move cannot be made! Try again");
        }
    }

    /**
     * Function to help with FR4, FR5 and FR6.
     *  @param chosenPile      is the pile at the source, the card to be moved
     * @param destinationPile is the pile it will be moved to
     * @param cardPilesMoved  is the number of piles it will be moved by
     * @return
     */
    public boolean makeMove(int chosenPile, int destinationPile, int cardPilesMoved) {
        moveMade = false;
        try {
            String chosenCard = gameTable.get(chosenPile);
            String destinationCard = gameTable.get(destinationPile);

            Character cardValue1 = chosenCard.charAt(0);
            Character cardSuit1 = chosenCard.charAt(1);
            Character cardValue2 = destinationCard.charAt(0);
            Character cardSuit2 = destinationCard.charAt(1);
            if (cardValue1.equals(cardValue2) || cardSuit1.equals(cardSuit2)) {
                if (cardPilesMoved == 1) {
                    gameTable.remove(destinationPile);
                    cardPiles--;
                    removedCards.add(destinationCard);
                    System.out.println(("The move has been made and the current playing field is: " + gameTable).replace("[", "").replace("]", ""));
                    moveMade = true;
                } else if (cardPilesMoved == 3) {
                    gameTable.remove(destinationPile);
                    gameTable.add(destinationPile, chosenCard);
                    gameTable.remove(chosenPile);
                    removedCards.add(destinationCard);
                    cardPiles--;
                    System.out.println(("The move has been made and the current playing field is: " + gameTable).replace("[", "").replace("]", ""));
                    moveMade = true;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return moveMade;
    }

    /**
     * FR6 - This function is to accept user input for the indexes and will then call function areIndexesValid to ensure they
     * are valid and then call amalgamateCards if they are
     */
    private void amalgamateChoices() {
        scan = new Scanner(System.in);
        try {
            System.out.println("Enter the position of the card moving: ");
            int rPileChoice = Integer.parseInt(scan.next());
            rPileChoice = rPileChoice - 1;
            System.out.println("Enter the position of where the card is to be moved to: ");
            int lPileChoice = Integer.parseInt(scan.next());
            lPileChoice = lPileChoice - 1;
            if (areIndexesValid(rPileChoice, lPileChoice)) {
                amalgamateCards(rPileChoice, lPileChoice);
            }
        } catch (NumberFormatException e) {
            System.out.println("The input you have given is not an integer! Try again! ");
            amalgamateChoices();
        }
    }

    /**
     * FR6 - This checks to see if the rPileChoice is less than lPileChoice OR if lPileChoice is less than 0 OR if rPileChoice
     * is less than cardPiles
     *
     * @param rPileChoice is the index to the right
     * @param lPileChoice is the index to the left
     * @return indexValid - if the index is valid or not
     */
    private boolean areIndexesValid(int rPileChoice, int lPileChoice) {
        if (rPileChoice < lPileChoice || lPileChoice < 0 || rPileChoice > (cardPiles)) {
            System.out.println("Indexes are not valid, try again!");
            indexValid = false;
        } else {
            indexValid = true;
        }
        return indexValid;
    }

    /**
     * FR6 - Sets the conditions for makeMove, if the index difference is 1 then it will set cardPilesMoved to 1 or if it is 3
     * it will be set to 3, and then it will call the method makeMove with these arguments
     *
     * @param rPileChoice is the index to the right
     * @param lPileChoice is the index to the left
     */
    private void amalgamateCards(int rPileChoice, int lPileChoice) {
        if (rPileChoice == (lPileChoice + 1)) {
            int chosenPile = rPileChoice;
            int destinationPile = lPileChoice;
            int cardPilesMoved = 1;
            makeMove(chosenPile, destinationPile, cardPilesMoved);
        } else if (rPileChoice == (lPileChoice + 3)) {
            int chosenPile = rPileChoice;
            int destinationPile = lPileChoice;
            int cardPilesMoved = 3;
            makeMove(chosenPile, destinationPile, cardPilesMoved);
        } else {
            System.out.println("Move cannot be made, try again!");
        }
    }

    /**
     * 
     */
    private void howManyTimes(){
        System.out.println("How many times would you like me to make a move for you? ");
        if(scan.hasNextInt()){
            int AITimes = scan.nextInt();
            for(int i = 0; i < AITimes; i++){
                if (deck.shuffle == 0) {
                    System.out.println("The deck has to be shuffled before you start, this has been done automatically!");
                    deck.shuffleDeck();
                    AIPlayForMe();
                } else {
                    AIPlayForMe();
                }
                if (!AIUsed) {
                    System.out.println("Moves cannot be made, so I have drawn a card!");
                    dealOneCard();
                }
            }
        } else {
            System.out.println("This was not a valid input!");
        }
    }

    /**
     * FR8 - This is the automation of play method, the program will try to make the move which would try to skip two
     * piles first if there are 4 piles on the table, then try to move onto the previous pile if it cannot
     * make that move. If there are less than 4 piles on the table, it will try to move onto the previous pile. If it
     * cannot do any of these, it will return false and then will make the program draw a card
     * @return AIUsed
     */
    private Boolean AIPlayForMe() {
        AIUsed = false;
        if (cardPiles > 0) {
            if (!AIUsed) {
                for (int i = 0; i < cardPiles + 4; i++) {
                    if (cardPiles >= 3) {
                        AIUsed = makeMove(i + 3, i, 3);
                        if (AIUsed) {
                            break;
                        } else {
                            AIUsed = makeMove(i + 1, i, 1);
                            if (AIUsed) {
                                break;
                            }
                        }
                    }
                    if (cardPiles >= 1) {
                        AIUsed = makeMove(i + 1, i, 1);
                        if (AIUsed) {
                            break;
                        }
                    }
                }
            }
        }
        return AIUsed;
    }

    /**
     *
     */
    private void restart(){
        Game gameApp = new Game();
        gameApp.initialiseMenu();
        gameApp.initialise();
        gameApp.runMenu();
    }

    /**
     * FR10 - This is called when the user chooses to quit the program, presenting them with their score and their options
     * of what to do next.
     */
    private void quitMenu() {
        newScore = gameTable.size() + (deck.sizeOfDeck - cardsDrawn);
        System.out.println("Your score is: " + newScore);
        System.out.println("""
                Please choose an option:
                1 -  Save and quit
                2 -  Return to menu
                3 -  Play again with a new deck
                """);
    }

    /**
     * FR10 - This is where the user can choose what to do next, whether it is to save and quit, return to menu in case
     * it was selected in error or play again with a whole new deck.
     */
    private void quitGame() {
        quitMenu();
        String option = scan.next();
        switch (option) {
            case "1":
                if (customDeck == false && AIUsed == false && cardPiles != -1) {
                    leaderboard.isTopTen(newScore);
                    leaderboard.printLeaderboard();
                } else {
                    System.out.println("You cannot save your score!");
                }
                System.out.println("Thank you for playing!");
                System.exit(0);
                break;
            case "2":
                runMenu();
                break;
            case "3":
                restart();
                break;
            default:
                System.out.println("Input not accepted! Try again!");
                quitGame();
        }
    }
}
