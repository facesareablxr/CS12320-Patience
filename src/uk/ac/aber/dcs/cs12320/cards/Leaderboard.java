package uk.ac.aber.dcs.cs12320.cards;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Leaderboard {
    private static ArrayList<Scores> players = new ArrayList<>();
    private static int LBLength = 10;

    /**
     * This is where the leaderboard scores are read from file, it will then append them to a list
     * @param fileName is the name of the file
     */
    public Leaderboard(File fileName) {
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            Scanner scoreFile = new Scanner(br);
            for (int i = 0; i < LBLength; i++) {
                String nameOfPlayer = scoreFile.next();
                int playerScore = scoreFile.nextInt();
                Scores newScore = new Scores(nameOfPlayer, playerScore);
                players.add(newScore);
            }
            scoreFile.close();

        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        }
    }

    public void addScore(Scores scores) {
        players.add(scores);
    }


    /**
     * This prints the leaderboard in order of lowest to the highest score
     */

    public static void printLeaderboard() {
        System.out.println("Top 10 Scores: ");
        Collections.sort(players, Collections.reverseOrder());
        for (Scores s : players){
            System.out.println("" + s.getName() + " " + s.getScore());
        }
    }

    /**
     * This checks if the score is in the top ten and then tells the player they can add their score to the leaderboard
     * @param newScore is the score of the player
     */
    public void isTopTen(int newScore) {
        for (int i = 0; i < LBLength; i++) {
            Scores s1 = players.get(i);
            if (newScore < s1.getScore()) {
                String input;
                System.out.println("You are in the top 10! Would you like to save your score? Y/N");
                Scanner scan = new Scanner(System.in);
                input = scan.nextLine().toUpperCase();
                switch (input) {
                    case "Y":
                        System.out.println("Please enter your name: ");
                        String name = scan.nextLine();
                        Scores newScores = new Scores(name, newScore);
                        players.add(newScores);
                        players.remove(LBLength - 1);
                        save(Game.scoreFile);
                        break;
                    case "N":
                        break;
                    default:
                        System.out.println("Y/N only!");
                }
            }
        }
    }

    /**
     * This saves the score to the file
     * @param scoreFile is the file it is saving to
     */
    public void save(File scoreFile) {
        try (FileWriter fw = new FileWriter(scoreFile);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter scoreFiles = new PrintWriter(bw)) {
            for (Scores s : players) {
                scoreFiles.println(s.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

