package Pieces;

import Chess.Board;
import Chess.Player;
import Chess.Square;
import Chess.Move;
import java.util.ArrayList;
import java.util.List;

public abstract class Piece {


    //A piece knows its square and a square knows its piece
    // so that every single operation that needs to be done can be done in O(1).
    private Square square;

    //Every piece keeps track of the squares it covers,
    // and every square keeps track of the number of pieces covering it for similar reasons as above.
    private List<Square> squaresCovered;

    private PieceColor pieceColor;
    //Used to represent the piece in this console visualisation.
    private char PieceSymbol;




    //Called after every move to calculate the squares a given piece is controlling.
    public abstract void changeSquaresCovered(Board board);
    //Used to validate the move input as per the given piece's rules of movement.
    public abstract boolean canMove(Board board, Square start, Square end);
    //Looks for any possible moves a piece can make- used to determine the state of the game
    //when the king can't move.
    public abstract List<Move> canMoveAnywhere(Board board, Player player);

    //Helper function to avoid adding squares that are out of bounds.
    public void listNoAddNullUtil(Board board,List<Move> moves, Move move){
        if(move.getStart() == null || move.getEnd() == null
                ||!move.getStart().isOnBoard(board) || !move.getEnd().isOnBoard(board)) {
            return;
        }
        moves.add(move);
    }
    //Used whenever a piece is captured.
    public void freeCoveredSquares(Board board){
        for (Square square: this.getSquaresCovered()) {
            square.decrementColorCovering(this.getPieceColor());
        }
    }
    public Piece(PieceColor pieceColor,int file, int rank) {
        this.setPieceColor(pieceColor);
        this.squaresCovered = new ArrayList<>();
        this.square = new Square(file,rank,null);
    }

    public PieceColor getPieceColor() {
        return pieceColor;
    }

    public void setPieceColor(PieceColor pieceColor) {
        this.pieceColor = pieceColor;
    }



    public char getPieceSymbol() {
        return PieceSymbol;
    }

    public void setPieceSymbol(char pieceSymbol) {
        PieceSymbol = pieceSymbol;
    }

    public void setSquare(Square square){
        this.square = square;
    }
    public Square getSquare() {
        return square;
    }



    public List<Square> getSquaresCovered() {
        return squaresCovered;
    }


}
