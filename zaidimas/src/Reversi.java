import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Reversi Class
 */

public class Reversi extends JPanel implements MouseListener {
    static int[][] data = new int[8][8];
    static int gameSizeInt = 8;
    static JPanel panel = new JPanel();
    static int turn = 2;
    static int black = 0;
    static int white = 0;
    static int free = 0;
    static int blue = 0;
    static int fontX = 10;
    static int fontY = 495;
    static int noBlue = 0;
    static boolean noOneWin = false;

    public Reversi() {
        JFrame frame = new JFrame();
        frame.setTitle("Reversi");
        frame.setLocationRelativeTo(null);
        frame.setLocation(481, 505);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().setBackground(new Color(18, 199, 24));

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (int i = 0; i < gameSizeInt; i++)
                    for (int j = 0; j < gameSizeInt; j++) {
                        g.setColor(new Color(18, 199, 24));
                        g.fillRect(j * 60, i * 60, 60, 60);
                        g.setColor(Color.black);
                        g.drawRect(j * 60, i * 60, 60, 60);
                    }
                for (int i = 0; i < data.length; i++) {
                    for (int j = 0; j < data[i].length; j++) {
                        switch (data[i][j]) {
                            case 0:
                                break;

                            case 1:
                                g.setColor(Color.BLACK);
                                g.fillOval(5 + i * 60, 5 + j * 60, 50, 50);
                                break;

                            case 2:
                                g.setColor(Color.WHITE);
                                g.fillOval(5 + i * 60, 5 + j * 60, 50, 50);
                                break;

                            case -1:
                                g.setColor(Color.BLUE);
                                g.fillOval(20 + i * 60, 20 + j * 60, 25, 25);
                                break;

                        }
                    }
                }
                g.setColor(Color.BLACK);
                g.setFont(new Font("Courier New", Font.BOLD, 15));
                if (free == 0) {
                    if (black > white) {
                        g.drawString("Black wins     Black = " + black + "  White = " + white, fontX, fontY);
                    } else if (black == white || noOneWin) {
                        g.drawString("Draw     Black = " + black + "  White = " + white, fontX, fontY);
                    } else {
                        g.drawString("White wins     Black = " + black + "  White = " + white, fontX, fontY);
                    }
                } else {
                    if (turn == 1) {
                        g.drawString("Black's Turn     Black = " + black + "  White = " + white, fontX, fontY);
                    } else {
                        g.drawString("White's Turn     Black = " + black + "  White = " + white, fontX, fontY);
                    }
                }
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(481, 505);
            }
        };

        frame.add(panel);
        panel.addMouseListener(this);
        frame.pack();
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        start();
        SwingUtilities.invokeLater(Reversi::new);
    }

    static void start() {
        data[(gameSizeInt / 2) - 1][(gameSizeInt / 2) - 1] = 1;
        data[gameSizeInt / 2][(gameSizeInt / 2) - 1] = 2;
        data[(gameSizeInt / 2) - 1][gameSizeInt / 2] = 2;
        data[gameSizeInt / 2][gameSizeInt / 2] = 1;
        help();
        count();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x, y, i, j;
        x = e.getX();
        y = e.getY();
        i = x / 60;
        j = y / 60;
        if (isAccepted(i, j)) {
            if (turn == 1) {
                data[i][j] = turn;
                fillAll(i, j);
                turn = 2;
            } else {
                data[i][j] = turn;
                fillAll(i, j);
                turn = 1;
            }
            help();
            panel.repaint();
        }
    }

    boolean isAccepted(int i, int j) {
        if (i < gameSizeInt && j < gameSizeInt) {
            return data[i][j] == -1;
        }
        return false;
    }

    static void help() {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] == -1) {
                    data[i][j] = 0;
                }
            }
        }

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] == turn) {
                    check(i, j);
                }
            }
        }

        count();
        if (blue == 0 && free != 0) {
            noBlue++;
            if (noBlue >= 1) {
                noOneWin = true;
            }
            if (!noOneWin) {
                help();
            }
        } else {
            noBlue = 0;
        }
    }

    static void check(int i, int j) {
        int notTurn;
        int oI = i;
        int oJ = j;
        boolean done = false;
        if (turn == 1) {
            notTurn = 2;
        } else {
            notTurn = 1;
        }

        //up
        while (i >= 0 && i < gameSizeInt && j - 1 >= 0 && j - 1 < gameSizeInt && !done) {
            if (data[i][j - 1] == notTurn) {
                if (j - 2 >= 0 && j - 2 < gameSizeInt) {
                    if (data[i][j - 2] == 0) {
                        data[i][j - 2] = -1;
                        done = true;
                    } else if (data[i][j - 2] == notTurn) {
                        j = j - 1;
                    } else {
                        done = true;
                    }
                } else {
                    done = true;
                }
            } else {
                done = true;
            }
        }

        j = oJ;
        done = false;

        //up-right
        while (i + 1 >= 0 && i + 1 < gameSizeInt && j - 1 >= 0 && j - 1 < gameSizeInt && !done) {
            if (data[i + 1][j - 1] == notTurn) {
                if (i + 2 < gameSizeInt && j - 2 >= 0 && j - 2 < gameSizeInt) {
                    if (data[i + 2][j - 2] == 0) {
                        data[i + 2][j - 2] = -1;
                        done = true;
                    } else if (data[i + 2][j - 2] == notTurn) {
                        j = j - 1;
                        i = i + 1;
                    } else {
                        done = true;
                    }
                } else {
                    done = true;
                }
            } else {
                done = true;
            }
        }
        i = oI;
        j = oJ;
        done = false;

        //right
        while (i + 1 >= 0 && i + 1 < gameSizeInt && j >= 0 && j < gameSizeInt && !done) {
            if (data[i + 1][j] == notTurn) {
                if (i + 2 < gameSizeInt) {
                    if (data[i + 2][j] == 0) {
                        data[i + 2][j] = -1;
                        done = true;
                    } else if (data[i + 2][j] == notTurn) {
                        i = i + 1;
                    } else {
                        done = true;
                    }
                } else {
                    done = true;
                }
            } else {
                done = true;
            }
        }

        i = oI;
        done = false;

        //right-down
        while (i + 1 >= 0 && i + 1 < gameSizeInt && j + 1 >= 0 && j + 1 < gameSizeInt && !done) {
            if (data[i + 1][j + 1] == notTurn) {
                if (i + 2 < gameSizeInt && j + 2 < gameSizeInt) {
                    if (data[i + 2][j + 2] == 0) {
                        data[i + 2][j + 2] = -1;
                        done = true;
                    } else if (data[i + 2][j + 2] == notTurn) {
                        i = i + 1;
                        j = j + 1;
                    } else {
                        done = true;
                    }
                } else {
                    done = true;
                }
            } else {
                done = true;
            }
        }

        i = oI;
        j = oJ;
        done = false;

        //down
        while (i >= 0 && i < gameSizeInt && j + 1 >= 0 && j + 1 < gameSizeInt && !done) {
            if (data[i][j + 1] == notTurn) {
                if (j + 2 < gameSizeInt) {
                    if (data[i][j + 2] == 0) {
                        data[i][j + 2] = -1;
                        done = true;
                    } else if (data[i][j + 2] == notTurn) {
                        j = j + 1;
                    } else {
                        done = true;
                    }
                } else {
                    done = true;
                }
            } else {
                done = true;
            }
        }

        j = oJ;
        done = false;

        //down-left
        while (i - 1 >= 0 && i - 1 < gameSizeInt && j + 1 >= 0 && j + 1 < gameSizeInt && !done) {
            if (data[i - 1][j + 1] == notTurn) {
                if (i - 2 >= 0 && i - 2 < gameSizeInt && j + 2 < gameSizeInt) {
                    if (data[i - 2][j + 2] == 0) {
                        data[i - 2][j + 2] = -1;
                        done = true;
                    } else if (data[i - 2][j + 2] == notTurn) {
                        j = j + 1;
                        i = i - 1;
                    } else {
                        done = true;
                    }
                } else {
                    done = true;
                }
            } else {
                done = true;
            }
        }

        i = oI;
        j = oJ;
        done = false;

        //left
        while (i - 1 >= 0 && i - 1 < gameSizeInt && j >= 0 && j < gameSizeInt && !done) {
            if (data[i - 1][j] == notTurn) {
                if (i - 2 >= 0 && i - 2 < gameSizeInt) {
                    if (data[i - 2][j] == 0) {
                        data[i - 2][j] = -1;
                        done = true;
                    } else if (data[i - 2][j] == notTurn) {
                        i = i - 1;
                    } else {
                        done = true;
                    }
                } else {
                    done = true;
                }
            } else {
                done = true;
            }
        }

        i = oI;
        done = false;

        //left-up
        while (i - 1 >= 0 && i - 1 < gameSizeInt && j - 1 >= 0 && j - 1 < gameSizeInt && !done) {
            if (data[i - 1][j - 1] == notTurn) {
                if (i - 2 >= 0 && i - 2 < gameSizeInt && j - 2 >= 0 && j - 2 < gameSizeInt) {
                    if (data[i - 2][j - 2] == 0) {
                        data[i - 2][j - 2] = -1;
                        done = true;
                    } else if (data[i - 2][j - 2] == notTurn) {
                        i = i - 1;
                        j = j - 1;
                    } else {
                        done = true;
                    }
                } else {
                    done = true;
                }
            } else {
                done = true;
            }
        }

    }

    static void fillAll(int i, int j) {
        int notTurn;
        int oI = i;
        int oJ = j;
        boolean done = false;
        if (turn == 1) {
            notTurn = 2;
        } else {
            notTurn = 1;
        }

        //up
        while (i >= 0 && i < gameSizeInt && j - 1 >= 0 && j - 1 < gameSizeInt && !done) {
            if (data[i][j - 1] == notTurn) {
                if (j - 2 >= 0 && j - 2 < gameSizeInt) {
                    if (data[i][j - 2] == turn) {
                        for (int k = j - 1; k <= oJ; k++) {
                            data[i][k] = turn;
                        }
                        done = true;
                    } else if (data[i][j - 2] == notTurn) {
                        j = j - 1;
                    } else {
                        done = true;
                    }
                } else {
                    done = true;
                }
            } else {
                done = true;
            }
        }

        j = oJ;
        done = false;

        //up-right
        while (i + 1 >= 0 && i + 1 < gameSizeInt && j - 1 >= 0 && j - 1 < gameSizeInt && !done) {
            if (data[i + 1][j - 1] == notTurn) {
                if (i + 2 < gameSizeInt && j - 2 >= 0 && j - 2 < gameSizeInt) {
                    if (data[i + 2][j - 2] == turn) {
                        int m = i + 1;
                        for (int k = j - 1; k < oJ; k++) {
                            data[m][k] = turn;
                            m--;
                        }
                        done = true;
                    } else if (data[i + 2][j - 2] == notTurn) {
                        j = j - 1;
                        i = i + 1;
                    } else {
                        done = true;
                    }
                } else {
                    done = true;
                }
            } else {
                done = true;
            }
        }
        i = oI;
        j = oJ;
        done = false;

        //right
        while (i + 1 >= 0 && i + 1 < gameSizeInt && j >= 0 && j < gameSizeInt && !done) {
            if (data[i + 1][j] == notTurn) {
                if (i + 2 < gameSizeInt) {
                    if (data[i + 2][j] == turn) {
                        for (int k = i + 1; k > oI; k--) {
                            data[k][j] = turn;
                        }
                        done = true;
                    } else if (data[i + 2][j] == notTurn) {
                        i = i + 1;
                    } else {
                        done = true;
                    }
                } else {
                    done = true;
                }
            } else {
                done = true;
            }
        }

        i = oI;
        done = false;

        //right-down
        while (i + 1 >= 0 && i + 1 < gameSizeInt && j + 1 >= 0 && j + 1 < gameSizeInt && !done) {
            if (data[i + 1][j + 1] == notTurn) {
                if (i + 2 < gameSizeInt && j + 2 < gameSizeInt) {
                    if (data[i + 2][j + 2] == turn) {
                        int m = i + 1;
                        for (int k = j + 1; k > oJ; k--) {
                            data[m][k] = turn;
                            m--;
                        }
                        done = true;
                    } else if (data[i + 2][j + 2] == notTurn) {
                        i = i + 1;
                        j = j + 1;
                    } else {
                        done = true;
                    }
                } else {
                    done = true;
                }
            } else {
                done = true;
            }
        }

        i = oI;
        j = oJ;
        done = false;

        //down
        while (i >= 0 && i < gameSizeInt && j + 1 >= 0 && j + 1 < gameSizeInt && !done) {
            if (data[i][j + 1] == notTurn) {
                if (j + 2 < gameSizeInt) {
                    if (data[i][j + 2] == turn) {
                        for (int k = j + 1; k > oJ; k--) {
                            data[i][k] = turn;
                        }
                        done = true;
                    } else if (data[i][j + 2] == notTurn) {
                        j = j + 1;
                    } else {
                        done = true;
                    }
                } else {
                    done = true;
                }
            } else {
                done = true;
            }
        }

        j = oJ;
        done = false;

        //down-left
        while (i - 1 >= 0 && i - 1 < gameSizeInt && j + 1 >= 0 && j + 1 < gameSizeInt && !done) {
            if (data[i - 1][j + 1] == notTurn) {
                if (i - 2 >= 0 && i - 2 < gameSizeInt && j + 2 < gameSizeInt) {
                    if (data[i - 2][j + 2] == turn) {
                        int m = i - 1;
                        for (int k = j + 1; k > oJ; k--) {
                            data[m][k] = turn;
                            m++;
                        }
                        done = true;
                    } else if (data[i - 2][j + 2] == notTurn) {
                        j = j + 1;
                        i = i - 1;
                    } else {
                        done = true;
                    }
                } else {
                    done = true;
                }
            } else {
                done = true;
            }
        }

        i = oI;
        j = oJ;
        done = false;

        //left
        while (i - 1 >= 0 && i - 1 < gameSizeInt && j >= 0 && j < gameSizeInt && !done) {
            if (data[i - 1][j] == notTurn) {
                if (i - 2 >= 0 && i - 2 < gameSizeInt) {
                    if (data[i - 2][j] == turn) {
                        for (int k = i - 2; k < oI; k++) {
                            data[k][j] = turn;
                        }
                        done = true;
                    } else if (data[i - 2][j] == notTurn) {
                        i = i - 1;
                    } else {
                        done = true;
                    }
                } else {
                    done = true;
                }
            } else {
                done = true;
            }
        }

        i = oI;
        done = false;

        //left-up
        while (i - 1 >= 0 && i - 1 < gameSizeInt && j - 1 >= 0 && j - 1 < gameSizeInt && !done) {
            if (data[i - 1][j - 1] == notTurn) {
                if (i - 2 >= 0 && i - 2 < gameSizeInt && j - 2 >= 0 && j - 2 < gameSizeInt) {
                    if (data[i - 2][j - 2] == turn) {
                        int m = i - 1;
                        for (int k = j - 1; k < oJ; k++) {
                            data[m][k] = turn;
                            m++;
                        }
                        done = true;
                    } else if (data[i - 2][j - 2] == notTurn) {
                        i = i - 1;
                        j = j - 1;
                    } else {
                        done = true;
                    }
                } else {
                    done = true;
                }
            } else {
                done = true;
            }
        }

    }

    static void count() {
        black = 0;
        white = 0;
        free = 0;
        blue = 0;
        for(int i = 0 ;i < data.length;i++){
            for(int j = 0 ;j < data[i].length;j++){
                if(data[i][j] == 1){
                    black++;
                }
                if(data[i][j] == 2){
                    white++;
                }
                if(data[i][j] == 0){
                    free++;
                }
                if(data[i][j] == -1){
                    blue++;
                }
            }
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }
}