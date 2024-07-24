import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.Objects;

public class Connect4 implements ActionListener {

    JButton[] row1 = new JButton[7];
    JButton[] row2 = new JButton[7];
    JButton[] row3 = new JButton[7];
    JButton[] row4 = new JButton[7];
    JButton[] row5 = new JButton[7];
    JButton[] row6 = new JButton[7];
    JButton[] row7 = new JButton[7];

    JButton[][] buttons = {row1, row2, row3, row4, row5, row6, row7};

    MyFrame myFrame = new MyFrame();

    int player = 1;

    static boolean stillPlaying = true;

    Connect4() {

        // BUTTONS
        for (JButton[] row : buttons) {
            for (int i = 0; i < row.length; i++) {
                JButton button = new JButton();
                button.addActionListener(this);
                //button.setBorderPainted(false);
                button.setBorder(null);
                button.setOpaque(true);
                button.setFocusable(false);
                //button.setText(String.valueOf(i));
                button.setFont(new Font("Times New Roman", Font.PLAIN, 10));
                button.setPreferredSize(new Dimension(1, 1));
                button.setForeground(Color.black);
                button.setEnabled(false);
                button.setBackground(Color.white);
                row[i] = button;
            }
        }

       for (int i = 0; i < buttons[6].length; i++) {
           buttons[6][i].setEnabled(true);
           buttons[6][i].setBackground(Color.pink);
           Border buttonBorder = BorderFactory.createLineBorder(Color.white);
           buttons[6][i].setBorder(buttonBorder);
       }

        // PANEL
        JPanel panel = new JPanel();
        panel.setBackground(Color.pink);
        panel.setBounds(35, 10, 360, 360);
        panel.setLayout(new GridLayout(7, 7, 5, 5));
        Border panelBorder = BorderFactory.createLineBorder(Color.pink);
        panel.setBorder(panelBorder);

        for (JButton[] buttonArray : buttons) {
            for (int i = 0; i < buttonArray.length; i++) {
                panel.add(buttonArray[i]);
            }
        }

        // FRAME
        myFrame.add(panel);
        myFrame.setVisible(true);

    }


    public static void main(String[] args) {

        Border border = BorderFactory.createLineBorder(Color.black, 3);
        Connect4 connect4 = new Connect4();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < buttons[6].length; i++) {
            if (e.getSource() == buttons[6][i] && player == 1) {
                for (int j = 5; j >= 0; j--) {
                    if (buttons[j][i].getBackground() == Color.white) {
                        buttons[j][i].setBackground(Color.black);

                        if (checkScore(Color.black, buttons)) {
                            playAgain(player);
                        }
                        player = 2;
                        myFrame.setTitle("Player " + player + "'s Turn");

                        break;
                    }

                }
            } else if (e.getSource() == buttons[6][i] && player == 2) {
                for (int j = 5; j >= 0; j--) {
                    if (buttons[j][i].getBackground() == Color.white) {
                        buttons[j][i].setBackground(Color.magenta);

                        if (checkScore(Color.magenta, buttons))
                            playAgain(player);

                        player = 1;
                        myFrame.setTitle("Player " + player + "'s Turn");

                        break;
                    }

                }
            }
        }

    }

    private void playAgain(int player) {
        String s = (String) JOptionPane.showInputDialog(
                myFrame,
                "Play Again? Y or N", "Player " + player + " wins!", JOptionPane.QUESTION_MESSAGE);
        if (Objects.equals(s.toUpperCase(), "Y")) {
            resetGame();
        }
        else {
            myFrame.dispatchEvent(new WindowEvent(myFrame, WindowEvent.WINDOW_CLOSING));
        }
    }

    public void resetGame() {
        for (JButton[] row : buttons) {
            if (!Arrays.equals(row, buttons[6]))
                for (int i = 0; i < row1.length; i++) {
                    row[i].setBackground(Color.white);
                }
        }
    }


    public boolean checkScore(Color color, JButton[][] buttons) {
        boolean isWinner = false;
        int rowCount = 0;
        // Check rows
        for (int i = buttons.length - 2; i >= 0; i--) {
            for (int j = 0; j < buttons[i].length; j++) {
                if (buttons[i][j].getBackground() == color) {
                    rowCount++;
                    if (rowCount == 4) {
                        isWinner = true;
                        break;
                    }
                }
                else {
                    rowCount = 0;
                }
            }
            if (isWinner)
                break;

        }

        // Check columns
        int columnCount = 0;
        for (int i = 0; i < row1.length; i++) {
            for (JButton[] row : buttons) {
                if (row[i].getBackground() == color) {
                    columnCount++;
                    if (columnCount == 4) {
                        isWinner = true;
                        break;
                    }
                } else {
                    columnCount = 0;
                }
            }
        }

        // Check LEFT Downward Diagonals
        int diagonalCount = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0, k = i; k < buttons.length; k++, j++) {
                if (buttons[k][j].getBackground() == color) {
                    diagonalCount++;
                    if (diagonalCount == 4) {
                        isWinner = true;
                        break;
                    }
                } else {
                    diagonalCount = 0;
                }
            }
        }

        // Check LEFT Upward diagonals
        diagonalCount = 0;
        for (int i = 3; i < buttons.length; i++) {
            for (int j = 0, k = i; k >= 0; k--, j++) {
                if (buttons[k][j].getBackground() == color) {
                    diagonalCount++;
                    if (diagonalCount == 4) {
                        isWinner = true;
                        break;
                    }
                } else {
                    diagonalCount = 0;
                }
            }
        }

        // Check RIGHT Downward Diagonals
        diagonalCount = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 6, k = i; k < buttons.length; k++, j--) {
                if (buttons[k][j].getBackground() == color) {
                    diagonalCount++;
                    if (diagonalCount == 4) {
                        isWinner = true;
                        break;
                    }
                } else {
                    diagonalCount = 0;
                }
            }
        }

        // Check RIGHT Upward diagonals
        diagonalCount = 0;
        for (int i = 3; i < buttons.length; i++) {
            for (int j = 6, k = i; k >= 0; k--, j--) {
                if (buttons[k][j].getBackground() == color) {
                    diagonalCount++;
                    if (diagonalCount == 4) {
                        isWinner = true;
                        break;
                    }
                } else {
                    diagonalCount = 0;
                }
            }
        }
        return isWinner;
    }
}