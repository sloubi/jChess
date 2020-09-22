import model.Board;
import model.Coordinate;
import model.Pawn;
import model.Piece;
import view.MainFrame;

public class MainClass {
    public static void main(String[] args) {
        new MainFrame();

//        Piece piece = Board.getInstance().getPiece(new Coordinate('e', 1));
//        System.out.println(Board.getInstance().getPiece(new Coordinate('e', 1)));
//        Board.getInstance().movePiece(new Coordinate("b7"), new Coordinate("b8"));
//        System.out.println(Board.getInstance().getPiece(new Coordinate("b8")));

        // test clouage => seulement colonne E autorisée
//        System.out.println(Board.getInstance().getPiece(new Coordinate('e', 2)).getPossibleMoves());

        // test mise en echec, colonne D interdite
//        System.out.println(Board.getInstance().getPiece(new Coordinate('e', 1)).getPossibleMoves());


//        Coordinate a = new Coordinate("a1");
//        Coordinate b = new Coordinate("d1");
//        System.out.println(a.between(b)); // b1,c1
//        System.out.println(b.between(a)); // b1,c1
//
//        Coordinate c = new Coordinate("a1");
//        Coordinate d = new Coordinate("a4");
//        System.out.println(c.between(d));//a2, a3
//        System.out.println(d.between(c));//a2, a3
//
//        Coordinate e = new Coordinate("a1");
//        Coordinate f = new Coordinate("c4");
//        System.out.println(e.between(f));//rien
//        System.out.println(f.between(e));//rien
//
//        Coordinate g = new Coordinate("a1");
//        Coordinate h = new Coordinate("d4");
//        System.out.println(g.between(h));//b2, c3
//        System.out.println(h.between(g));//b2, c3
    }
}
