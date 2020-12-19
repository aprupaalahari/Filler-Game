

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This class sets up the top-level frame and widgets for the GUI.
 * 
 * This game adheres to a Model-View-Controller design framework. This framework
 * is very effective for turn-based games. We STRONGLY recommend you review
 * these lecture slides, starting at slide 8, for more details on
 * Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 * 
 * In a Model-View-Controller framework, Game initializes the view, implements a
 * bit of controller functionality through the reset button, and then
 * instantiates a GameBoard. The GameBoard will handle the rest of the game's
 * view and controller functionality, and it will instantiate a TicTacToe object
 * to serve as the game's model.
 */
public class Game implements Runnable {
    public void run() {
        // NOTE: the 'final' keyword denotes immutability even for local variables.

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Filler");
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setLocation(300, 300);
        frame.setVisible(true);

        // Input Dialog
//        String name = JOptionPane.showInputDialog(frame, 
//                "WINNER, ENTER YOUR INITIALS");

        // Image icon = new ImageIcon()
        JOptionPane.showMessageDialog(frame,
                "The objective of Filler is to gather more tiles than your "
                + "opponent. \n"
                        + "Click on tiles to switch your collection to that color, "
                        + "and add any adjacent \n"
                        + "tiles of that same color to your collection. "
                        + "You can't switch to your own color \n"
                        + "or that of your opponent. You can 'get' "
                        + "vertically and horizontally adjacent tiles by \n"
                        + "clicking to switch to their colors. "
                        + "Player 1 starts with the bottom \n"
                        + "left corner tile, and Player 2 "
                        + "starts with the top right corner tile. \n"
                        + "Have fun and good luck!");

        // Statuses panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("PLAYER 1'S TURN");
        status_panel.add(status);

        // Scores panel
        final JPanel scores_panel = new JPanel();
        frame.add(scores_panel, BorderLayout.WEST);
        final JLabel player1Score = new JLabel(
                "<html>&nbsp Player 1:&nbsp&nbsp<br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp "
                + "1</html>");
        final JLabel player2Score = new JLabel(
                "<html>&nbsp Player 2:&nbsp&nbsp<br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp "
                + "1</html>");
        scores_panel.setLayout(new GridLayout(2, 1));
        scores_panel.add(player1Score);
        scores_panel.add(player2Score);

        // Highscore panel
        final JPanel highscore_panel = new JPanel();
        frame.add(highscore_panel, BorderLayout.EAST);
        final JLabel highscore = new JLabel("<html>&nbsp HIGH SCORES&nbsp</html>");
        final JLabel highscore1 = new JLabel("");
        final JLabel highscore2 = new JLabel("");
        final JLabel highscore3 = new JLabel("");
        highscore_panel.setLayout(new GridLayout(4, 1));
        highscore_panel.add(highscore);
        highscore_panel.add(highscore1);
        highscore_panel.add(highscore2);
        highscore_panel.add(highscore3);

        // Game board
        final GameBoard board = new GameBoard(1, status, player1Score, 
                player2Score, highscore1, highscore2, highscore3);
        board.setBackground(Color.BLACK);
        frame.add(board, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we define
        // it as an
        // anonymous inner class that is an instance of ActionListener with its
        // actionPerformed()
        // method overridden. When the button is pressed, actionPerformed() will be
        // called.
        final JButton resetLevel1 = new JButton("Level 1");
        resetLevel1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.reset(1);
            }
        });

        final JButton resetLevel2 = new JButton("Level 2");
        resetLevel2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.reset(2);
            }
        });
        control_panel.add(resetLevel1);
        control_panel.add(resetLevel2);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    /**
     * Main method run to start and run the game. Initializes the GUI elements
     * specified in Game and runs it. IMPORTANT: Do NOT delete! You MUST include
     * this in your final submission.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}