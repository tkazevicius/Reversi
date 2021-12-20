package com.reversi;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static com.reversi.Constants.BLACK;
import static com.reversi.Constants.WHITE;

public class Board extends Canvas implements MouseListener {

    public final Rectangle[][] boxes;
    Piece piece = new Piece();
    Status status = new Status();

    public Board() {
        addMouseListener(this);
        boxes = new Rectangle[8][8];

        for ( int r=0, c=0; r<boxes.length && c<boxes[0].length; r++, c++ ) {
            boxes[r][c] = new Rectangle(30+c*90,100+r*90,90,90);
        }
    }

    protected void paintComponent(Graphics g) {
        g.setColor( Color.orange );
        g.fillRect( 0, 0, 800, 900 );
        g.setColor( Color.black );
        g.setFont( new Font("Arial", Font.BOLD, 48) );
    }

    public void drawBoard(Graphics g) {
        paintComponent(g);
        for (int r=10;r < 800;r=r+90)
            for (int c = 10; c < 900; c=c+90) {
                g.drawRect(r,c,90,90);
            }
    }

    public void paintPieces(Graphics g) {
        for ( int r=0, c=0; r<piece.getPieces().length && c<piece.getPieces()[0].length; r++, c++ )
                if ( piece.getPieces()[r][c] > 0 )
                {
                    if ( piece.getPieces()[r][c] == BLACK )
                    {
                        g.setColor(Color.black);
                        status.scoreA++;
                    }
                    else if ( piece.getPieces()[r][c] == WHITE )
                    {
                        g.setColor(Color.white);
                        status.scoreB++;
                    }
                    Rectangle box = boxes[r][c];
                    g.fillOval(box.x+5, box.y+5, box.width-10, box.height-10);
                }
    }

    public void mouseReleased( MouseEvent evt )
    {
        int x = evt.getX();
        int y = evt.getY();

        for (int r = 0, c = 0; r < boxes.length && c < boxes[0].length; r++, c++)
            if (boxes[r][c].contains(evt.getPoint()) && checkBoundaries(x,y) == 1) {
                if (piece.getTurn() == BLACK && piece.detectTurn(x, y)) {
                    piece.setTurn(WHITE);
                } else if (piece.getTurn() == WHITE && piece.detectTurn(x, y)) {
                    piece.setTurn(BLACK);
                }
                repaint();
                return;
            }
    }
    public int checkBoundaries(int x, int y) {
        int xBoundaries = 800;
        int yBoundaries = 900;

        if (xBoundaries >= x && x >= 0 && yBoundaries >= y && y >= 0 )
            return 1;
        else
            return -1;
    }

    public Rectangle[][] getBoxes() {
        return boxes;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
