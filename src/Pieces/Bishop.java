package Pieces;

import Chess.Board;
import Chess.Move;
import Chess.Player;
import Chess.Square;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {


    public Bishop(PieceColor pieceColor, int file, int rank) {
        super(pieceColor, file, rank);
        super.getSquare().setPiece(this);
        this.setPieceSymbol('B');
    }

    @Override
    public void changeSquaresCovered(Board board) {
        for (Square square: this.getSquaresCovered()) {
            square.decrementColorCovering(this.getPieceColor());
        }


        this.getSquaresCovered().clear();

        int file = this.getSquare().getFile();
        int rank = this.getSquare().getRank();
        //The bishop controls the 4 diagonals on each of its sides.
        //If there is a piece on any diagonal of them, the bishop doesn't control the diagonal past that piece.
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
        if (start.isOnBoard(board) && end.isOnBoard(board)) {
            if (start.getPiece() == this && (end.isEmpty() || end.getPiece().getPieceColor() != this.getPieceColor())) {

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