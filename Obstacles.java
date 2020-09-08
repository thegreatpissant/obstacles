/**
 * File: Obstacles.java
 * Author: Delta Group
 * Purpose: Obstacle course game.
 *
 * DELTA GROUP Obstacle Course
 */
import javax.swing.*;
import java.awt.*;

/**
 * The Obstacles class is the main application frame.
 */
public class Obstacles extends JFrame {
    //  Application Defaults
    public static String AppName = "Obstacles";
    public static final int APP_WIDTH = 800, APP_HEIGHT = 800;

    //  Display scale.
    public static double BOARD_SCALE = 1.;

    //  Game World
    public GameBoard gameBoard = new GameBoard();

    //  Constructor
    public Obstacles () {
        super(AppName);

        //  Setup the application display
        setSize(APP_WIDTH, APP_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //  Default Layout
        this.setLayout(new BorderLayout());

        //  Game Panel to draw
        GameRenderPanel gameRenderPanel = new GameRenderPanel(this.gameBoard);

        this.add(gameRenderPanel, BorderLayout.CENTER);
        this.addKeyListener(gameRenderPanel);

        gameRenderPanel.repaint();

        //  Display the frame
        this.setVisible(true);
    }

    //  Main Entry Point.
    static public void main (String[] args) {
        new Obstacles();
    }
}
