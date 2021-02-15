package Pieces;

import Chess.Board;
import Chess.Move;
import Chess.Player;
import Chess.Square;

import java.util.ArrayList;
import java.util.List;

//The queen essentially combines the movement of a rook and bishop, so all the logic here is
//the union of the logic you'll find in the Rook and Bishop classes.
public class Queen extends Piece {

    public Queen(PieceColor pieceColor, int file, int rank) {
        super(pieceColor, file, rank);
        this.getSquare().setPiece(this);
        this.setPieceSymbol('Q');
    }

    @Override
    public void changeSquaresCovered(Board board) {
        //Same logic as the rook piece
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

        //Same logic as the bishop piece

        for (int i = file + 1, j = rank + 1; i < board.getDimensions() && j < board.getDimensions(); i++, j++) {
            if (board.getSquare(i, j).isEmpty() == false && board.getSquare(i,j).getPiece().getClass() != King.class) {
                this.getSquaresCovered().add(board.getSquare(i, j));
                board.getSquare(i, j).incrementColorCovering(this.getPieceColor());
                break;
            }
            this.getSquaresCovered().add(board.getSquare(i, j));
            board.getSquare(i, j).incrementColorCovering(this.getPieceColor());
        }
        for (int i = file + 1, j = rank - 1; i < board.getDimensions() && j >= 0; i++, j--) {
            if (board.getSquare(i, j).isEmpty() == false && board.getSquare(i,j).getPiece().getClass() != King.class) {
                this.getSquaresCovered().add(board.getSquare(i, j));
                board.getSquare(i, j).incrementColorCovering(this.getPieceColor());
                break;
            }
            this.getSquaresCovered().add(board.getSquare(i, j));
            board.getSquare(i, j).incrementColorCovering(this.getPieceColor());
        }
        for (int i = file - 1, j = rank + 1; i >= 0 && j < board.getDimensions(); i--, j++) {
            if (board.getSquare(i, j).isEmpty() == false && board.getSquare(i,j).getPiece().getClass() != King.class) {
                this.getSquaresCovered().add(board.getSquare(i, j));
                board.getSquare(i, j).incrementColorCovering(this.getPieceColor());
                break;
            }
            this.getSquaresCovered().add(board.getSquare(i, j));
            board.getSquare(i, j).incrementColorCovering(this.getPieceColor());
        }
        for (int i = file - 1, j = rank - 1; i >= 0 && j >= 0; i--, j--) {
            if (board.getSquare(i, j).isEmpty() == false && board.getSquare(i,j).getPiece().getClass() != King.class) {
                this.getSquaresCovered().add(board.getSquare(i, j));
                board.getSquare(i, j).incrementColorCovering(this.getPieceColor());
                break;
            }
            this.getSquaresCovered().add(board.getSquare(i, j));
            board.getSquare(i, j).incrementColorCovering(this.getPieceColor());
        }


    }


