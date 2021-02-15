package Pieces;

import Chess.Board;
import Chess.Move;
import Chess.Player;
import Chess.Square;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece{
    public Rook(PieceColor pieceColor, int file,int rank){
        super(pieceColor,file,rank);
        super.getSquare().setPiece(this);
        this.setPieceSymbol('R');

    }



    @Override
    public void changeSquaresCovered(Board board) {

        for (Square square: this.getSquaresCovered()) {

            square.decrementColorCovering(this.getPieceColor());
        }


        this.getSquaresCovered().clear();
        int file = this.getSquare().getFile();
        int rank = this.getSquare().getRank();
        for(int i = rank+1; i < board.getDimensions();i++){
            if(board.getSquare(file,i).isEmpty() == false && board.getSquare(file,i).getPiece().getClass() != King.class){
                this.getSquaresCovered().add(board.getSquare(file,i));
                board.getSquare(file,i).incrementColorCovering(this.getPieceColor());
                break;
            }
            this.getSquaresCovered().add(board.getSquare(file,i));
            board.getSquare(file,i).incrementColorCovering(this.getPieceColor());
        }
        for(int i = rank-1;i >= 0; i--){
            if(board.getSquare(file,i).isEmpty() == false && board.getSquare(file,i).getPiece().getClass() != King.class){
                this.getSquaresCovered().add(board.getSquare(file,i));
                board.getSquare(file,i).incrementColorCovering(this.getPieceColor());
                break;
            }
            this.getSquaresCovered().add(board.getSquare(file,i));
            board.getSquare(file,i).incrementColorCovering(this.getPieceColor());
        }
        for(int i = file+1; i < board.getDimensions();i++){
            if(board.getSquare(i,rank).isEmpty() == false && board.getSquare(i,rank).getPiece().getClass() != King.class){
                this.getSquaresCovered().add(board.getSquare(i,rank));
                board.getSquare(i,rank).incrementColorCovering(this.getPieceColor());
                break;
            }
            this.getSquaresCovered().add(board.getSquare(i,rank));
            board.getSquare(i,rank).incrementColorCovering(this.getPieceColor());
        }
        for(int i = file-1;i >= 0; i--){
            if(board.getSquare(i,rank).isEmpty() == false && board.getSquare(i,rank).getPiece().getClass() != King.class){
                this.getSquaresCovered().add(board.getSquare(i,rank));
                board.getSquare(i,rank).incrementColorCovering(this.getPieceColor());
                break;
            }
            this.getSquaresCovered().add(board.getSquare(i,rank));
            board.getSquare(i,rank).incrementColorCovering(this.getPieceColor());
        }
    }
    @Override
    public boolean canMove(Board board, Square start, Square end) {
        if(start.isOnBoard(board) && end.isOnBoard(board)) {
            if(start.getPiece() == this && (end.isEmpty() || end.getPiece().getPieceColor() != this.getPieceColor())){
                //XOR to ignore the current square
                if(end.getFile() == this.getSquare().getFile() ^ end.getRank() == this.getSquare().getRank()){

                    if(end.getFile() == this.getSquare().getFile()){
                        if(end.getRank() > this.getSquare().getRank()){
                            for(int i = this.getSquare().getRank() + 1; i < end.getRank()&& i < board.getDimensions() ;i++){
                                if(!board.getSquare(this.getSquare().getFile(),i).isEmpty()){
                                    return false;
                                }
                            }
                        }else{
                            for(int i = end.getRank() + 1; i <end.getRank() && i < board.getDimensions() ;i++){
                                if(!board.getSquare(this.getSquare().getFile(),i).isEmpty()){
                                    return false;
                                }
                            }
                        }
                    }
                    else{

                            if(end.getFile() > this.getSquare().getFile()){
                                for(int i = this.getSquare().getFile() + 1; i < end.getFile()&& i < board.getDimensions() ;i++){
                                    if(!board.getSquare(i,this.getSquare().getRank()).isEmpty()){
                                        return false;
                                    }
                                }
                            }
                            else{
                                for(int i = end.getFile() + 1; i < this.getSquare().getFile() && i < board.getDimensions() ;i++){
                                    if(!board.getSquare(i,this.getSquare().getRank()).isEmpty()){
                                        return false;
                                    }
                                }
                            }


                }
                    return true;
                }
        }

    }
        return false;
    }

    @Override
    public List<Move> canMoveAnywhere(Board board, Player player) {
        List<Move> validMoves = new ArrayList<>();
        int file = this.getSquare().getFile();
        int rank = this.getSquare().getRank();

        for(int i = file; i < board.getDimensions();i++){
            listNoAddNullUtil(board,validMoves,new Move(player,this.getSquare(),board.getSquare(i,rank)));
        }
        for(int i = file; i >= 0;i--){
            listNoAddNullUtil(board,validMoves,new Move(player,this.getSquare(),board.getSquare(i,rank)));
        }
        for(int i = rank; i < board.getDimensions();i++){
            listNoAddNullUtil(board,validMoves,new Move(player,this.getSquare(),board.getSquare(file,i)));
        }
        for(int i = rank; i >= 0; i--){
            listNoAddNullUtil(board,validMoves,new Move(player,this.getSquare(),board.getSquare(file,i)));
        }


        return validMoves;
    }
}
