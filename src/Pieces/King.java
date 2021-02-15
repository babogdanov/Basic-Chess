package Pieces;

import Chess.Board;
import Chess.Move;
import Chess.Player;
import Chess.Square;

import java.util.List;

public class King extends Piece{


    public King(PieceColor pieceColor, int file,int rank){
        super(pieceColor,file,rank);
        super.getSquare().setPiece(this);
        this.setPieceSymbol('K');

    }

    @Override
    public void changeSquaresCovered(Board board) {

        for(int i = 0; i < this.getSquaresCovered().size();i++){
            this.getSquaresCovered().get(i).decrementColorCovering(this.getPieceColor());
        }

        this.getSquaresCovered().clear();
        for(int i = this.getSquare().getFile()-1; i <= this.getSquare().getFile() + 1; i++){
            for(int j = this.getSquare().getRank()-1; j <= this.getSquare().getRank()+1;j++){
                //Ignore the square the king is currently on.
                if(  (i == this.getSquare().getFile()) && (j == this.getSquare().getRank()))
                {
                    continue;
                }

                if(board.getSquare(i,j) != null && board.getSquare(i,j).isOnBoard(board)){
                    this.getSquaresCovered().add(board.getSquare(i,j));
                    board.getSquare(i,j).incrementColorCovering(this.getPieceColor());
                }
            }
        }
    }


    @Override
    public boolean canMove(Board board, Square start, Square end) {
        if(start.isOnBoard(board) && end.isOnBoard(board)){

                    int endFile = end.getFile();
                    int endRank = end.getRank();
                    if(start.getPiece() == this && endRank >= start.getRank() - 1 && endRank <= start.getRank() +1
                    && endFile >= start.getFile()-1 && endRank <= start.getRank() +1
                            && !(start.getRank() == endRank && start.getFile() == endFile)) {
                        //Empty square- all is well.
                        if(board.getSquare(endFile,endRank).isEmpty()){

                            return true;
                        }
                        //Occupied by a friendly piece - illegal move.
                        else if(board.getSquare(endFile,endRank).getPiece().getPieceColor() == this.getPieceColor()){
                            return false;
                        }
                        //Occupied by enemy piece - have to check if that square is covered by other enemy pieces
                        // (that would make it illegal to move the king there).
                        else {
                            return board.getSquare(endFile,endRank).getCoveredByOppositeColor(this.getPieceColor()) < 1;
                        }
                    }
        }
        return false;
    }

    //This functionality is implemented in the Board class.
    @Override
    public List<Move> canMoveAnywhere(Board board, Player player) {
        return null;
    }
}
