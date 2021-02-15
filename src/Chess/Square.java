package Chess;

import Pieces.Piece;
import Pieces.PieceColor;

public class Square {

    //In chess the columns are called ranks and the rows are called files.
    private int rank, file;
    private Piece piece;
    //Count of pieces of each color that cover a given square.
    private int coveredByWhite;
    private int coveredByBlack;

    public Square(int file, int rank, Piece piece) {
        setRank(rank);
        setFile(file);
        setPiece(piece);
        coveredByWhite = 0;
        coveredByBlack = 0;
    }

    public boolean isOnBoard(Board board){

        return (this.rank >= 0 && this.rank < board.getDimensions() && this.file >= 0 && this.file < board.getDimensions());
    }
    public boolean isEmpty(){
        return piece == null;
    }
    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getFile() {
        return file;
    }

    public void setFile(int file) {
        this.file = file;
    }

    public Piece getPiece() {

        return this.piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }



    public void incrementColorCovering(PieceColor pieceColor) {
        if(pieceColor == PieceColor.WHITE ){
            this.coveredByWhite++;
        }
        else {
            this.coveredByBlack++;
        }
    }
    public void decrementColorCovering(PieceColor pieceColor) {
        if(pieceColor == PieceColor.WHITE && this.coveredByWhite > 0) {
            this.coveredByWhite--;
        }
        else if(this.coveredByBlack > 0){
            this.coveredByBlack--;
        }
    }

    public int getCoveredByColor(PieceColor pieceColor){
        if(pieceColor == PieceColor.BLACK){
            return this.coveredByBlack;
        }
        return this.coveredByWhite;
    }
    //returns the enemy pieces covering
    public int getCoveredByOppositeColor(PieceColor oppositePieceColor){
        if(oppositePieceColor == PieceColor.WHITE){
            return this.coveredByBlack;
        }
        return this.coveredByWhite;
    }

}
