package com.sxt;

import classes.Mario;
import classes.GamePanel;
import classes.Menu;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class MyFrame extends JFrame implements KeyListener {

    private Mario mario;
    private GamePanel panel;
    private Menu menu;
    private boolean inChoosingLevel;
    private GameState gameState;
    private int state;

    public MyFrame() throws IOException {
        //set window size as 800*600
        this.setSize(640, 480);
        //set the window in the center
        this.setLocationRelativeTo(null);
        //set the visibility of the window
        this.setVisible(true);
        //set the close button to end the game
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //set the window size unchangeable
        setResizable(false);
        //add keyboard monitoring to th window object
        this.addKeyListener(this);
        //set the name of the window
        this.setTitle("Mario");



        mario = new Mario(2*32,12*32);
        menu = new Menu();
        panel = new GamePanel(mario,menu);
        this.setContentPane(panel);

        Timer timer = new Timer(100, e -> {
            mario.update();
            repaint();
        });
        timer.start();

        this.setVisible(true);

    }

    public static void main(String[] args) throws IOException {
        MyFrame myFrame = new MyFrame();

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        // 先根据大状态（游戏状态）来分支
        if (panel.getGameState() == GameState.MENU) {
            // 菜单状态下的逻辑
            if (!menu.getInChoosingLevel()) {
                // 在主菜单
                if (menu.getstate() == 0 && code == KeyEvent.VK_ENTER) {
                    menu.setInChoosingLevel(true);
                    panel.setGameState(GameState.CHOOSELEVEL);
                } else if (menu.getstate() == 1 && code == KeyEvent.VK_ENTER) {
                    panel.setGameState(GameState.SETTING);
                } else if (menu.getstate() == 2 && code == KeyEvent.VK_ENTER) {
                    System.exit(0);
                } else if(code == KeyEvent.VK_UP){
                    if(menu.getstate() == 0){
                        menu.setState(2);
                    }else if(menu.getstate() == 1){
                        menu.setState(0);
                    } else if(menu.getstate() == 2){
                        menu.setState(1);
                    }
                } else if(code == KeyEvent.VK_DOWN){
                    if(menu.getstate() == 0){
                        menu.setState(1);
                    } else if(menu.getstate() == 1){
                        menu.setState(2);
                    }  else if(menu.getstate() == 2){
                        menu.setState(0);
                    }
                }
            }
            if (!menu.getInChoosingLevel() && code == KeyEvent.VK_ESCAPE) {
                System.exit(0);
            }
        } else if (panel.getGameState() == GameState.PLAY) {
            // 游戏状态下的逻辑
            if (code == KeyEvent.VK_LEFT) {
                mario.setFacingleft(true);
                mario.moveLeft();
            } else if (code == KeyEvent.VK_RIGHT) {
                mario.setFacingleft(false);
                mario.moveRight();
            } else if(code == KeyEvent.VK_ESCAPE) {
                panel.setGameState(GameState.PAUSE);
            }
        } else if (panel.getGameState() == GameState.CHOOSELEVEL) {
            // 在选关界面
            if (code == KeyEvent.VK_ESCAPE) {
                menu.setInChoosingLevel(false);
                panel.setGameState(GameState.MENU);
            } else if(code ==KeyEvent.VK_RIGHT){
                if(menu.getCurrenSelectedLevel()==1){
                    menu.setCurrenSelectedLevel(2);
                }else {
                    menu.setCurrenSelectedLevel(1);
                }
            } else if(code == KeyEvent.VK_LEFT){
                if(menu.getCurrenSelectedLevel()==2){
                    menu.setCurrenSelectedLevel(1);
                }else{
                    menu.setCurrenSelectedLevel(2);
                }
            } else if(code ==KeyEvent.VK_ENTER){
                panel.setGameState(GameState.PLAY);
            }
        } else if (panel.getGameState() == GameState.PAUSE) {

        }

        repaint();
    }


    @Override
    public void keyReleased(KeyEvent e) {
        mario.stop();
    }
}
