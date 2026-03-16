package StartGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import board.Board;
import datastructures.Vector2D;
import pieces.King;
import pieces.Piece;

public class GameWindow extends JFrame {
    private Board gameBoard;
    private JPanel boardPanel;
    private JButton[][] squares = new JButton[8][8];
    private ImageLoader iconBank;
    private Vector2D selectedSquare = null;
    private String currentTurn = "White"; // White usually starts

    public GameWindow() {
        //Setup Window
        setTitle("Reverse Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);

        //Initialize the Logic
        gameBoard = new Board();
        //Call a method here to place the pieces on the board
        iconBank = new ImageLoader("sprites/pcqrGKzLi.png", 75);

        //Setup UI Grid
        boardPanel = new JPanel(new GridLayout(8, 8));
        initializeGrid();
        refreshUI();

        add(boardPanel);
        setVisible(true);
    }

    public void refreshUI() {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Piece p = gameBoard.getPiece(new Vector2D(x, y));
                JButton btn = squares[x][y];

                if (p != null) {
                    // p.getType() should return "king", "queen", etc.
                    btn.setIcon(iconBank.getIcon(p.getColor(), p.getType()));
                } else {
                    btn.setIcon(null);
                }
            }
        }
    }

    private void handleSquareClick(int x, int y) {
        Vector2D clickedPos = new Vector2D(x, y);
        Piece clickedPiece = gameBoard.getPiece(clickedPos);

        if (selectedSquare == null) {
            //FIRST CLICK: Selection
            if (clickedPiece != null) {
                if (clickedPiece.getColor().equalsIgnoreCase(currentTurn)) {
                    selectedSquare = clickedPos;
                    highlightSquare(x, y, Color.YELLOW);
                } else {
                    System.out.println("It is " + currentTurn + "'s turn!");
                }
            }
        } else {
            //SECOND CLICK: Movement
            //Check for double-click to deselect
            if (selectedSquare.getX() == x && selectedSquare.getY() == y) {
                selectedSquare = null;
                resetBoardColors();
                return;
            }

            Piece activePiece = gameBoard.getPiece(selectedSquare);

            //Validate move before moving
            if (activePiece != null && isMoveLegal(activePiece, clickedPos)) {

                //Handle special moves vs standard moves
                if (activePiece instanceof King && Math.abs(clickedPos.getX() - selectedSquare.getX()) == 2) {
                    gameBoard.executeCastle(selectedSquare, clickedPos);
                } else {
                    gameBoard.Move(selectedSquare, clickedPos);
                }

                // 4. Switch Turns (Standard If-Else)
                if (currentTurn.equals("White")) {
                    currentTurn = "Black";
                } else {
                    currentTurn = "White";
                }

                System.out.println("Successful move! Turn: " + currentTurn);
            } else {
                System.out.println("Invalid Move!");
            }

            //Cleanup
            selectedSquare = null;
            resetBoardColors();
            refreshUI();
        }
    }

    private void highlightSquare(int x, int y, Color color) {
        squares[x][y].setBackground(color);
    }

    private boolean isMoveLegal(Piece p, Vector2D destination) {
        java.util.List<Vector2D> legalMoves = p.possibleMoves(gameBoard);

        for (Vector2D move : legalMoves) {
            if (move.getX() == destination.getX() && move.getY() == destination.getY()) {
                return true;
            }
        }
        return false;
    }

    private void resetBoardColors() {
        Color chessGreen = new Color(118, 150, 86);
        Color lightSquare = new Color(238, 238, 210);

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if ((x + y) % 2 == 0) {
                    squares[x][y].setBackground(chessGreen);
                } else {
                    squares[x][y].setBackground(lightSquare);
                }
            }
        }
    }

    private void initializeGrid() {
        Color chessGreen = new Color(118, 150, 86);
        Color lightSquare = new Color(238, 238, 210);

        for (int y = 7; y >= 0; y--) {
            for (int x = 0; x < 8; x++) {
                JButton button = new JButton();

                if ((x + y) % 2 == 0) {
                    button.setBackground(chessGreen);
                } else {
                    button.setBackground(lightSquare);
                }

                button.setOpaque(true);
                button.setBorderPainted(false);

                // Store coordinates in final variables so the inner class can see them
                final int finalX = x;
                final int finalY = y;

                // The "Old School" way to add a click listener
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handleSquareClick(finalX, finalY);
                    }
                });

                squares[x][y] = button;
                boardPanel.add(button);
            }
        }
    }
}

