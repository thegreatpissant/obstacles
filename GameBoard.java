/**
 * File: GameBoard.java
 * Author: Delta Group
 * Purpose: The Obstacle Course GameBoard.  Runs all the logic for the game.
 *
 * DELTA GROUP Obstacle Course
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * Direction the player is moving
 */
enum Direction {
    UP, RIGHT, DOWN, LEFT
}

/**
 * The Game Board that represents the maze.
 */
public class GameBoard extends JPanel implements KeyListener, ComponentListener {

    //  Tokens
    final char PLAYER_TOKEN = 'x';
    final char END_GOAL_TOKEN = '*';
    final char OPEN_TOKEN = '.';
    final char OBSTACLE_TOKEN = 'o';
    final char HAZARD_TOKEN = 'v';
    final char RESET_PLAYER_TOKEN = 'r';

    //  GameBoard Cell Dimensions
    int cellHeight;
    int cellWidth;

    //  Board Width in cells
    int boardWidth;
    //  Board Height in cells
    int boardHeight;

    //  Player position
    Point playerPosition = new Point();
    //  Player Sprite
    //  @@TODO: add player sprite

    //  Start position
    Point startPosition = new Point();
    //  Goal position
    Point goalPosition = new Point();
    //  Goal Sprite
    //  @@TODO: add goal sprite

    //  Locations of hazards
    ArrayList<Point> hazardPositions = new ArrayList<>();
    //  Locations of obstacles
    ArrayList<Point> obstaclePositions = new ArrayList<>();
    //  Location of reset Positions
    ArrayList<Point> resetPositions = new ArrayList<>();

    String[] defaultBoardOne = {
            "*..........",
            "...........",
            "...........",
            "...........",
            "...o.......",
            "...........",
            "...........",
            "...........",
            "...........",
            "..........x",
    };
    String[] defaultBoardTwo = {
            "..........x",
            "...........",
            "...r.......",
            "...........",
            "...........",
            "...........",
            "...........",
            "...........",
            "...........",
            "*..........",
    };
    String[] defaultBoardThree = {
            "...........",
            "........v..",
            "...........",
            "...........",
            "....x......",
            "...........",
            "...........",
            "...........",
            "...........",
            "..........*",
    };
    String[][] boards = {defaultBoardOne, defaultBoardTwo, defaultBoardThree};

    //  Current board the player is on.
    int currentBoardIndex;

    //  Temp player position, work on this player position before committing.
    private Point tempPlayerPosition;

    //  Constructor
    public GameBoard() {
        //  To handle resize events.
        this.addComponentListener(this);

        //  Reset the game to initial state
        this.resetGame();

        //  Load the game.
        this.loadGameBoard();
    }

