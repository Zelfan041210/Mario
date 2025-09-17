package com.sxt;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyFrame extends JFrame implements KeyListener {


    public MyFrame(){
        //set window size as 800*600
        this.setSize(800, 600);
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
    }

    public static void main(String[] args) {
        MyFrame myFrame = new MyFrame();

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
