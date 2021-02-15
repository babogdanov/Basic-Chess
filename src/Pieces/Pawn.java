package Pieces;

import Chess.Board;
import Chess.Move;
import Chess.Player;
import Chess.Square;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    //Used to keep track if moving 2 squares ahead is legal (it's only legal on the first move of the given pawn).
    private int timesMoved;

    public Pawn(PieceColor pieceColor,int file,int rank){
        super(pieceColor,file,rank);
        super.getSquare().setPiece(this);
        this.setPieceSymbol('P');
        timesMoved = 0;
    }

    //The pawn is the only piece for which its color affects the direction it can move,
    //so essentially all the logic here is rewritten twice for white and black.
    @Override
    public void changeSquaresCovered(Board board) {

        for(int i = 0; i < this.getSquaresCovered().size();i++){
            this.getSquaresCovered().get(i).decrementColorCovering(this.getPieceColor());
        }
        this.getSquaresCovered().clear();
        Square currSquare;
        if(this.getPieceColor() == PieceColor.WHITE){
            currSquare = board.getSquare(this.getSquare().getFile()-1,this.getSquare().getRank()+1);
            if(currSquare != null && currSquare.isOnBoard(board)){
                this.getSquaresCovered().add(currSquare);
                board.getSquare(currSquare.getFile(),currSquare.getRank()).incrementColorCovering(this.getPieceColor());
            }
            currSquare = board.getSquare(this.getSquare().getFile()-1,this.getSquare().getRank()-1);
            if(currSquare != null && currSquare.isOnBoard(board)){
                this.getSquaresCovered().add(currSquare);
                board.getSquare(currSquare.getFile(),currSquare.getRank()).incrementColorCovering(this.getPieceColor());
            }
        }
        else{
            currSquare = board.getSquare(this.getSquare().getFile()+1,this.getSquare().getRank()-1);
            if(currSquare != null && currSquare.isOnBoard(board)){
                this.getSquaresCovered().add(currSquare);
                board.getSquare(currSquare.getFile(),currSquare.getRank()).incrementColorCovering(this.getPieceColor());
            }
            currSquare = board.getSquare(this.getSquare().getFile()+1,this.getSquare().getRank()+1);
            if(currSquare != null && currSquare.isOnBoard(board)){
                this.getSquaresCovered().add(currSquare);
                board.getSquare(currSquare.getFile(),currSquare.getRank()).incrementColorCovering(this.getPieceColor());
            }
        }
    }

    //TODO implement en passant and promotion.
    @Override
    public boolean canMove(Board board, Square start, Square end) {

        if (start.isOnBoard(board) && end.isOnBoard(board) && start.getPiece() == this
                && (end.isEmpty() || ((end.getPiece().getPieceColor() != this.getPieceColor())  && end.getPiece().getClass() != King.class))){



            /*A pawn can move 2 squares ahead(if both are empty) on its first move,
            or 1 square ahead(if it's empty) on every move. */
            if (end.isEmpty()) {

                if (this.getPieceColor() == PieceColor.WHITE) {
                    if (end.getRank() == start.getRank() && end.getFile() == start.getFile() - 1) {
                        timesMoved++;
                        return true;
                    }
                    if (end.getRank() == start.getRank() && end.getFile() == start.getFile() - 2) {
                        if (timesMoved == 0) {

                            Square between = board.getSquare(start.getFile() - 1, end.getRank());
                            if (between.isEmpty()) {
                                timesMoved++;
                                return true;

                            }
                        }
                    }
                } else {
                    if (end.getRank() == start.getRank() && end.getFile() == start.getFile() + 1) {
                        timesMoved++;
                        return true;
                    }
                    if (end.getRank() == start.getRank() && end.getFile() == start.getFile() + 2) {
                        if (timesMoved == 0) {
                            Square between = board.getSquare(start.getFile() + 1, end.getRank());
                            if (between.isEmpty()) {
                                timesMoved++;
                                return true;

                            }
                        }
                    }
                }
            }
            // A pawn can capture one square diagonally ahead.
            else {
                if (this.getPieceColor() == PieceColor.WHITE) {

                    if (end.getRank() == start.getRank() - 1 && end.getFile() == start.getFile() - 1) {
                        timesMoved++;
                        return true;
                    }

                    if (end.getRank() == start.getRank() + 1 && end.getFile() == start.getFile() - 1) {
                        timesMoved++;
                        return true;
                    }
                }
                     else {
                        if (end.getRank() == start.getRank() - 1 && end.getFile() == start.getFile() + 1) {
                            timesMoved++;
                            return true;
                        }
                        if (end.getRank() == start.getRank() + 1 && end.getFile() == start.getFile() + 1) {
                            timesMoved++;
                            return true;
                        }
                    }
                }
            }



        return false;
    }
    @Override
    public List<Move> canMoveAnywhere(Board board, Player player) {
        List<Move> validMoves = new ArrayList<>();
        if(timesMoved == 0){
            listNoAddNullUtil(board,validMoves,
                    new Move(player,this.getSquare(),
                            board.getSquare(this.getSquare().getFile()+2,this.getSquare().getRank())));
        }
        listNoAddNullUtil(board,validMoves,
                new Move(player,this.getSquare(),
                        board.getSquare(this.getSquare().getFile()+1,this.getSquare().getRank())));
        return validMoves;
    }

    public int getTimesMoved() {
        return timesMoved;
    }
    public void incrementTimesMoved(){
        this.timesMoved++;
    }
    public void decrementTimesMoved(){
        this.timesMoved--;
    }
}
