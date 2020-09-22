package view;

import model.Board;
import model.BoardListener;
import model.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Sidebar extends JPanel implements BoardListener {

    private final JLabel player = new JLabel("Trait au Blanc");
    private final JLabel state = new JLabel();
    private final JButton newGameButton = new JButton("Nouvelle partie");

    public Sidebar() {
        JPanel texts = new JPanel();
        texts.setLayout(new GridLayout(2, 1));
        texts.add(player);
        texts.add(state);

        setLayout(new BorderLayout());
        add(texts, BorderLayout.NORTH);
        add(newGameButton, BorderLayout.SOUTH);

        Board.getInstance().addListener(this);
        newGameButton.addActionListener(new ButtonListener());
    }

    @Override
    public void playerChanged(Piece.Color color) {
        player.setText("Trait au " + color.toString());
        state.setText("");
    }

    @Override
    public void playerInCheck(Piece.Color color) {
        state.setText(color.toString() + " est en échec.");
    }

    @Override
    public void playerInCheckmate(Piece.Color color) {
        player.setText("");
        state.setText(color.toString() + " est échec et mat.");
    }

    @Override
    public void newGame() {

    }

    class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Board.getInstance().newGame();
        }
    }
}