    private void resetGame() {
        //  Set the default board
        this.currentBoardIndex = 0;
    }
    //  Load Game Board
    private void loadGameBoard() {
        if (this.currentBoardIndex >= this.boards.length) {
            System.out.println("Board #" + this.currentBoardIndex + " is invalid, max boards is: " + this.boards.length);
            this.currentBoardIndex = this.boards.length - 1;
        }
        String[] gameBoard = this.boards[this.currentBoardIndex];
        //  Set the board as the passed in gameBoard
        String[] board = gameBoard;

        //  Set the board Dimensions in cells
        this.boardHeight = gameBoard.length;
        this.boardWidth = gameBoard[0].length();

        //  Set the cell size
        this.updateCellSize();

        //  Clear any static objects
        hazardPositions.clear();
        obstaclePositions.clear();
        resetPositions.clear();

        //  Scan the board
        Point scannedPlayerPosition = new Point();
        Point scannedGoalPosition = new Point();
        boolean playerPositionSet = false;
        boolean goalPositionSet = false;
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length(); x++) {
                switch (board[y].charAt(x)) {
                    case PLAYER_TOKEN:
                            scannedPlayerPosition = new Point(x, y);
                            playerPositionSet = true;
                        break;
                    case END_GOAL_TOKEN:
                            scannedGoalPosition = new Point(x, y);
                            goalPositionSet = true;
                        break;
                    case OBSTACLE_TOKEN:
                        this.obstaclePositions.add(new Point(x, y));
                        break;
                    case RESET_PLAYER_TOKEN:
                        this.resetPositions.add(new Point(x, y));
                        break;
                    case HAZARD_TOKEN:
                        this.hazardPositions.add(new Point(x, y));
                        break;
                    case OPEN_TOKEN:
                        break;
                    default:
                        System.out.println("Map Token type: \"" + board[y].charAt(x) + "\" not recognized.");
                        break;
                }
            }
        }
        if (!playerPositionSet) {
            System.out.println("MAP Player position not found");
        }
        this.playerPosition = scannedPlayerPosition;
        this.startPosition = scannedPlayerPosition;
        if (!goalPositionSet) {
            System.out.println("MAP Goal position not found");
        }
        this.goalPosition = scannedGoalPosition;
    }

    //  Draw a tile
    private void drawTile(Graphics graphics, Point point) {
        graphics.fillRect(point.x * cellWidth,point.y * cellHeight,cellWidth,cellHeight );
    }

    //  Draw the player
    private void drawPlayer(Graphics graphics) {
        graphics.setColor(Color.black);
        this.drawTile(graphics, this.playerPosition);
    }

    //  Draw the player
    private void drawGoal(Graphics graphics) {
        graphics.setColor(Color.ORANGE);
        this.drawTile(graphics, this.goalPosition);
    }

    //  Draw the player
    private void drawHazards(Graphics graphics) {
        graphics.setColor(Color.RED);
        for ( Point point : this.hazardPositions) {
            this.drawTile(graphics, point);
        }
    }

    //  Draw the player
    private void drawResets(Graphics graphics) {
        graphics.setColor(Color.GREEN);
        for ( Point point : this.resetPositions) {
            this.drawTile(graphics, point);
        }
    }

    //  Draw the player
    private void drawObstacles(Graphics graphics) {
        graphics.setColor(Color.BLUE);
        for ( Point point : this.obstaclePositions) {
            this.drawTile(graphics, point);
        }
    }
    //  Draw the world
    public void draw(Graphics graphics) {
        //  Draw the board
        //  @@TODO Draw all the things.

        //  Draw the static map objects
        this.drawGoal(graphics);
        this.drawObstacles(graphics);
        this.drawHazards(graphics);
        this.drawResets(graphics);

        //  Draw the player
        this.drawPlayer(graphics);
    }

    //  Update the size of a cell
    private void updateCellSize() {
        //  Scale the cell dimensions
        cellHeight = this.getHeight() / (this.boardHeight+1);
        cellWidth = this.getWidth() / (this.boardWidth+1);
    }

    //  Move player
    public void movePlayer(Direction direction) {
        //  Work with a temp version of the player position.
        this.tempPlayerPosition = new Point(playerPosition);
        switch (direction) {
            case RIGHT:
                this.tempPlayerPosition.x += 1;
                break;
            case LEFT:
                this.tempPlayerPosition.x -= 1;
                break;
            case UP:
                this.tempPlayerPosition.y -= 1;
                break;
            case DOWN:
                this.tempPlayerPosition.y += 1;
                break;
        }
    }
    private void checkPlayerCollisions() {
        //  Is the player in board bounds?
        if (this.tempPlayerPosition.x >= 0 && this.tempPlayerPosition.x <= boardWidth &&
                this.tempPlayerPosition.y >= 0 && this.tempPlayerPosition.y <= boardHeight) {

            //  Did the player collide with an obstacle?
            //  - Update position
            //  - ??Update Health??
            //  - Teleport the player

            //  Update the player position.
            this.playerPosition = this.tempPlayerPosition;
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

        //  Check player collisions
        this.checkPlayerCollisions();

        //  repaint board
        this.repaint();

        //  Check player win condition
        if (beatLevel()) {
            //  Yes, Increment to the next board.
            this.currentBoardIndex++;

            //  Did they also beat the game?
            if (this.beatGame()) {
                //  Yes, tell them they won and set the next board to 0
                JOptionPane.showMessageDialog(this,"You beat the Game!!\n Restarting at level 1");
                this.resetGame();
            }
            else {
                JOptionPane.showMessageDialog(this,"You beat the board!!");
                //  No, load the next board.
            }
            this.loadGameBoard();
        }
        this.repaint();
    }

    private boolean beatGame() {
        return this.currentBoardIndex >= this.boards.length;
    }

    private boolean beatLevel() {
        return this.playerPosition.equals(this.goalPosition);
    }
    @Override
    public void componentResized(ComponentEvent componentEvent) {
        this.updateCellSize();
        this.repaint();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) { }

    @Override
    public void keyReleased(KeyEvent keyEvent) { }

    @Override
    public void componentMoved(ComponentEvent componentEvent) { }

    @Override
    public void componentShown(ComponentEvent componentEvent) { }

    @Override
    public void componentHidden(ComponentEvent componentEvent) { }
}
