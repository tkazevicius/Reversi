package test;

import com.reversi.Board;
import com.reversi.Piece;
import com.reversi.Black;
import com.reversi.White;
import com.reversi.strategy.BlackPiece;
import com.reversi.strategy.FillStrategy;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.awt.*;
import java.io.File;

public class Tests {

    Piece piece = new Piece();
    Board board = new Board();
    Black black = new Black();
    White white = new White();
    Rectangle[][] boxes;
    private static final int INCORRECT_MOUSE_POS_X = 999;
    private static final int INCORRECT_MOUSE_POS_Y = 999;

    @Test
    public void TestGameStart() {

        boxes = new Rectangle[8][8];

        for ( int r=0, c=0; r<boxes.length && c<boxes[0].length; r++, c++ ) {
            boxes[r][c] = new Rectangle(30+c*90,100+r*90,90,90);
        }

        Assertions.assertEquals(boxes.length,8);
        Assertions.assertEquals(boxes[0].length,8);

        Assertions.assertEquals(piece.getPieces()[3][4],1);
        Assertions.assertEquals(piece.getPieces()[4][3],1);

        Assertions.assertEquals(piece.getPieces()[3][3],2);
        Assertions.assertEquals(piece.getPieces()[4][4],2);
    }

    @Test
    public void TestFirstMove() {
        Assertions.assertEquals(1,piece.getTurn());
    }

    @Test
    public void TestGameBoundaries() {
        Assertions.assertEquals(-1,board.checkBoundaries(INCORRECT_MOUSE_POS_X, INCORRECT_MOUSE_POS_Y));
    }

    @Test
    public void TestAbstraction() {
        Assertions.assertEquals("Black's turn", black.whoseTurn());
        Assertions.assertEquals("White's turn", white.whoseTurn());
    }

}
