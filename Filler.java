import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

/**

 * This game adheres to a Model-View-Controller design framework. This framework
 * is very effective for turn-based games. We STRONGLY recommend you review
 * these lecture slides, starting at slide 8, for more details on
 * Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 * 
 * This model is completely independent of the view and controller. This is in
 * keeping with the concept of modularity! We can play the whole game from start
 * to finish without ever drawing anything on a screen or instantiating a Java
 * Swing object.
 * 
 * Run this file to see the main method play a game of TicTacToe, visualized
 * with Strings printed to the console.
 */
@SuppressWarnings("serial")
public class Filler extends JPanel {

    private Tile[][] tileArray;
    private List<int[]> player1Tiles;
    private List<int[]> player2Tiles;
    private String player1Color;
    private String player2Color;
    private boolean player1;
    private int player1Count;
    private int player2Count;
    private boolean gameOver;

    /**
     * Constructor sets up game state.
     */
    public Filler(int level) {
        reset(level);
    }

    /**
     * playTurn allows players to play a turn. Returns true if the move is
     * successful and false if a player tries to play their own color of the color
     * of the opponent. If the turn is successful and the game has not ended, the
     * player is changed. If the turn is unsuccessful or the game has ended, the
     * player is not changed.
     * 
     * @param c column to play in
     * @param r row to play in
     * @return whether the turn was successful
     */
    public boolean playTurn(String color) {
        boolean boolToReturn = false;
        if (isGameOver()) {
            return boolToReturn;
        }

        if (isPlayer1()) {
            if (color.equals("black")) {
                boolToReturn = false;
            } else if (player1Color.equals(color)) {
                boolToReturn = false;
            } else if (player2Color.equals(color)) {
                boolToReturn = false;
            } else {
                playTurnHelper(player1, tileArray, player1Tiles, color);
                player1Color = color;
                player1 = false;
                boolToReturn = true;
            }
        } else /* if (!player1) */ {
            if (color.equals("black")) {
                boolToReturn = false;
            } else if (player2Color.equals(color)) {
                boolToReturn = false;
            } else if (player1Color.equals(color)) {
                boolToReturn = false;
            } else {
                playTurnHelper(player1, tileArray, player2Tiles, color);
                player2Color = color;
                player1 = true;
                boolToReturn = true;
            }
        }
        if (checkWinner() == 0) {
            gameOver = false;
        }
        return boolToReturn;
    }

    /**
     * playTurnHelper goes through a player's collection of tiles, and checks the
     * horizontally and vertically neighboring of tiles. If a neighbor is the chosen
     * color and isn't already in the player's collection, it adds it.
     * 
     * @param color
     * @param playerTiles collection of the current player's tiles
     */
    public void playTurnHelper(boolean player1, Tile[][] tileArray, 
            List<int[]> playerTiles, String color) {
        List<int[]> tempList = new LinkedList<int[]>();
        for (int[] tileSpot : playerTiles) {
            int row = tileSpot[0];
            int col = tileSpot[1];
            // update the color of anything already in the collection
            tileArray[row][col].setColor(color);

            if (tileArray[row - 1][col].getColor().equals(color)) {
                if (!isInCollection(playerTiles, row - 1, col) && 
                        !isInCollection(tempList, row - 1, col)) {
                    // add the tile to player's tiles
                    if (player1) {
                        setPlayer1Count(getPlayer1Count() + 1);
                    } else {
                        setPlayer2Count(getPlayer2Count() + 1);
                    }
                    int[] tempArray = new int[2];
                    tempArray[0] = row - 1;
                    tempArray[1] = col;
                    tempList.add(tempArray);
                } 
            }
            if (tileArray[row + 1][col].getColor().equals(color)) {
                if (!isInCollection(playerTiles, row + 1, col) && 
                        !isInCollection(tempList, row + 1, col)) {
                    // add the tile to player's tiles
                    if (player1) {
                        setPlayer1Count(getPlayer1Count() + 1);
                    } else {
                        setPlayer2Count(getPlayer2Count() + 1);
                    }
                    int[] tempArray = new int[2];
                    tempArray[0] = row + 1;
                    tempArray[1] = col;
                    tempList.add(tempArray);
                } 
            }
            if (tileArray[row][col - 1].getColor().equals(color)) {
                if (!isInCollection(playerTiles, row, col - 1) && 
                        !isInCollection(tempList, row, col - 1)) {
                    // add the tile to player's tiles
                    if (player1) {
                        setPlayer1Count(getPlayer1Count() + 1);
                    } else {
                        setPlayer2Count(getPlayer2Count() + 1);
                    }
                    int[] tempArray = new int[2];
                    tempArray[0] = row;
                    tempArray[1] = col - 1;
                    tempList.add(tempArray);
                } 
            }
            if (tileArray[row][col + 1].getColor().equals(color)) {
                if (!isInCollection(playerTiles, row, col + 1) && 
                        !isInCollection(tempList, row, col + 1)) {
                    // add the tile to player's tiles
                    if (player1) {
                        setPlayer1Count(getPlayer1Count() + 1);
                    } else {
                        setPlayer2Count(getPlayer2Count() + 1);
                    }
                    int[] tempArray = new int[2];
                    tempArray[0] = row;
                    tempArray[1] = col + 1;
                    tempList.add(tempArray);
                } 
            }

        }
        playerTiles.addAll(tempList);
    }

