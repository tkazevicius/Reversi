import java.awt.*;
import java.awt.Rectangle;
import java.awt.event.*;
import javax.swing.*;

public class Reversi extends Canvas implements MouseListener
{
    public static void main(String[] args)
    {
        JFrame win = new JFrame("Reversi");
        win.setSize(800,900);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.add( new Reversi() );
        win.setVisible(true);
    }

    private final int NONE = 0;
    private int[][] toFill;
    private final int BLACK = 1;
    private final int WHITE = 2;
    private boolean turnOver = false;
    private boolean inBounds;
    private boolean inBounds2;

    private int player = BLACK;

    private final Rectangle[][] boxes;
    private final int[][] pieces;

    public Reversi()
    {
        addMouseListener(this);
        boxes = new Rectangle[8][8];
        pieces = new int[9][9];
        toFill = new int[8][8];
        for ( int r=0; r<toFill.length; r++ )
            for ( int c=0; c<toFill[0].length; c++ )
                toFill[r][c]=0;
        for ( int r=0; r<boxes.length; r++ )
            for ( int c=0; c<boxes[0].length; c++ )
            {
                boxes[r][c] = new Rectangle(30+c*90,100+r*90,90,90);
            }
        pieces[3][3] = 2;
        pieces[3][4] = 1;
        pieces[4][3] = 1;
        pieces[4][4] = 2;

    }

