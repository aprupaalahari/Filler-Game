
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

/**
 * This class instantiates a TicTacToe object, which is the model for the game.
 * As the user clicks the game board, the model is updated. Whenever the model
 * is updated, the game board repaints itself and updates its status JLabel to
 * reflect the current state of the model.
 * 
 * This game adheres to a Model-View-Controller design framework. This framework
 * is very effective for turn-based games. We STRONGLY recommend you review
 * these lecture slides, starting at slide 8, for more details on
 * Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 * 
 * In a Model-View-Controller framework, GameBoard stores the model as a field
 * and acts as both the controller (with a MouseListener) and the view (with its
 * paintComponent method and the status JLabel).
 */
@SuppressWarnings("serial")
public class GameBoard extends JPanel {
    private Filler f; // model for the game
    private JLabel status; // current status text
    private JLabel player1Score;
    private JLabel player2Score;
    private JLabel highScore1;
    private JLabel highScore2;
    private JLabel highScore3;
    private ArrayList<String> leaderboardNames;
    private ArrayList<Integer> leaderboardScores;
    private Tile[][] tileArray;

    // Game constants
    public static final int BOARD_WIDTH = 300;
    public static final int BOARD_HEIGHT = 300;

    public GameBoard(int level, JLabel statusInit, JLabel player1Score, 
            JLabel player2Score, JLabel highScore1, JLabel highScore2, JLabel highScore3) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.f = new Filler(level);
        this.tileArray = f.getTileArray();

        // Enable keyboard focus on the court area.
        // When this component has the keyboard focus, key events are handled by its key
        // listener.
        setFocusable(true);

        status = statusInit;

        this.player1Score = player1Score;
        this.player2Score = player2Score;

        this.highScore1 = highScore1;
        this.highScore2 = highScore2;
        this.highScore3 = highScore3;

        // add anything from the leaderboard.txt to the actual leaderboard
        addToLeaderboard();
        System.out.println(leaderboardNames);

        if (!(leaderboardNames == null)) {
            // set high scores to JPanels
            String[] highscoresArray = readHighScores();
            if (highscoresArray[0] == null) {
                highScore1.setText("<html> &nbsp&nbsp&nbsp " + "");
            } else {
                highScore1.setText("<html> &nbsp&nbsp&nbsp " + highscoresArray[0]);
            }
            if (highscoresArray[1] == null) {
                highScore2.setText("<html> &nbsp&nbsp&nbsp " + "");
            } else {
                highScore2.setText("<html> &nbsp&nbsp&nbsp " + highscoresArray[1]);
            }
            if (highscoresArray[2] == null) {
                highScore3.setText("<html> &nbsp&nbsp&nbsp " + "");
            } else {
                highScore3.setText("<html> &nbsp&nbsp&nbsp " + highscoresArray[2]);
            }
        } else {
            String[] highscoresArray = readHighScores();
            if (highscoresArray[0] == null) {
                highScore1.setText("<html> &nbsp&nbsp&nbsp " + "");
            } else {
                highScore1.setText("<html> &nbsp&nbsp&nbsp " + highscoresArray[0]);
            }
            if (highscoresArray[1] == null) {
                highScore2.setText("<html> &nbsp&nbsp&nbsp " + "");
            } else {
                highScore2.setText("<html> &nbsp&nbsp&nbsp " + highscoresArray[1]);
            }
            if (highscoresArray[2] == null) {
                highScore3.setText("<html> &nbsp&nbsp&nbsp " + "");
            } else {
                highScore3.setText("<html> &nbsp&nbsp&nbsp " + highscoresArray[2]);
            }
        }

