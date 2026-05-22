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

                // PREVENT MOVING INTO CHECK
                if (putsOwnKingInCheck(activePiece, selectedSquare, clickedPos)) {
                    System.out.println("Invalid Move: You cannot move into check!");
                    selectedSquare = null;
                    resetBoardColors();
                    return; // Stop the code here so the move doesn't happen
                }
                //En-passant
                if (activePiece instanceof pieces.Pawn) {
                    Vector2D epTarget = gameBoard.getEnPassantTargetSquare();

                    // If an En Passant target exists, and the user clicked it, and the square is empty...
                    if (epTarget != null && clickedPos.getX() == epTarget.getX() && clickedPos.getY() == epTarget.getY()
                            && gameBoard.getPiece(clickedPos) == null) {

                        // The victim pawn is on our same row (selectedSquare.getY()) but the clicked column (x)
                        Vector2D victimPos = new Vector2D(x, selectedSquare.getY());
                        gameBoard.setPiece(victimPos, null); // Clear the captured pawn from the board!
                        System.out.println("En Passant Executed!");
                    }
                }

                // Check for Castling
                if (activePiece instanceof King && Math.abs(clickedPos.getX() - selectedSquare.getX()) == 2) {
                    gameBoard.executeCastle(selectedSquare, clickedPos);
                } else {
                    //Standard Move
                    gameBoard.Move(selectedSquare, clickedPos);

                    //PROMOTION CHECK
                    if (activePiece instanceof pieces.Pawn) {
                        pieces.Pawn p = (pieces.Pawn) activePiece;
                        if (p.isPromotionMove(clickedPos)) {
                            triggerPromotionPopup(clickedPos, p.getColor());
                        }
                    }
                }

                //Switch Turns
                if (currentTurn.equals("White")) {
                    currentTurn = "Black";
                } else {
                    currentTurn = "White";
                }

                System.out.println("Successful move! Turn: " + currentTurn);

                // CHECK FOR GAME OVER
                checkGameEndConditions();

            } else {
                System.out.println("Invalid Move!");
            }
            //Cleanup
            selectedSquare = null;
            resetBoardColors();
            refreshUI();
        }
    }

    private void triggerPromotionPopup(Vector2D pos, String color) {
        // 1. Prepare the Icons from your ImageLoader
        Icon qIcon = iconBank.getIcon(color, "Queen");
        Icon rIcon = iconBank.getIcon(color, "Rook");
        Icon nIcon = iconBank.getIcon(color, "Knight");
        Icon bIcon = iconBank.getIcon(color, "Bishop");

        // 2. Put them in an array (the order here determines the button order)
        Object[] options = {qIcon, rIcon, nIcon, bIcon};

        // 3. Show the dialog with icons instead of text
        int selection = JOptionPane.showOptionDialog(
                this,
                "Select your promotion piece:",
                "Pawn Promotion",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, // Plain message removes the default '?' icon
                null,
                options,
                options[0]
        );

        //Map the selection index back to the type string
        String choice;
        if (selection == 1) {
            choice = "Rook";
        } else if (selection == 2) {
            choice = "Knight";
        } else if (selection == 3) {
            choice = "Bishop";
        } else {
            choice = "Queen"; //Default to Queen if index 0 or closed
        }

        executePromotion(pos, choice, color);
    }

    private void executePromotion(Vector2D loc, String type, String color) {
        Piece newPiece;

        if (type.equals("Rook")) {
            newPiece = new pieces.Rook(color, loc);
        } else if (type.equals("Knight")) {
            newPiece = new pieces.Knight(color, loc);
        } else if (type.equals("Bishop")) {
            newPiece = new pieces.Bishop(color, loc);
        } else {
            newPiece = new pieces.Queen(color, loc);
        }

        //Update the gameBoard directly
        gameBoard.setPiece(loc, newPiece);
    }

    private void highlightSquare(int x, int y, Color color) {
        squares[x][y].setBackground(color);
    }

    private boolean isMoveLegal(Piece p, Vector2D destination) {
        return p.possibleMoves(gameBoard).contains(destination);
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

    private boolean putsOwnKingInCheck(Piece piece, Vector2D start, Vector2D end) {
        // Save the state of the destination square (in case we capture a piece)
        Piece capturedPiece = gameBoard.getPiece(end);

        // Simulate the move on the board
        gameBoard.setPiece(end, piece);
        gameBoard.setPiece(start, null);

        // Scan the board to see if our king is now in danger
        boolean inCheck = isKingInCheck(piece.getColor());

        // Undo the simulation
        gameBoard.setPiece(start, piece);
        gameBoard.setPiece(end, capturedPiece);

        return inCheck;
    }

    private boolean isKingInCheck(String color) {
        Vector2D kingPos = null;

        // Find this color's King
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Piece p = gameBoard.getPiece(new Vector2D(x, y));
                if (p instanceof King && p.getColor().equalsIgnoreCase(color)) {
                    kingPos = new Vector2D(x, y);
                    break;
                }
            }
        }
        if (kingPos == null) return false;

        // Check if any enemy piece can move to the King's square
        String enemyColor = color.equalsIgnoreCase("White") ? "Black" : "White";
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Piece p = gameBoard.getPiece(new Vector2D(x, y));
                if (p != null && p.getColor().equalsIgnoreCase(enemyColor)) {
                    if (p.possibleMoves(gameBoard).contains(kingPos)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void checkGameEndConditions() {
        int myPieceCount = 0;
        boolean hasAnyLegalMoves = false;

        // Scan the board for the current turn's player
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Vector2D pos = new Vector2D(x, y);
                Piece p = gameBoard.getPiece(pos);

                if (p != null && p.getColor().equalsIgnoreCase(currentTurn)) {
                    myPieceCount++;

                    // See if this piece has any moves that don't result in check
                    for (Vector2D move : p.possibleMoves(gameBoard)) {
                        if (!putsOwnKingInCheck(p, pos, move)) {
                            hasAnyLegalMoves = true;
                        }
                    }
                }
            }
        }

        // Win Condition 1: Lost all pieces (Or only the King is left = 1)
        if (myPieceCount <= 1) {
            triggerEndScreen(currentTurn + " Wins!", "Lost all pieces!");
            return;
        }

        // Win Condition 2: Checkmated (In check, and zero legal moves)
        if (isKingInCheck(currentTurn) && !hasAnyLegalMoves) {
            triggerEndScreen(currentTurn + " Wins!", "Successfully achieved Checkmate!");
        }
    }

    private void triggerEndScreen(String title, String reason) {
        // Wipe the chessboard out of the window
        this.getContentPane().removeAll();

        // Create the endgame UI
        JPanel endPanel = new JPanel();
        endPanel.setLayout(new BoxLayout(endPanel, BoxLayout.Y_AXIS));
        endPanel.setBackground(new Color(30, 30, 30));

        JLabel winTitle = new JLabel(title);
        winTitle.setFont(new Font("Arial", Font.BOLD, 48));
        winTitle.setForeground(Color.GREEN);
        winTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subTitle = new JLabel(reason);
        subTitle.setFont(new Font("Arial", Font.PLAIN, 24));
        subTitle.setForeground(Color.LIGHT_GRAY);
        subTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        endPanel.add(Box.createVerticalStrut(250)); // Push down from top
        endPanel.add(winTitle);
        endPanel.add(Box.createVerticalStrut(20));
        endPanel.add(subTitle);

        this.add(endPanel);
        this.revalidate();
        this.repaint();
    }
}