    public void paint( Graphics g)
    {
        g.setColor( Color.orange );
        g.fillRect( 0, 0, 800, 900 );
        g.setColor( Color.black );
        Graphics2D g2 = (Graphics2D)g;
        g.setFont( new Font("Arial", Font.BOLD, 48) );

        // board
        for (Rectangle[] rectangles : boxes)
            for (int c = 0; c < boxes[0].length; c++) {
                g2.draw(rectangles[c]);
            }
        // pieces
        int scoreA = 0;
        int scoreB = 0;
        for ( int r=0; r<pieces.length; r++ )
            for ( int c=0; c<pieces[0].length; c++ )
                if ( pieces[r][c] > 0 )
                {
                    if ( pieces[r][c] == BLACK )
                    {
                        g.setColor(Color.black);
                        scoreA++;
                    }
                    else if ( pieces[r][c] == WHITE )
                    {
                        g.setColor(Color.white);
                        scoreB++;
                    }
                    Rectangle box = boxes[r][c];
                    g.fillOval(box.x+5, box.y+5, box.width-10, box.height-10);
                }

        // status
        g.setColor( Color.black );
        g.setFont( new Font("Arial", Font.BOLD, 48) );
        if(scoreA+scoreB != 64) {
            g.drawString( (player==BLACK ? "Black" : "White") + "'s turn.", 50, 70 );
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


    public void mouseReleased( MouseEvent evt )
    {
        int x = evt.getX();
        int y = evt.getY();

        for ( int r=0; r<boxes.length; r++ )
            for ( int c=0; c<boxes[0].length; c++ )
                if ( boxes[r][c].contains( evt.getPoint() ) )
                {
                    if (player == BLACK && detectTurn(x,y)) {
                        player=WHITE;
                    }
                    else if (player == WHITE && detectTurn(x,y)) {
                        player=BLACK;
                    }
                    repaint();
                    return;
                }
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
        int currentColor;
        if (player == BLACK) {
            other = 2;
            currentColor = 1;
        } else {
            other = 1;
            currentColor = 2;
        }

        //down
        if (yCurrent - 1 > 0) {
            if (pieces[yCurrent - 1][xCurrent] == other && pieces[yCurrent][xCurrent] == NONE) {
                for (int pos = yCurrent - 2; pos >= 0; pos--) {

                    if (pieces[pos][xCurrent] == NONE) {
                        break;
                    }

                    if (pieces[pos][xCurrent] == player) {
                        for(int i=pos;i<=yCurrent;i++)
                            toFill[i][xCurrent]=3;
                        turnOver=true;
                    }
                }
            }
        }

        //up
        if (yCurrent < 8) {
            if (pieces[yCurrent + 1][xCurrent] == other && pieces[yCurrent][xCurrent] == NONE) {
                for (int pos = yCurrent + 2; pos < 8 ; pos++) {

                    if (pieces[pos][xCurrent] == NONE) {
                        break;
                    }

                    if (pieces[pos][xCurrent] == player) {
                        for(int i=yCurrent;i<=pos;i++)
                            toFill[i][xCurrent]=3;
                        turnOver=true;
                    }
                }
            }
        }

        //left
        if (xCurrent < 8) {
            if (pieces[yCurrent][xCurrent + 1] == other && pieces[yCurrent][xCurrent] == NONE) {
                for (int pos = xCurrent + 2; pos < 8; pos++) {

                    if (pieces[yCurrent][pos] == NONE) {
                        break;
                    }

                    if (pieces[yCurrent][pos] == player) {
                        for(int i=xCurrent;i<=pos;i++)
                            toFill[yCurrent][i]=3;
                        turnOver=true;
                    }
                }
            }
        }


        //right
        if (xCurrent > 0) {
            if (pieces[yCurrent][xCurrent - 1] == other && pieces[yCurrent][xCurrent] == NONE) {
                for (int pos = xCurrent - 2; pos >= 0; pos--) {

                    if (pieces[yCurrent][pos] == NONE) {
                        break;
                    }

                    if (pieces[yCurrent][pos] == player) {
                        for(int i=pos;i<=xCurrent;i++)
                            toFill[yCurrent][i]=3;
                        turnOver=true;
                    }
                }
            }
        }

        //up-right
        if (xCurrent > 0 && yCurrent < 8) {
            if (pieces[yCurrent + 1][xCurrent - 1] == other && pieces[yCurrent][xCurrent] == NONE) {
                for (int pos = xCurrent - 2, pos2 = yCurrent + 2; pos >= 0 && pos2 < 8; pos--, pos2++) {

                    if (pieces[pos2][pos] == NONE) {
                        break;
                    }

                    if (pieces[pos2][pos] == player) {
                        //fillDiagonalUpRight(yCurrent, pos, currentColor, pos2, xCurrent);
                        for (int i=yCurrent, j=xCurrent;i<=pos2 && j>=pos;i++, j--)
                            toFill[i][j]=3;
                        turnOver=true;
                    }
                }
            }
        }


        //up-left
        if (xCurrent < 8 && yCurrent < 8) {
            if (pieces[yCurrent][xCurrent] == NONE && pieces[yCurrent + 1][xCurrent + 1] == other) {
                for (int pos = xCurrent + 2, pos2 = yCurrent + 2; pos < 8 && pos2 < 8; pos++, pos2++) {

                    if (pieces[pos2][pos] == NONE) {
                        break;
                    }

                    if (pieces[pos2][pos] == player) {
                        for (int i=yCurrent, j=xCurrent;i<=pos2 && j<=pos;i++, j++)
                            toFill[i][j]=3;
                        turnOver=true;
                    }
                }
            }
        }

        //down-right
        if (xCurrent > 0 && yCurrent > 0) {
            if (pieces[yCurrent][xCurrent] == NONE && pieces[yCurrent - 1][xCurrent - 1] == other) {
                for (int pos = xCurrent - 2, pos2 = yCurrent - 2; pos >= 0 && pos2 >= 0; pos--, pos2--) {

                    if (pieces[pos2][pos] == NONE) {
                        break;
                    }

                    if (pieces[pos2][pos] == player) {
                        for (int i=yCurrent, j=xCurrent;i>=pos2 && j>=pos;i--, j--)
                            toFill[i][j]=3;
                        turnOver=true;
                    }
                }
            }
        }

        //down-left
        if (xCurrent < 8 && yCurrent > 0) {
            if (pieces[yCurrent][xCurrent] == NONE && pieces[yCurrent - 1][xCurrent + 1] == other) {
                for (int pos = xCurrent + 2, pos2 = yCurrent - 2; pos < 8 && pos2 >= 0; pos++, pos2--) {

                    if (pieces[pos2][pos] == NONE) {
                        break;
                    }

                    if (pieces[pos2][pos] == player) {
                        //fillDiagonalDownLeft(pos2, xCurrent, currentColor, yCurrent, pos);
                        for (int i=yCurrent, j=xCurrent;i>=pos2 && j<=pos;i--, j++)
                            toFill[i][j]=3;
                        turnOver=true;
                    }
                }
            }
        }
        fill();
    }

    private void fill() {
    for ( int r=0; r<toFill.length; r++ )
        for ( int c=0; c<toFill[0].length; c++ ) {
            if (toFill[r][c] == 3 && player == BLACK) {
                pieces[r][c]= 1;
            }
            else if (toFill[r][c] == 3 && player == WHITE) {
                pieces[r][c] = 2;
            }
        }
        for ( int r=0; r<toFill.length; r++ )
            for ( int c=0; c<toFill[0].length; c++ )
                toFill[r][c]=0;
}

    public void mouseClicked( MouseEvent evt ) {}
    public void mousePressed ( MouseEvent evt ) {}
    public void mouseEntered ( MouseEvent evt ) {}
    public void mouseExited  ( MouseEvent evt ) {}
}