/**
 * File: GameRenderPanel.java
 * Author: Delta Group
 * Purpose: The panel that will host the game world.  Provides the render context and input context.
 *
 * DELTA GROUP Obstacle Course
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class GameRenderPanel extends JPanel implements KeyListener {

    //  Reference to the gameWorld
    GameBoard gameBoard;

    //  Constructor
    public GameRenderPanel(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    //  Render the GameWorld Panel
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        //  default to show any misses in rendering.
        graphics.setColor(Color.PINK);

        //  Make sure there is something to render.
        if (this.gameBoard == null) {
            return;
        }

        this.gameBoard.draw(graphics);
    }


    @Override
    public void keyTyped(KeyEvent keyEvent) {
        //  Handle User input
        switch (keyEvent.getKeyChar()) {
            case 'w':
                this.gameBoard.movePlayer(Direction.UP);
                break;
            case 'd':
                this.gameBoard.movePlayer(Direction.RIGHT);
                break;
            case 's':
                this.gameBoard.movePlayer(Direction.DOWN);
                break;
            case 'a':
                this.gameBoard.movePlayer(Direction.LEFT);
                break;
            default:
                System.out.println("Keypresse = " + keyEvent.getKeyChar());
                break;
        }
        this.repaint();
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
