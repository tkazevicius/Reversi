package com.reversi.strategy;

import com.reversi.Piece;

import static com.reversi.Constants.BLACK;
import static com.reversi.Constants.WHITE;

public class BlackPiece implements FillStrategy {

    private final int[][] toFill;
    Piece piece = new Piece();

    public BlackPiece() {
        toFill = new int[8][8];
    }

    @Override
    public void fill(int player) {
        for ( int r=0; r<toFill.length; r++ )
            for ( int c=0; c<toFill[0].length; c++ ) {
                if (toFill[r][c] == 3) {
                    piece.getPieces()[r][c]= 1;
                }
            }
        for ( int r=0; r<toFill.length; r++ )
            for ( int c=0; c<toFill[0].length; c++ )
                toFill[r][c]=0;
            System.out.println("Black pieces filled");
    }
}
