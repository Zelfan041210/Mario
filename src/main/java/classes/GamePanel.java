package main.java.classes;

import main.java.state.GameState;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GamePanel extends JPanel {
    private GameState gameState = GameState.MENU;
    private Mario mario;
    private Menu menu;

    public GamePanel(Mario mario, Menu menu) {
        this.mario = mario;
        setDoubleBuffered(true);
        this.menu = menu;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // 清屏
        if(gameState == GameState.MENU){
            menu.draw_menubackground(g,true);
            try {
                menu.draw_menu(g);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if(gameState == GameState.PLAY){
            //draw mario
            mario.draw(g);
        } else if(gameState == GameState.CHOOSELEVEL){
            menu.draw_menubackground(g,false);
            menu.drawLevelChooser(g);
        }
    }
    public void setGameState(GameState gameState){
        this.gameState = gameState;
        repaint();
    }

    public GameState getGameState(){
        return this.gameState;
    }
}
