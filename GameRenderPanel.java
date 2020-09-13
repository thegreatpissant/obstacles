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

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        //  Handle User input
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                this.gameBoard.movePlayer(Direction.UP);
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                this.gameBoard.movePlayer(Direction.DOWN);
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                this.gameBoard.movePlayer(Direction.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                this.gameBoard.movePlayer(Direction.RIGHT);
                break;
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
