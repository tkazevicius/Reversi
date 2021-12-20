package com.reversi;

import java.awt.*;
import static com.reversi.Constants.*;

public class Status {

    int scoreA = 0;
    int scoreB = 0;
    Piece piece = new Piece();
    Black black = new Black();
    White white = new White();

    public void showResult (Graphics g) {
        g.setColor( Color.black );
        g.setFont( new Font("Arial", Font.BOLD, 48) );
        if(scoreA+scoreB != 64) {
            if (piece.getTurn() == BLACK) {
                g.drawString(black.whoseTurn(), 50, 70);
                System.out.println(black.whoseTurn());
            }
            else if (piece.getTurn() == WHITE) {
                g.drawString(white.whoseTurn(), 50, 70);
                System.out.println(white.whoseTurn());
            }
            g.drawString( scoreA +" : "+ scoreB, 620, 70 );
        } else if (scoreA > scoreB && scoreA+scoreB == 64) {
            g.drawString( "Black wins!", 50, 70 );
            g.drawString( scoreA +" : "+ scoreB, 620, 70 );
        } else if (scoreA < scoreB && scoreA+scoreB == 64) {
            g.drawString( "White wins!", 50, 70 );
            g.drawString( scoreA +" : "+ scoreB, 620, 70 );
        } else if (scoreA == scoreB && scoreA == 32) {
            g.drawString( "Draw", 50, 70 );
            g.drawString( scoreA +" : "+ scoreB, 620, 70 );
        }
    }
}