    @Override
    public boolean canMove(Board board, Square start, Square end) {

        //same logic as the rook piece
        if (start.isOnBoard(board) && end.isOnBoard(board)) {
            if (start.getPiece() == this && (end.isEmpty() || end.getPiece().getPieceColor() != this.getPieceColor())) {
                //XOR to ignore the current square
                if (end.getFile() == this.getSquare().getFile() ^ end.getRank() == this.getSquare().getRank()) {

                    if (end.getFile() == this.getSquare().getFile()) {
                        if (end.getRank() > this.getSquare().getRank()) {
                            for (int i = this.getSquare().getRank() + 1; i < end.getRank() && i < board.getDimensions(); i++) {
                                if (!board.getSquare(this.getSquare().getFile(), i).isEmpty()) {
                                    return false;
                                }
                            }
                        } else {
                            for (int i = end.getRank() + 1; i < end.getRank() && i < board.getDimensions(); i++) {
                                if (!board.getSquare(this.getSquare().getFile(), i).isEmpty()) {
                                    return false;
                                }
                            }
                        }
                    } else {

                        if (end.getFile() > this.getSquare().getFile()) {
                            for (int i = this.getSquare().getFile() + 1; i < end.getFile() && i < board.getDimensions(); i++) {
                                if (!board.getSquare(i, this.getSquare().getRank()).isEmpty()) {
                                    return false;
                                }
                            }
                        } else {
                            for (int i = end.getFile() + 1; i < this.getSquare().getFile() && i < board.getDimensions(); i++) {
                                if (!board.getSquare(i, this.getSquare().getRank()).isEmpty()) {
                                    return false;
                                }
                            }
                        }


                    }
                    return true;
                }

                //same logic as the bishop piece
                int startFile = start.getFile();
                int startRank = start.getRank();
                int endFile = end.getFile();
                int endRank = end.getRank();
                if (endFile > startFile && endRank > startRank) {
                    for (int i = startFile + 1, j = startRank + 1; i < board.getDimensions() && j < board.getDimensions(); i++, j++) {
                        if (!board.getSquare(i, j).isEmpty() && board.getSquare(i, j) != end) {
                            break;
                        }
                        if (board.getSquare(i, j) == end) {
                            if (board.getSquare(i, j).isEmpty()
                                    || board.getSquare(i, j).getPiece().getPieceColor() != this.getPieceColor()
                                    && board.getSquare(i,j).getPiece().getClass() != King.class) {
                                return true;
                            } else if (board.getSquare(i, j).getPiece().getPieceColor() == this.getPieceColor()) {
                                return false;
                            }
                        }


                    }
                } else if (endFile > startFile && endRank < startRank) {
                    for (int i = startFile + 1, j = startRank - 1; i < board.getDimensions() && j >= 0; i++, j--) {
                        if (!board.getSquare(i, j).isEmpty() && board.getSquare(i, j) != end) {
                            break;
                        }
                        if (board.getSquare(i, j) == end) {
                            if (board.getSquare(i, j).isEmpty()
                                    || board.getSquare(i, j).getPiece().getPieceColor() != this.getPieceColor()
                                    && board.getSquare(i,j).getPiece().getClass() != King.class) {
                                return true;
                            } else if (board.getSquare(i, j).getPiece().getPieceColor() == this.getPieceColor()) {
                                return false;
                            }
                        }


                    }
                } else if (endFile < startFile && endRank > startRank) {
                    for (int i = startFile - 1, j = startRank + 1; i >= 0 && j < board.getDimensions(); i--, j++) {
                        if (!board.getSquare(i, j).isEmpty() && board.getSquare(i, j) != end
                                && board.getSquare(i,j).getPiece().getClass() != King.class) {
                            break;
                        }

                        if (board.getSquare(i, j) == end) {
                            if (board.getSquare(i, j).isEmpty()
                                    || board.getSquare(i, j).getPiece().getPieceColor() != this.getPieceColor()) {
                                return true;
                            } else if (board.getSquare(i, j).getPiece().getPieceColor() == this.getPieceColor()) {
                                return false;
                            }
                        }


                    }
                } else if (endFile < startFile && endRank < startRank) {
                    for (int i = startFile - 1, j = startRank - 1; i >= 0 && j >= 0; i--, j--) {

                        if (!board.getSquare(i, j).isEmpty() && board.getSquare(i, j) != end) {
                            break;
                        }
                        if (board.getSquare(i, j) == end) {
                            if (board.getSquare(i, j).isEmpty()
                                    || board.getSquare(i, j).getPiece().getPieceColor() != this.getPieceColor()
                                    && board.getSquare(i,j).getPiece().getClass() != King.class) {
                                return true;
                            } else if (board.getSquare(i, j).getPiece().getPieceColor() == this.getPieceColor()) {
                                return false;
                            }
                        }


                    }
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

        for(int i = file, j = rank; i < board.getDimensions() && j < board.getDimensions();i++,j++){
            listNoAddNullUtil(board,validMoves,new Move(player,this.getSquare(),board.getSquare(i,j)));
        }
        for(int i = file, j = rank; i < board.getDimensions() && j >= 0;i++,j--){
            listNoAddNullUtil(board,validMoves,new Move(player,this.getSquare(),board.getSquare(i,j)));
        }
        for(int i = file, j = rank; i >= 0 && j < board.getDimensions();i--,j++){
            listNoAddNullUtil(board,validMoves,new Move(player,this.getSquare(),board.getSquare(i,j)));
        }
        for(int i = file, j = rank; i >= 0 && j >= 0;i--,j--){
            listNoAddNullUtil(board,validMoves,new Move(player,this.getSquare(),board.getSquare(i,j)));
        }
        return validMoves;
    }
}

