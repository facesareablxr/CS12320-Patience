package uk.ac.aber.dcs.cs12320.cards;

/**
 * This is the class where the whole program is run from
 */
public class GameApp {

    /**
     * This method is where the program will create instances of Game and the menus
     * @param args standard parameter
     */
    public static void main(String[] args) {
        Game gameApp = new Game();
        gameApp.initialiseMenu();
        gameApp.initialise();
        gameApp.runMenu();
    }
}