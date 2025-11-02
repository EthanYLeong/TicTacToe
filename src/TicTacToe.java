import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;

public class TicTacToe {

    int boardWidth = 700;
    int boardHeight = 750;

    JFrame frame = new JFrame("Tic-Tac-Toe");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JButton restartButton = new JButton();

    JButton[][] board = new JButton[5][5];
    String playerX = "X";
    String playerO = "O";
    String currentPlayer = playerX;

    boolean gameOver = false;
    int turns = 0;

    TicTacToe() {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Tic-Tac-Toe");
        textLabel.setOpaque(true);

        restartButton.setBackground(Color.gray);
        restartButton.setForeground(Color.white);
        restartButton.setText("Restart");
        restartButton.setFont(new Font("Arial", Font.BOLD, 20));
        restartButton.setFocusable(false);
        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                turns = 0;
                gameOver = false;
                clearBoard();
                currentPlayer = "X";
                textLabel.setText(currentPlayer + "'s turn");
            }
        });

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        textPanel.add(restartButton, BorderLayout.EAST);
        textPanel.setBackground(Color.blue);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(5, 5));
        boardPanel.setBackground(Color.darkGray);
        frame.add(boardPanel);

        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);

                tile.setBackground(Color.darkGray);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);
                // tile.setText(currentPlayer);

                tile.addActionListener(
                        new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                if (gameOver)
                                    return;
                                if (tile.getText() == "") {
                                    turns++;
                                    tile.setText(currentPlayer);
                                    checkWinner();
                                    if (!gameOver) {
                                        currentPlayer = currentPlayer == playerX ? playerO : playerX;
                                        textLabel.setText(currentPlayer + "'s turn");
                                    }

                                }
                            }
                        });
            }
        }
    }

    void checkWinner() {
        if (checkHorizontal()) return;
        if (checkVertical()) return;
        if (checkDiagonal()) return;
        if (checkUpperDiagonal()) return;
        if (checkLowerDiagonal()) return;
        if (checkAntiDiagonal()) return;
        if (checkAntiUpperDiagonal()) return;
        if (checkAntiLowerDiagonal()) return;
        checkDraw();
    }

    boolean checkHorizontal() {
        for (int r = 0; r < 5; r++) {
            // use set for list without duplicates
            Set<JButton> xWinnerList = new HashSet<>();
            Set<JButton> oWinnerList = new HashSet<>();

            // counter represents amount of connections between x's or o's
            int xCounter = 0;
            int oCounter = 0;
            for (int c = 1; c < 5; c++) {
                JButton previous = board[r][c - 1];
                if (previous.getText() == board[r][c].getText()) {
                    if (previous.getText() == "X") {
                        xWinnerList.add(previous);
                        xWinnerList.add(board[r][c]);
                        xCounter++;
                    } else if (previous.getText() == "O") {
                        oWinnerList.add(previous);
                        oWinnerList.add(board[r][c]);
                        oCounter++;
                    }
                }
            }

            // 3 connections = 4 x's or o's
            if (xCounter >= 3) {
                for (JButton tile : xWinnerList) {
                    setWinner(tile);
                }
                gameOver = true;
                return true;
            } else if (oCounter >= 3) {
                for (JButton tile : oWinnerList) {
                    setWinner(tile);
                }
                gameOver = true;
                return true;
            } 
        }
        return false;
    }

    boolean checkVertical() {
        for (int c = 0; c < 5; c++) {
            Set<JButton> xWinnerList = new HashSet<>();
            Set<JButton> oWinnerList = new HashSet<>();

            int xCounter = 0;
            int oCounter = 0;
            for (int r = 1; r < 5; r++) {
                JButton previous = board[r - 1][c];
                if (previous.getText() == board[r][c].getText()) {
                    if (previous.getText() == "X") {
                        xWinnerList.add(previous);
                        xWinnerList.add(board[r][c]);
                        xCounter++;
                    } else if (previous.getText() == "O") {
                        oWinnerList.add(previous);
                        oWinnerList.add(board[r][c]);
                        oCounter++;
                    }
                }
            }
            if (xCounter >= 3) {
                for (JButton tile : xWinnerList) {
                    setWinner(tile);
                }
                gameOver = true;
                return true;
            } else if (oCounter >= 3) {
                for (JButton tile : oWinnerList) {
                    setWinner(tile);
                }
                gameOver = true;
                return true;
            }
        }
        return false;
    }

    boolean checkDiagonal() {
        Set<JButton> xWinnerList = new HashSet<>();
        Set<JButton> oWinnerList = new HashSet<>();
        int xCounter = 0;
        int oCounter = 0;
        for (int r = 1; r < 5; r++) {
            int c = r;
            JButton previous = board[r - 1][c - 1];
            if (previous.getText() == board[r][c].getText()) {
                if (previous.getText() == "X") {
                    xCounter++;
                    xWinnerList.add(previous);
                    xWinnerList.add(board[r][c]);
                }
                if (previous.getText() == "O") {
                    oCounter++;
                    oWinnerList.add(previous);
                    oWinnerList.add(board[r][c]);
                }
            }
        }
        if (xCounter >= 3) {
            for (JButton button : xWinnerList) {
                setWinner(button);
            }
            gameOver = true;
            return true;
        } else if (oCounter >= 3) {
            for (JButton button : oWinnerList) {
                setWinner(button);
            }
            gameOver = true;
            return true;
        }
        return false;
    }

    boolean checkUpperDiagonal() {
        int counter = 0;
        for (int c = 2; c < 5; c++) {
            int r = c - 1;
            if (board[r][c].getText() == board[0][1].getText() && board[0][1].getText() != "") {
                counter++;
            }
        }

        if (counter == 3) {
            for (int c = 1; c < 5; c++) {
                int r = c - 1;
                setWinner(board[r][c]);
            }
            gameOver = true;
            return true;
        }
        return false;
    }

    boolean checkLowerDiagonal() {
        int counter = 0;
        for (int r = 2; r < 5; r++) {
            int c = r - 1;
            if (board[r][c].getText() == board[1][0].getText() && board[1][0].getText() != "") {
                counter++;
            }
        }

        if (counter == 3) {
            for (int r = 1; r < 5; r++) {
                int c = r - 1;
                setWinner(board[r][c]);
            }
            gameOver = true;
            return true;
        }
        return false;
    }

    boolean checkAntiDiagonal() {
        Set<JButton> xWinnerList = new HashSet<>();
        Set<JButton> oWinnerList = new HashSet<>();
        int xCounter = 0;
        int oCounter = 0;
        for (int r = 1; r < 5; r++) {
            int c =  4 - r;
            JButton previous = board[r - 1][c + 1];
            if (previous.getText() == board[r][c].getText()) {
                if (previous.getText() == "X") {
                    xCounter++;
                    xWinnerList.add(previous);
                    xWinnerList.add(board[r][c]);
                }
                if (previous.getText() == "O") {
                    oCounter++;
                    oWinnerList.add(previous);
                    oWinnerList.add(board[r][c]);
                }
            }
        }
        if (xCounter >= 3) {
            for (JButton button : xWinnerList) {
                setWinner(button);
            }
            gameOver = true;
            return true;
        } else if (oCounter >= 3) {
            for (JButton button : oWinnerList) {
                setWinner(button);
            }
            gameOver = true;
            return true;
        }
        return false;
    }

    boolean checkAntiUpperDiagonal() {
        int counter = 0;
        for (int c = 1; c < 4; c++) {
            int r = 3 - c;
            if (board[r][c].getText() == board[3][0].getText() && board[3][0].getText() != "") {
                counter++;
            }
        }

        if (counter == 3) {
            for (int c = 0; c < 4; c++) {
                int r = 3 - c;
                setWinner(board[r][c]);
            }
            gameOver = true;
            return true;
        }
        return false;
    }

    boolean checkAntiLowerDiagonal() {
        int counter = 0;
        for (int c = 2; c < 5; c++) {
            int r = 5 - c;
            if (board[r][c].getText() == board[4][1].getText() && board[4][1].getText() != "") {
                counter++;
            }
        }

        if (counter == 3) {
            for (int c = 1; c < 5; c++) {
                int r = 5 - c;
                setWinner(board[r][c]);
            }
            gameOver = true;
            return true;
        }
        return false;
    }

    void checkDraw() {
        if (turns == 25) {
            gameOver = true;
            textLabel.setText("It's a draw!");
            for (int r = 0; r < 5; r++) {
                for (int i = 0; i < 5; i++) {
                    setDraw(board[r][i]);
                }
            }
        }
    }

    void setWinner(JButton tile) {
        tile.setForeground(Color.green);
        tile.setBackground(Color.gray);
        textLabel.setText(currentPlayer + " is the winner!");
    }

    void setDraw(JButton tile) {
        tile.setForeground(Color.orange);
        tile.setBackground(Color.gray);
    }

    void clearBoard() {
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                board[r][c].setText("");
                board[r][c].setBackground(Color.darkGray);
                board[r][c].setForeground(Color.white);
            }
        }
    }

}
