package Pieces;

import Chess.Board;
import Chess.Player;
import Chess.Square;
import Chess.Move;
import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {

    public Knight(PieceColor pieceColor, int file, int rank) {
        super(pieceColor, file, rank);
        this.getSquare().setPiece(this);
        this.setPieceSymbol('H');
    }
    public void addToControlledSquares(Square square, Board board){
        if(square != null && square.isOnBoard(board)){
            this.getSquaresCovered().add(square);
        }
    }

    @Override
    public void changeSquaresCovered(Board board) {
        for (Square square: this.getSquaresCovered()) {
            square.decrementColorCovering(this.getPieceColor());
        }

        this.getSquaresCovered().clear();
        int file = this.getSquare().getFile();
        int rank = this.getSquare().getRank();

        // The knight moves in an L shape- 2 squares vertically and 1 square horizontally or vice versa.
        addToControlledSquares(board.getSquare(file-2,rank-1),board);
        addToControlledSquares(board.getSquare(file-2,rank+1),board);
        addToControlledSquares(board.getSquare(file+2,rank-1),board);
        addToControlledSquares(board.getSquare(file+2,rank+1),board);

        addToControlledSquares(board.getSquare(file-1,rank-2),board);
        addToControlledSquares(board.getSquare(file-1,rank+2),board);
        addToControlledSquares(board.getSquare(file+1,rank-2),board);
        addToControlledSquares(board.getSquare(file+1,rank+2),board);

        for(Square square:this.getSquaresCovered()){
                board.getSquare(square.getFile(), square.getRank()).incrementColorCovering(this.getPieceColor());
        }
    }

    @Override
    public boolean canMove(Board board, Square start, Square end) {
        if(start.isOnBoard(board) && end.isOnBoard(board)) {
            if(start.getPiece() == this && (end.isEmpty() || end.getPiece().getPieceColor() != this.getPieceColor()
            || end.getPiece().getClass() != King.class)){

                //We need to check if the absolute value of the difference between the start and end
                //files and ranks is 2 and 1 or vice versa.
                if( ( Math.abs(start.getFile() - end.getFile()) == 2 &&  Math.abs(start.getRank() - end.getRank()) == 1)
                 ^ ( Math.abs(start.getFile() - end.getFile()) == 1 &&  Math.abs(start.getRank() - end.getRank()) == 2)){
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


        listNoAddNullUtil(board,validMoves,new Move(player,this.getSquare(),board.getSquare(file-2,rank-1)));
        listNoAddNullUtil(board,validMoves,new Move(player,this.getSquare(),board.getSquare(file-2,rank+1)));
        listNoAddNullUtil(board,validMoves,new Move(player,this.getSquare(),board.getSquare(file+2,rank-1)));
        listNoAddNullUtil(board,validMoves,new Move(player,this.getSquare(),board.getSquare(file+2,rank+1)));

        listNoAddNullUtil(board,validMoves,new Move(player,this.getSquare(),board.getSquare(file-1,rank-2)));
        listNoAddNullUtil(board,validMoves,new Move(player,this.getSquare(),board.getSquare(file-1,rank+2)));
        listNoAddNullUtil(board,validMoves,new Move(player,this.getSquare(),board.getSquare(file+1,rank-2)));
        listNoAddNullUtil(board,validMoves,new Move(player,this.getSquare(),board.getSquare(file+1,rank+2)));
        return validMoves;

    }
}
