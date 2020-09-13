/**
 * File: GameBoard.java
 * Author: Delta Group
 * Purpose: The Obstacle Course GameBoard.  Runs all the logic for the game.
 *
 * DELTA GROUP Obstacle Course
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Direction the player is moving
 */
enum Direction {
    UP, RIGHT, DOWN, LEFT
}

/**
 * The Game Board that represents the maze.
 */
public class GameBoard extends JPanel implements KeyListener {

    //  Tokens
    static final char PLAYER_TOKEN = 'x';
    static final char END_TOKEN = '*';
    static final char OPEN_TOKEN = '.';
    static final char OBSTACLE_TOKEN = 'o';

    //  GameBoard Cell Dimensions
    static int cellHeight;
    static int cellWidth;

    //  Board Width in cells
    static int boardWidth;
    //  Board Height in cells
    static int boardHeight;

    //  Player position
    Point playerPosition = new Point();
    //  Player Sprite
    //  @@TODO: add player sprite

    //  Goal position
    Point goalPosition = new Point();
    //  Goal Sprite
    //  @@TODO: add goal sprite


    //  Board
    String[] board;

    String[] defaultBoard = {
            "...........",
            "...........",
            "...........",
            "...........",
            "....x......",
            "...........",
            "...........",
            "...........",
            "...........",
            "..........*",
    };

    //  Render Panel

    //  Constructor
    public GameBoard() {
        loadGameBoard(defaultBoard);
    }

    //  Load Game Board
    private void loadGameBoard(String[] gameBoard) {
        //  Set the board as the passed in gameBoard
        this.board = gameBoard;

        //  Set the board Dimensions in cells
        boardHeight = gameBoard.length;
        boardWidth = gameBoard[0].length();

        //  Scale the cell dimensions
        cellHeight = Obstacles.APP_HEIGHT / (GameBoard.boardHeight+1);
        cellWidth = Obstacles.APP_WIDTH / (GameBoard.boardWidth+1);

        //  Scan the board
        Point tempPlayerPosition = new Point();
        Point tempGoalPosition = new Point();
        boolean playerPositionSet = false;
        boolean goalPositionSet = false;
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length(); x++) {
                switch (board[y].charAt(x)) {
                    case PLAYER_TOKEN:
                            tempPlayerPosition = new Point(x, y);
                            playerPositionSet = true;
                        break;
                    case END_TOKEN:
                            tempGoalPosition = new Point(x, y);
                            goalPositionSet = true;
                        break;
                    case OBSTACLE_TOKEN:
                    default:
                            break;

                }
            }
        }
        if (!playerPositionSet) {
            System.out.println("MAP Player position not found");
        }
        this.playerPosition = tempPlayerPosition;
        if (!goalPositionSet) {
            System.out.println("MAP Goal position not found");
        }
        this.goalPosition = tempGoalPosition;
    }

    //  Draw the player
    private void drawPlayer(Graphics graphics) {
        graphics.setColor(Color.black);
        graphics.fillRect(
                (int)(this.playerPosition.x * cellWidth * Obstacles.BOARD_SCALE),
                (int)(this.playerPosition.y * cellHeight * Obstacles.BOARD_SCALE),
                (int)(cellWidth * Obstacles.BOARD_SCALE),
                (int)(cellHeight * Obstacles.BOARD_SCALE));

//        BufferedImage img = null;
//        try {
//            img = ImageIO.read(new File("Cylinder.png"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        graphics.drawImage(img,
//                (int)(this.playerPosition.x * Obstacles.BOARD_SCALE),
//                (int)(this.playerPosition.y * Obstacles.BOARD_SCALE),
//                (int)(cellDimension * Obstacles.BOARD_SCALE),
//                (int)(cellDimension * Obstacles.BOARD_SCALE),
//                Color.LIGHT_GRAY, null);
    }

    //  Draw the player
    private void drawGoal(Graphics graphics) {
        graphics.setColor(Color.ORANGE);
        graphics.fillRect(
                (int)(this.goalPosition.x * cellWidth * Obstacles.BOARD_SCALE),
                (int)(this.goalPosition.y * cellHeight * Obstacles.BOARD_SCALE),
                (int)(cellWidth * Obstacles.BOARD_SCALE),
                (int)(cellHeight * Obstacles.BOARD_SCALE));

//        BufferedImage img = null;
//        try {
//            img = ImageIO.read(new File("Cylinder.png"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        graphics.drawImage(img,
//                (int)(this.playerPosition.x * Obstacles.BOARD_SCALE),
//                (int)(this.playerPosition.y * Obstacles.BOARD_SCALE),
//                (int)(cellDimension * Obstacles.BOARD_SCALE),
//                (int)(cellDimension * Obstacles.BOARD_SCALE),
//                Color.LIGHT_GRAY, null);
    }



    //  Draw the world
    public void draw(Graphics graphics) {
        //  Draw the board
        //  @@TODO Draw all the things.

        //  Draw the goal, should be one of the things.
        this.drawGoal(graphics);

        //  Draw the player
        this.drawPlayer(graphics);
    }

    //  Move player
    public void movePlayer(Direction direction) {
        //  Work with a temp version of the player position.
        Point tempPlayerPosition = new Point(playerPosition);
        switch(direction) {
            case RIGHT:
                tempPlayerPosition.x += 1;
                break;
            case LEFT:
                tempPlayerPosition.x -= 1;
                break;
            case UP:
                tempPlayerPosition.y -= 1;
                break;
            case DOWN:
                tempPlayerPosition.y += 1;
                break;
        }

        //  Is the player in board bounds?
        if (tempPlayerPosition.x >= 0 && tempPlayerPosition.x <= boardWidth &&
                tempPlayerPosition.y >= 0 && tempPlayerPosition.y <= boardHeight) {

            //  Did the player collide with an obstacle?
            //  - Update position
            //  - Update Health
            //  - Teleport the player

            //  Update the player position.
            playerPosition = tempPlayerPosition;

            if (playerPosition == this.goalPosition) {
                JOptionPane.showInputDialog(null,"You have Won!", null);
            }
        }
    }

    //  Draw the panel
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        //  default to show any misses in rendering.
        graphics.setColor(Color.PINK);

        this.draw(graphics);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        //  Handle User input
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                this.movePlayer(Direction.UP);
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                this.movePlayer(Direction.DOWN);
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                this.movePlayer(Direction.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                this.movePlayer(Direction.RIGHT);
                break;
            case KeyEvent.VK_ESCAPE:
            case KeyEvent.VK_Q:
                System.exit(0);
            default:
                System.out.println("KeyPress = " + keyEvent.getKeyChar());
                break;
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
