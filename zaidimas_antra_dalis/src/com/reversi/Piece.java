package com.reversi;

import com.reversi.strategy.BlackPiece;
import com.reversi.strategy.FillStrategy;
import com.reversi.strategy.WhitePiece;

import static com.reversi.Constants.*;

public class Piece {

    private final int[][] pieces;
    private final int[][] toFill;
    private boolean turnOver = false;
    Black black = new Black();
    White white = new White();
    private int turn=1;

    public Piece() {
        pieces = new int[9][9];
        toFill = new int[8][8];
        for ( int r=0; r<toFill.length; r++ )
            for ( int c=0; c<toFill[0].length; c++ )
                toFill[r][c]=NONE;
        pieces[3][3] = WHITE;
        pieces[3][4] = BLACK;
        pieces[4][3] = BLACK;
        pieces[4][4] = WHITE;
    }

    public boolean detectTurn(int x, int y) {
        int xCurrent = (x-30)/90;
        int yCurrent = (y-100)/90;
        turnOver = false;
        move(xCurrent,yCurrent);
        return turnOver;
    }

    public void move(int xCurrent, int yCurrent) {
        int other;
        if (turn == BLACK) {
            other = 2;
        } else {
            other = 1;
        }

        //down
        if (yCurrent - 1 > 0 && pieces[yCurrent - 1][xCurrent] == other && pieces[yCurrent][xCurrent] == NONE) {
            for (int pos = yCurrent - 2; pos >= 0; pos--) {

                if (pieces[pos][xCurrent] == NONE) {
                    break;
                }

                if (pieces[pos][xCurrent] == turn) {
                    for(int i=pos;i<=yCurrent;i++)
                        toFill[i][xCurrent]=3;
                    turnOver=true;
                }
            }
        }

        //up
        if (yCurrent < 8 && pieces[yCurrent + 1][xCurrent] == other && pieces[yCurrent][xCurrent] == NONE) {
            for (int pos = yCurrent + 2; pos < 8 ; pos++) {

                if (pieces[pos][xCurrent] == NONE) {
                    break;
                }

                if (pieces[pos][xCurrent] == turn) {
                    for(int i=yCurrent;i<=pos;i++)
                        toFill[i][xCurrent]=3;
                    turnOver=true;
                }
            }
        }

        //left
        if (xCurrent < 8 && pieces[yCurrent][xCurrent + 1] == other && pieces[yCurrent][xCurrent] == NONE) {
            for (int pos = xCurrent + 2; pos < 8; pos++) {

                if (pieces[yCurrent][pos] == NONE) {
                    break;
                }

                if (pieces[yCurrent][pos] == turn) {
                    for(int i=xCurrent;i<=pos;i++)
                        toFill[yCurrent][i]=3;
                    turnOver=true;
                }
            }
        }


        //right
        if (xCurrent > 0 && pieces[yCurrent][xCurrent - 1] == other && pieces[yCurrent][xCurrent] == NONE) {
            for (int pos = xCurrent - 2; pos >= 0; pos--) {

                if (pieces[yCurrent][pos] == NONE) {
                    break;
                }

                if (pieces[yCurrent][pos] == turn) {
                    for(int i=pos;i<=xCurrent;i++)
                        toFill[yCurrent][i]=3;
                    turnOver=true;
                }
            }
        }

        //up-right
        if (xCurrent > 0 && yCurrent < 8 && pieces[yCurrent + 1][xCurrent - 1] == other && pieces[yCurrent][xCurrent] == NONE) {
            for (int pos = xCurrent - 2, pos2 = yCurrent + 2; pos >= 0 && pos2 < 8; pos--, pos2++) {

                if (pieces[pos2][pos] == NONE) {
                    break;
                }

                if (pieces[pos2][pos] == turn) {
                    for (int i=yCurrent, j=xCurrent;i<=pos2 && j>=pos;i++, j--)
                        toFill[i][j]=3;
                    turnOver=true;
                }
            }
        }


        //up-left
        if (xCurrent < 8 && yCurrent < 8 && pieces[yCurrent][xCurrent] == NONE && pieces[yCurrent + 1][xCurrent + 1] == other) {
            for (int pos = xCurrent + 2, pos2 = yCurrent + 2; pos < 8 && pos2 < 8; pos++, pos2++) {

                if (pieces[pos2][pos] == NONE) {
                    break;
                }

                if (pieces[pos2][pos] == turn) {
                    for (int i=yCurrent, j=xCurrent;i<=pos2 && j<=pos;i++, j++)
                        toFill[i][j]=3;
                    turnOver=true;
                }
            }
        }

        //down-right
        if (xCurrent > 0 && yCurrent > 0 && pieces[yCurrent][xCurrent] == NONE && pieces[yCurrent - 1][xCurrent - 1] == other) {
            for (int pos = xCurrent - 2, pos2 = yCurrent - 2; pos >= 0 && pos2 >= 0; pos--, pos2--) {

                if (pieces[pos2][pos] == NONE) {
                    break;
                }

                if (pieces[pos2][pos] == turn) {
                    for (int i=yCurrent, j=xCurrent;i>=pos2 && j>=pos;i--, j--)
                        toFill[i][j]=3;
                    turnOver=true;
                }
            }
        }

        //down-left
        if (xCurrent < 8 && yCurrent > 0 && pieces[yCurrent][xCurrent] == NONE && pieces[yCurrent - 1][xCurrent + 1] == other) {
            for (int pos = xCurrent + 2, pos2 = yCurrent - 2; pos < 8 && pos2 >= 0; pos++, pos2--) {

                if (pieces[pos2][pos] == NONE) {
                    break;
                }

                if (pieces[pos2][pos] == turn) {
                    for (int i=yCurrent, j=xCurrent;i>=pos2 && j<=pos;i--, j++)
                        toFill[i][j]=3;
                    turnOver=true;
                }
            }
        }
        if (getTurn() == BLACK) {
            FillStrategy blackPiece = new BlackPiece();
            blackPiece.fill(BLACK);
        }
        else if (getTurn() == WHITE) {
            FillStrategy whitePiece = new WhitePiece();
            whitePiece.fill(WHITE);
        }
    }


    public int[][] getPieces() {
        return pieces;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }
}
