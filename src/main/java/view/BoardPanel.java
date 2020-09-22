package view;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class BoardPanel extends JPanel implements MouseListener, BoardListener {

    public BoardPanel() {
        addMouseListener(this);
        Board.getInstance().addListener(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        for (int row = 8; row >= 1; row--) {
            for (char file = 'a'; file <= 'h'; file++) {
                Coordinate c = new Coordinate(file, row);

                // Square
                Square square = Board.getInstance().getSquare(c);
                SquareView rect = new SquareView(square);
                g2.setPaint(rect.getColor());
                g2.fill(rect);

                // Piece
                Piece piece = Board.getInstance().getPiece(c);
                if (piece != null) {
                    g2.drawImage(piece.getImage(), (int)rect.getX() + 2, (int)rect.getY() + 2, 36, 36, this);
                }
            }
        }

        // Affichage des cases possibles
        Coordinate selection = Board.getInstance().getSelection();
        if (selection != null) {
            Piece piece = Board.getInstance().getPiece(selection);
            showPossibleSquares(piece, g2);
        }

        g2.dispose();
    }

    private void showPossibleSquares(Piece piece, Graphics2D g2) {
        g2.setColor(new Color(50, 168, 82, 70));

        ArrayList<Coordinate> possibleSquares = piece.getPossibleMoves();
        for (Coordinate c : possibleSquares) {
            g2.fillOval(SquareView.file2X(c.getFile()) + 10, SquareView.row2Y(c.getRow()) + 10, 20, 20);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        char file = SquareView.x2File(e.getX());
        int row = SquareView.y2Row(e.getY());
        Coordinate clickedSquare = new Coordinate(file, row);

        if (clickedSquare.isValid()) {
            // Si une pièce est déjà sélectionnée et que la nouvelle case cliquée est un mouvement possible
            Coordinate selection = Board.getInstance().getSelection();
            if (selection != null && Board.getInstance().getPiece(selection).canMoveTo(clickedSquare)) {
                Board.getInstance().movePiece(selection, clickedSquare);
            }
            // Sinon on sélectionne ou déselectionne une case
            else {
                Board.getInstance().setSelection(new Coordinate(file, row));
            }

            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void playerChanged(Piece.Color color) {

    }

    @Override
    public void playerInCheck(Piece.Color color) {

    }

    @Override
    public void playerInCheckmate(Piece.Color color) {

    }

    @Override
    public void newGame() {
        repaint();
    }
}
