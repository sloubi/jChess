package org.sloubi.view;

import org.sloubi.model.Board;
import org.sloubi.model.BoardListener;
import org.sloubi.model.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Sidebar extends JPanel implements BoardListener {

    private final JLabel player = new JLabel("Trait au Blanc");
    private final JLabel state = new JLabel();
    private final JButton newGameButton = new JButton("Nouvelle partie");
    private final JButton aboutButton = new JButton("?");

    public Sidebar() {
        JPanel texts = new JPanel();
        texts.setLayout(new GridLayout(2, 1));
        texts.add(player);
        texts.add(state);

        player.setFont(player.getFont().deriveFont(Font.PLAIN, 16));
        state.setFont(state.getFont().deriveFont(Font.BOLD, 16));

        Board.getInstance().addListener(this);

        newGameButton.addActionListener(new ButtonListener());
        newGameButton.setPreferredSize(new Dimension(140, 40));
        newGameButton.setFont(newGameButton.getFont().deriveFont(Font.PLAIN, 16));

        aboutButton.addActionListener(new ButtonListener());
        aboutButton.setPreferredSize(new Dimension(40, 40));
        aboutButton.setFont(aboutButton.getFont().deriveFont(Font.PLAIN, 16));

        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        setPreferredSize(new Dimension(250, 100));

        JPanel buttons = new JPanel();
        buttons.add(newGameButton);
        buttons.add(aboutButton);

        setLayout(new BorderLayout());
        add(texts, BorderLayout.NORTH);
        add(buttons, BorderLayout.SOUTH);
    }

    @Override
    public void playerChanged(Piece.Color color) {
        player.setText("Trait au " + color.translate());
        state.setText("");
    }

    @Override
    public void playerInCheck(Piece.Color color) {
        state.setText(color.translate() + " est en échec.");
    }

    @Override
    public void playerInCheckmate(Piece.Color color) {
        player.setText("");
        state.setText(color.translate() + " est échec et mat.");
    }

    @Override
    public void newGame() {

    }

    class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(newGameButton))
                Board.getInstance().newGame();
            else if (e.getSource().equals(aboutButton)) {
                AboutDialog ad = new AboutDialog();
                ad.setVisible(true);
            }
        }
    }
}
