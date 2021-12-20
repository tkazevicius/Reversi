package com.reversi;

import java.awt.*;
import javax.swing.*;

public class Reversi extends Canvas {

    Status status = new Status();
    Board board = new Board();
    Piece piece = new Piece();

    public static void main(String[] args) {
        JFrame win = new JFrame(Name.getInstance().nameReversi);
        win.setSize(800,900);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.add( new Reversi() );
        win.setVisible(true);
        win.setResizable(false);
    }

    public Reversi() {

    }

    public void paint( Graphics g) {
        board.drawBoard(g);
        board.paintPieces(g);
        status.showResult(g);
    }

}