        int h = 10;
        int w = 10;

        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                Tile t = tileArray[row][col];
                t.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String tileColor = t.getColor();
                        f.playTurn(tileColor);
                        updateStatus(); // updates the status JLabel
                        repaint(); // repaints the game board
                    }
                });
            }
        }

        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                this.add(tileArray[row][col]);
            }
        }
        setLayout(new GridLayout(10, 10));
    }

    /**
     * (Re-)sets the game to a new state.
     */
    public void reset(int level) {

        removeAll();
        this.f = new Filler(level);
        this.tileArray = f.getTileArray();
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setFocusable(true);
        status.setText("PLAYER 1'S TURN");
        player1Score.setText("Player 1: 1");
        player2Score.setText("Player 2: 1");
        if (!(leaderboardNames == null)) {
            // set high scores to JPanels
            String[] highscoresArray = readHighScores();
            highScore1.setText("<html> &nbsp&nbsp&nbsp " + highscoresArray[0]);
            highScore2.setText("<html> &nbsp&nbsp&nbsp " + highscoresArray[1]);
            highScore3.setText("<html> &nbsp&nbsp&nbsp " + highscoresArray[2]);
        }

        // System.out.println(tileArray[6][1].getColor());
        int h = 10;
        int w = 10;

        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                Tile t = tileArray[row][col];
                t.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String tileColor = t.getColor();
                        f.playTurn(tileColor);
                        revalidate();
                        repaint(); // repaints the game board
                        updateStatus(); // updates the status JLabel
                    }
                });
            }
        }
        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                this.add(tileArray[row][col]);
            }
        }
        revalidate();
        repaint(); // repaints the game board
        updateStatus(); // updates the status JLabel
        setLayout(new GridLayout(10, 10));

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        if (f.isPlayer1()) {
            status.setText("PLAYER 1'S TURN");
        } else {
            status.setText("PLAYER 2'S TURN");
        }
        String player1Count = Integer.toString(f.getPlayer1Count());
        player1Score.setText(
                "<html>&nbsp Player 1:&nbsp&nbsp<br> &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp " + player1Count + "</html>");
        String player2Count = Integer.toString(f.getPlayer2Count());
        player2Score.setText(
                "<html>&nbsp Player 2:&nbsp&nbsp<br> &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp " + player2Count + "</html>");

        int winner = f.checkWinner();
        if (winner == 0) {
            // no one has won yet, game continues
        } else {
            // game is over, someone has won
            int score = 0;
            if (winner == 1) {
                status.setText("PLAYER 1 WINS");
                score = f.getPlayer1Count();
                // update the high scores file
                updateLeaderboard(score);
//                System.out.println(leaderboardNames);
//                System.out.println(leaderboardScores);
            } else if (winner == 2) {
                status.setText("PLAYER 2 WINS");
                score = f.getPlayer2Count();
                // update the high scores file
                updateLeaderboard(score);
//                System.out.println(leaderboardNames);
//                System.out.println(leaderboardScores);
            } else if (winner == 3) {
                status.setText("IT'S A TIE");
            }
            if (!(leaderboardNames == null)) {
                // set high scores to JPanels
                String[] highscoresArray = readHighScores();
                if (highscoresArray[0] == null) {
                    highScore1.setText("<html> &nbsp&nbsp&nbsp " + "");
                } else {
                    highScore1.setText("<html> &nbsp&nbsp&nbsp " + highscoresArray[0]);
                }
                if (highscoresArray[1] == null) {
                    highScore2.setText("<html> &nbsp&nbsp&nbsp " + "");
                } else {
                    highScore2.setText("<html> &nbsp&nbsp&nbsp " + highscoresArray[1]);
                }
                if (highscoresArray[2] == null) {
                    highScore3.setText("<html> &nbsp&nbsp&nbsp " + "");
                } else {
                    highScore3.setText("<html> &nbsp&nbsp&nbsp " + highscoresArray[2]);
                }
            }
        }
    }

    /**
     * To be called after a game has ended. Adds winning player's name and score
     */
    public void updateLeaderboard(int winningScore) {
        // update scores and users
        String name = JOptionPane.showInputDialog("WINNER, ENTER YOUR INITIALS");
        boolean bool = (leaderboardScores == null);
        if (bool || leaderboardNames.isEmpty()) {
            leaderboardNames = new ArrayList<String>();
            leaderboardScores = new ArrayList<Integer>();
            leaderboardNames.add(name);
            leaderboardScores.add(winningScore);
            writeHighScore();

        } else {
            if (leaderboardScores.size() >= 3) {
                boolean added = false;
                int leaderSize = leaderboardScores.size();
                for (int n = 0; n < leaderSize && n < 5; n++) {
                    if (winningScore > leaderboardScores.get(n)) {
                        if (!added) {
                            leaderboardScores.add(n, winningScore);
                            leaderboardNames.add(n, name);
                            added = true;
                            if (leaderboardScores.size() > 5) {
                                leaderboardScores.remove(leaderboardScores.size() - 1);
                                leaderboardNames.remove(leaderboardNames.size() - 1);
                            }
                        }
                    }
                }
            } else {
                boolean smaller = true;
                int leaderSize = leaderboardScores.size();
                for (int n = 0; n < leaderSize; n++) {
                    if (winningScore > leaderboardScores.get(n)) {
                        smaller = false;
                        leaderboardScores.add(n, winningScore);
                        leaderboardNames.add(n, name);
                    }
                }
                if (smaller) {
                    leaderboardScores.add(winningScore);
                    leaderboardNames.add(name);
                }
            }
            writeHighScore();
        }
    }

    /**
     * Used in updateLeaderboard to write to file
     */
    private void writeHighScore() {
        File leaderboardFile = new File("leaderboard.txt");
        if (!leaderboardFile.exists()) {
            try {
                leaderboardFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileWriter fileWriter = null;
        BufferedWriter buffWriter = null;

        try {
            fileWriter = new FileWriter("leaderboard.txt");
            buffWriter = new BufferedWriter(fileWriter);
            for (int n = 0; n < 5 && n < leaderboardScores.size(); n++) {
                buffWriter.write(leaderboardNames.get(n) + ": "
            + Integer.toString(leaderboardScores.get(n)));
                buffWriter.newLine();
            }
            buffWriter.close();
        } catch (IOException e) {
            // nothing
        }
    }

    public void addToLeaderboard() {
        FileReader fileReader = null;
        BufferedReader buffReader = null;
        leaderboardNames = new ArrayList<String>();
        leaderboardScores = new ArrayList<Integer>();
        File leaderboardFile = new File("leaderboard.txt");
        if (!leaderboardFile.exists()) {
            try {
                leaderboardFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            fileReader = new FileReader("leaderboard.txt");
            buffReader = new BufferedReader(fileReader);

            for (int n = 0; n < 5; n++) {
                if (buffReader.ready()) {
                    String x = buffReader.readLine();
                    String[] xs = x.split(": ");
                    leaderboardNames.add(xs[0]);
                    leaderboardScores.add(Integer.parseInt(xs[1]));
                }
            }
        } catch (Exception e) {
            // do nothing
        } finally {
            try {
                buffReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Used in updateLeaderboard to read file
     */
    private String[] readHighScores() {
        FileReader fileReader = null;
        BufferedReader buffReader = null;
        String[] highscoresArray = new String[5];
        try {
            fileReader = new FileReader("leaderboard.txt");
            buffReader = new BufferedReader(fileReader);

            for (int n = 0; n < 5; n++) {
                if (buffReader.ready()) {
                    highscoresArray[n] = buffReader.readLine();
                } else {
                    highscoresArray[n] = "";
                }
            }
        } catch (Exception e) {
            // do nothing
        } finally {
            try {
                buffReader.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return highscoresArray;
    }

    /**
     * Draws the game board.
     * 
     * There are many ways to draw a game board. This approach will not be
     * sufficient for most games, because it is not modular. All of the logic for
     * drawing the game board is in this method, and it does not take advantage of
     * helper methods. Consider breaking up your paintComponent logic into multiple
     * methods or classes, like Mushroom of Doom.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Tile[][] tileArray = f.getTileArray();
        int h = 10;
        int w = 10;
        for (int row = 0; row < w; row++) {
            for (int col = 0; col < h; col++) {
                // tileArray[row][col].removeAll();
                // tileArray[row][col].repaint();
            }
        }
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }

    public Tile[][] getTileArray() {
        return tileArray;
    }

    public void setTileArray(Tile[][] tileArray) {
        this.tileArray = tileArray;
    }

}