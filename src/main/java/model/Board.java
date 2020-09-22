package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private static final Board instance = new Board();
    private BoardMap squares;
    private Piece.Color currentPlayer = Piece.Color.White;
    private Coordinate selection;
    private HashMap<Piece.Color, King> kings;
    private final List<BoardListener> listeners = new ArrayList<>();

    private Board() {
        newGame();
    }

    public static Board getInstance() {
        return instance;
    }

    private void initBoard() {
        for (char file = 'a'; file <= 'h'; file++) {
            for (int row = 1; row <= 8; row++) {
                Coordinate c = new Coordinate(file, row);
                Square square = new Square((file + row) % 2 == 0 ? Square.Color.Dark : Square.Color.Light, c);
                squares.put(c, square);
            }
        }
    }

    private void initPieces() {
         // On garde toujours une référence sur les rois
        kings.put(Piece.Color.White, new King(Piece.Color.White));
        kings.put(Piece.Color.Black, new King(Piece.Color.Black));

        // White pieces
        squares.get("a1").setPiece(new Rook(Piece.Color.White));
        squares.get("b1").setPiece(new Knight(Piece.Color.White));
        squares.get("c1").setPiece(new Bishop(Piece.Color.White));
        squares.get("d1").setPiece(new Queen(Piece.Color.White));
        squares.get("e1").setPiece(kings.get(Piece.Color.White));
        squares.get("f1").setPiece(new Bishop(Piece.Color.White));
        squares.get("g1").setPiece(new Knight(Piece.Color.White));
        squares.get("h1").setPiece(new Rook(Piece.Color.White));

        for (char file = 'a'; file <= 'h'; file++) {
            squares.get(new Coordinate(file, 2)).setPiece(new Pawn(Piece.Color.White));
        }

        // Black pieces
        squares.get("a8").setPiece(new Rook(Piece.Color.Black));
        squares.get("b8").setPiece(new Knight(Piece.Color.Black));
        squares.get("c8").setPiece(new Bishop(Piece.Color.Black));
        squares.get("d8").setPiece(new Queen(Piece.Color.Black));
        squares.get("e8").setPiece(kings.get(Piece.Color.Black));
        squares.get("f8").setPiece(new Bishop(Piece.Color.Black));
        squares.get("g8").setPiece(new Knight(Piece.Color.Black));
        squares.get("h8").setPiece(new Rook(Piece.Color.Black));

//        squares.get("e1").setPiece(kings.get(Piece.Color.White));
//        squares.get("e2").setPiece(new Rook(Piece.Color.White));
//        squares.get("f1").setPiece(new Queen(Piece.Color.White));
//        squares.get("b7").setPiece(new Pawn(Piece.Color.White));
//
//        squares.get("a8").setPiece(kings.get(Piece.Color.Black));
//        squares.get("e8").setPiece(new Rook(Piece.Color.Black));
//        squares.get("d8").setPiece(new Rook(Piece.Color.Black));
//        squares.get("g3").setPiece(new Bishop(Piece.Color.Black));
//        squares.get("f6").setPiece(new Queen(Piece.Color.Black));

        for (char file = 'a'; file <= 'h'; file++) {
            squares.get(new Coordinate(file, 7)).setPiece(new Pawn(Piece.Color.Black));
        }
    }

    public void newGame() {
        currentPlayer = Piece.Color.White;
        selection = null;
        kings = new HashMap<>();
        squares = new BoardMap();

        initBoard();
        initPieces();

        // Notify
        for (BoardListener listener : listeners) {
            listener.newGame();
        }
    }

    public Piece.Color getCurrentPlayer() {
        return currentPlayer;
    }

    public void nextPlayer() {
        currentPlayer = currentPlayer.toggle();

        // Notify
        for (BoardListener listener : listeners) {
            listener.playerChanged(currentPlayer);
        }

        if (isInCheck(currentPlayer)) {
            if (isInCheckmate(currentPlayer)) {
                // Notify
                for (BoardListener listener : listeners) {
                    listener.playerInCheckmate(currentPlayer);
                }
            }
            else {
                // Notify
                for (BoardListener listener : listeners) {
                    listener.playerInCheck(currentPlayer);
                }
            }
        }
    }

    public void movePiece(Coordinate from, Coordinate to) {
        squares.movePiece(from, to);
        selection = null;
        nextPlayer();
    }

    public boolean isEmpty(Coordinate c) {
        return squares.get(c).isEmpty();
    }

    public Piece getPiece(Coordinate c) {
        return squares.get(c).getPiece();
    }

    public Square getSquare(Coordinate c) {
        return squares.get(c);
    }

    public Coordinate getSelection() {
        return selection;
    }

    public void setSelection(Coordinate selection) {
        // Si le joueur a cliqué sur la pièce déjà sélectionnée ou une case vide ou une pièce adverse,
        // on annule la sélection
        if (this.selection != null && this.selection.equals(selection) ||
                isEmpty(selection) ||
                getPiece(selection).getColor() != getCurrentPlayer()) {
            selection = null;
        }

        this.selection = selection;
    }

    /**
     * Est-ce que le joueur en paramètre est en échec ?
     * @return boolean
     */
    public boolean isInCheck(Piece.Color player) {
        Coordinate kingCoordinate = kings.get(player).getCoordinate();

        for (Map.Entry<String, Square> entry : squares.entrySet()) {
            Square square = entry.getValue();
            Piece piece = square.getPiece();
            if (piece != null && piece.getColor() != player && piece.canMoveTo(kingCoordinate)) {
                return true;
            }
        }

        return false;
    }

    // TODO gestion des nuls
    //  -stalemate : plus de move et pas en échec
    //  -dead position : seulement 2 rois, ou seulement 2 rois et 1 seul cavalier

    public BoardMap getSquares() {
        return squares;
    }

    /**
     * Est-ce que le joueur est échec et mat ?
     * (en prenant en compte qu'il soit déjà en échec)
     *
     * @param player Joueur à vérifier
     * @return boolean
     */
    public boolean isInCheckmate(Piece.Color player) {
        // Si le roi ne peut pas bouger => pas mat
        if (!kings.get(player).getPossibleMoves().isEmpty()) {
            return false;
        }

        // Récupération des pièces adverses qui attaquent le roi
        Coordinate kingCoordinate = kings.get(player).getCoordinate();
        ArrayList<Piece> attackers = new ArrayList<>();
        for (Map.Entry<String, Square> entry : squares.entrySet()) {
            Square square = entry.getValue();
            Piece piece = square.getPiece();
            if (piece != null && piece.getColor() != player && piece.canMoveTo(kingCoordinate)) {
                attackers.add(piece);
            }
        }

        // Si qu'un seul attaquant
        if (attackers.size() == 1) {
            Piece attacker = attackers.get(0);
            Coordinate attackerCoordinate = attacker.getCoordinate();

            for (Map.Entry<String, Square> entry : squares.entrySet()) {
                Square square = entry.getValue();
                Piece piece = square.getPiece();

                // Pour chacune des pièces du joueur
                if (piece != null && piece.getColor() == player) {
                    // Si l'attaquant peut être pris (en prenant en compte les clouages) => pas mat
                    if (piece.getPossibleMoves().contains(attackerCoordinate)) {
                        return false;
                    }
                    // Sinon si l'attaquant n'est pas cavalier
                    if (attacker.getClass() != Knight.class) {
                        // Si la pièce peut se mettre entre l'attaquant et le roi => pas mat
                        ArrayList<Coordinate> possibleMoves = piece.getPossibleMoves();
                        ArrayList<Coordinate> squaresBetween = attackerCoordinate.between(kingCoordinate);
                        possibleMoves.retainAll(squaresBetween);
                        if (!possibleMoves.isEmpty()) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    public King getKing(Piece.Color color) {
        return kings.get(color);
    }

    public void addListener(BoardListener listener) {
        listeners.add(listener);
    }
}