    /**
     * checkWinner Check the number of colors on the board. Game ends when it is
     * exactly 3 (black, player1Color, player2Color)
     * 
     * @return 0 if nobody has won yet, 1 if player 1 has won, and 2 if player 2 has
     *         won, 3 if the game is a tie
     */
    public int checkWinner() {
        int resultToReturn = 0;
        List<String> listColors = new LinkedList<String>();
        for (int row = 0; row <= 9; row++) {
            for (int col = 0; col <= 9; col++) {
                String tileColor = tileArray[row][col].getColor();
                if (!listColors.contains(tileColor)) {
                    listColors.add(tileColor);
                }
            }
        }
        if (listColors.size() == 3) {
            gameOver = true;
            // indicates that the game has ended, now want to see who won (who has more
            // tiles)
            if (getPlayer1Count() > getPlayer2Count()) {
                resultToReturn = 1;
            } else if (getPlayer2Count() > getPlayer1Count()) {
                resultToReturn = 2;
            } else if (getPlayer1Count() == getPlayer2Count()) {
                resultToReturn = 3;
            }
        }
        return resultToReturn;
    }

    /**
     * checkWinner gets the score of the winner, which is how many tiles they have.
     * game has to have ended; if used when !gameOver, outputs 0;
     * 
     * @return 0 if !gameOver, some integer otherwise.
     */
    public int scoreOfWinner(int winner) {
        int score = 0;
        if (winner == 1) {
            score = getPlayer1Count();
        } else if (winner == 2) {
            score = getPlayer2Count();
        }
        return score;
    }

    /**
     * printGameState prints the current game state for debugging.
     */
//    public void printGameState() {
//        System.out.println("\n\nTurn " + numTurns + ":\n");
//        for (int i = 0; i < board.length; i++) {
//            for (int j = 0; j < board[i].length; j++) {
//                System.out.print(board[i][j]);
//                if (j < 2) { 
//                    System.out.print(" | "); 
//                }
//            }
//            if (i < 2) {
//                System.out.println("\n---------"); 
//            }
//        }
//    }

    /**
     * Random color picker
     * 
     * @return String of color
     */
    public String randomColorPicker() {
        String color = null;
        Random random = new Random();
        int number = random.nextInt(6 - 1 + 1) + 1;
        if (number == 1) {
            color = "pink";
        } else if (number == 2) {
            color = "white";
        } else if (number == 3) {
            color = "blue";
        } else if (number == 4) {
            color = "green";
        } else if (number == 5) {
            color = "yellow";
        } else if (number == 6) {
            color = "orange";
        }
        return color;
    }

    /**
     * Selects + outputs appropriate color to make sure neighboring tiles aren't the
     * same to start
     * 
     * @return String of color
     */
    public String colorChooser(Tile[][] tileArray, int row, int col) {
        String colorToChangeTo = randomColorPicker();
        if (!tileArray[row - 1][col].getColor().equals(colorToChangeTo)
                && !tileArray[row + 1][col].getColor().equals(colorToChangeTo)
                && !tileArray[row][col - 1].getColor().equals(colorToChangeTo)
                && !tileArray[row][col + 1].getColor().equals(colorToChangeTo)) {
            return colorToChangeTo;
        } else {
            return colorChooser(tileArray, row, col);
        }
    }

    /**
     * Outputs a color that differs from a specific Tile
     * 
     * @return String of color
     */
    public String differentColor(String colorNotToBe, Tile[][] tileArray, 
            int row, int col) {
        String colorToChangeTo = colorChooser(tileArray, row, col);
        if (!colorToChangeTo.equals(colorNotToBe)) {
            return colorToChangeTo;
        } else {
            return differentColor(colorNotToBe, tileArray, row, col);
        }
    }

    public void addToCollection(List<int[]> playerTiles, int row, int col) {
        int[] coordinate = new int[2];
        coordinate[0] = row;
        coordinate[1] = col;
        playerTiles.add(coordinate);
    }

