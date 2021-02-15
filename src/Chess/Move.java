package Chess;

import Pieces.Piece;

// Just used as a container for the two queried squares that make up a move.
public class Move {
    private Player player;
    private Square start;
    private Square end;
    private Piece pieceToMove;


    public Move(Player player, Square start, Square end) {
        this.player = player;
        this.start = start;
        this.end = end;
        this.pieceToMove = start.getPiece();
    }



    public Player getPlayer() {
        return player;
    }

    public Square getStart() {
        return start;
    }

    public Square getEnd() {
        return end;
    }

    public Piece getPieceToMove() {
        return pieceToMove;
    }


}