    public boolean isInCollection(List<int[]> tileList, int row, int col) {
        for (int[] tileSpot : tileList) {
            int containedRow = tileSpot[0];
            int containedCol = tileSpot[1];
            if (containedRow == row && containedCol == col) {
                return true;
            }
        }
        return false;
    }

    /**
     * reset (re-)sets the game state to start a new game.
     */
    public void reset(int level) {
        player1 = true;
        setGameOver(false);
        setPlayer1Tiles(new LinkedList<int[]>());
        setPlayer2Tiles(new LinkedList<int[]>());
        player1Color = null;
        player2Color = null;
        setTileArray(new Tile[10][10]);
        setPlayer1Count(0);
        setPlayer2Count(0);
        tileArray = getTileArray();

        for (int row = 0; row <= 9; row++) {
            for (int col = 0; col <= 9; col++) {
                tileArray[row][col] = new Tile();
            }
        }

        // sets up the 2d array with colors
        if (level == 1) {
            for (int row = 1; row <= 8; row++) {
                for (int col = 1; col <= 8; col++) {
                    String colorToChangeTo = colorChooser(tileArray, row, col);
                    tileArray[row][col].setColor(colorToChangeTo);
                }
            }
            // if players currently have same color, update player 1's
            if (tileArray[8][1].getColor() == tileArray[1][8].getColor()) {
                String colorNotToBe = tileArray[1][8].getColor();
                tileArray[8][1].setColor(differentColor(colorNotToBe, tileArray, 8, 1));
            }
        } else if (level == 2) {
            for (int row = 1; row <= 8; row++) {
                for (int col = 1; col <= 8; col++) {
                    if ((row < 3 && col < 5) || (row > 6 && col > 4)) {
                        String colorToChangeTo = "black";
                        tileArray[row][col].setColor(colorToChangeTo);
                    } else {
                        String colorToChangeTo = colorChooser(tileArray, row, col);
                        tileArray[row][col].setColor(colorToChangeTo);
                    }
                }

            }
            // if players currently have same color, update player 1's
            if (tileArray[8][1].getColor() == tileArray[1][8].getColor()) {
                String colorNotToBe = tileArray[1][8].getColor();
                tileArray[8][1].setColor(differentColor(colorNotToBe, tileArray, 8, 1));
            }

        }
        setTileArray(tileArray);
        // initialize players' tile collections
        addToCollection(getPlayer1Tiles(), 8, 1);
        setPlayer1Count(getPlayer1Count() + 1);
        addToCollection(getPlayer2Tiles(), 1, 8);
        setPlayer2Count(getPlayer2Count() + 1);
        // keep track of players' colors
//        System.out.println("player 1 starts as" + tileArray[8][1].getColor());
//        System.out.println("player 2 starts as" + tileArray[1][8].getColor());
        player1Color = tileArray[8][1].getColor();
        player2Color = tileArray[1][8].getColor();

    }

    /**
     * getCell is a getter for the color of the specified cell
     * 
     * @param c column to retrieve
     * @param r row to retrieve
     * @return a String denoting color of a tile
     */
    public String getTileColor(int r, int c) {
        return getTileArray()[r][c].getColor();
    }

    public Tile[][] getTileArray() {
        return tileArray;
    }

    public void setTileArray(Tile[][] tileArray) {
        this.tileArray = tileArray;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isPlayer1() {
        return player1;
    }

    public void setPlayer1(boolean player1) {
        this.player1 = player1;
    }

    public void setPlayer1Tiles(List<int[]> player1Tiles) {
        this.player1Tiles = player1Tiles;
    }

    public List<int[]> getPlayer1Tiles() {
        return player1Tiles;
    }

    public List<int[]> getPlayer2Tiles() {
        return player2Tiles;
    }

    public void setPlayer2Tiles(List<int[]> player2Tiles) {
        this.player2Tiles = player2Tiles;
    }

    public int getPlayer1Count() {
        return player1Count;
    }

    public void setPlayer1Count(int player1Count) {
        this.player1Count = player1Count;
    }

    public int getPlayer2Count() {
        return player2Count;
    }

    public void setPlayer2Count(int player2Count) {
        this.player2Count = player2Count;
    }

    /**
     * This main method illustrates how the model is completely independent of the
     * view and controller. We can play the game from start to finish without ever
     * creating a Java Swing object.
     * 
     * This is modularity in action, and modularity is the bedrock of the
     * Model-View-Controller design framework.
     * 
     * Run this file to see the output of this method in your console.
     */
}
