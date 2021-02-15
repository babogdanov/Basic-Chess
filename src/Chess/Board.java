package Chess;

import Pieces.*;

import java.io.Console;


public class Board {
    private static Square[][] squares;
    private final int dimensions = 8;
    private boolean whiteKingIsInCheck = false;
    private boolean blackKingIsInCheck = false;
    public Board(){
        squares = new Square[dimensions][dimensions];
        for(int i = 2; i < dimensions - 2 ;i++){
            for(int j = 0; j < dimensions;j++) {
                squares[i][j] = new Square(i,j,null);
            }

        }



        //Generating all of the pieces.

        squares[7][4] = new Square(7,4,new King(PieceColor.WHITE,7,4));
        squares[0][4] = new Square(0,4,new King(PieceColor.BLACK,0,4));

        squares[7][3] = new Square(7,3,new Queen(PieceColor.WHITE,7,3));
        squares[0][3] = new Square(0,3,new Queen(PieceColor.BLACK,0,3));

        squares[7][1] = new Square(7,1,new Knight(PieceColor.WHITE,7,1));
        squares[7][6] = new Square(7,6,new Knight(PieceColor.WHITE,7,6));
        squares[0][1] = new Square(0,1,new Knight(PieceColor.BLACK,0,1));
        squares[0][6] = new Square(0,6,new Knight(PieceColor.BLACK,0,6));

        squares[7][2] = new Square(7,2,new Bishop(PieceColor.WHITE,7,2));
        squares[7][5] = new Square(7,5,new Bishop(PieceColor.WHITE,7,5));
        squares[0][2] = new Square(0,2,new Bishop(PieceColor.BLACK,0,2));
        squares[0][5] = new Square(0,5,new Bishop(PieceColor.BLACK,0,5));


        squares[7][0] = new Square(7,0, new Rook(PieceColor.WHITE,7,0));
        squares[7][7] = new Square(7,7, new Rook(PieceColor.WHITE,7,7));
        squares[0][0] = new Square(0,0, new Rook(PieceColor.BLACK,0,0));
        squares[0][7] = new Square(0,7, new Rook(PieceColor.BLACK,0,7));
        for(int i = 0; i < dimensions;i++)
        {
            squares[6][i] = new Square(6,i,new Pawn(PieceColor.WHITE,6,i));
            squares[1][i] = new Square(1,i,new Pawn(PieceColor.BLACK,1,i));
        }

    }

    public Board(Board other){
       this.squares = new Square[dimensions][dimensions];
        for(int i = 0; i < dimensions ;i++){
            for(int j = 0; j < dimensions;j++) {
                squares[i][j] = other.squares[i][j];
            }

        }
        this.blackKingIsInCheck = other.blackKingIsInCheck;
        this.whiteKingIsInCheck = other.whiteKingIsInCheck;

    }

    public int getDimensions(){
        return this.dimensions;
    }
    public Square getSquare(int file, int rank){
       if(file >= 0 && file < dimensions && rank >= 0 && rank < dimensions){
           return this.squares[file][rank];
       }
       return null;

    }

    public void printBoard(){

        for(int i = 0; i < dimensions;i++){
            System.out.print(ConsoleColors.RED_BOLD_BRIGHT + (dimensions-i ) + ConsoleColors.RESET+ " ");
            for(int j = 0; j < dimensions;j++){
                if(squares[i][j].getPiece() == null){
                    System.out.print(ConsoleColors.RED_BOLD_BRIGHT+ "- " + ConsoleColors.RESET);

                }

                else{
                    if(squares[i][j].getPiece().getPieceColor() == PieceColor.WHITE){
                        System.out.print(ConsoleColors.WHITE_BOLD + squares[i][j].getPiece().getPieceSymbol() + ConsoleColors.RESET + " ");

                    }

                    else{
                        System.out.print(ConsoleColors.BLACK_BOLD_BRIGHT + squares[i][j].getPiece().getPieceSymbol() + ConsoleColors.RESET+  " ");

                    }


                }

            }
            System.out.println();
        }
        System.out.print("  ");
        for(char i = 'A'; i <= 'H';i++)
        {
            System.out.print(ConsoleColors.RED_BOLD_BRIGHT + i + ConsoleColors.RESET+ " " );
        }
        System.out.println();
    }

    public Square getOwnKingSquare(PieceColor ownPieceColor){
        for(int i = 0; i < this.dimensions;i++){
            for(int j = 0; j < this.dimensions;j++){
                Piece placeholder = this.getSquare(i,j).getPiece();
                if(placeholder != null && placeholder.getPieceColor() == ownPieceColor
                        && placeholder.getClass() == King.class){

                    return this.getSquare(i,j);
                }
            }
        }
        //control will never reach this point, both kings remain on the board for the whole game.
        return null;
    }
    //used to get the enemy's king to see if it's in check.
    public Square getEnemyKingSquare(PieceColor ownPieceColor){
        for(int i = 0; i < this.dimensions;i++){
            for(int j = 0; j < this.dimensions;j++){
                Piece placeholder = this.getSquare(i,j).getPiece();
                if(placeholder != null && placeholder.getPieceColor() != ownPieceColor
                && placeholder.getClass() == King.class){
                    return  this.getSquare(i,j);
                }
            }
        }
        //control will never reach this point, both kings remain on the board for the whole game.
        return null;
    }

    //Called after every attempted move to account for changes to covered squares after moving a piece.
    public void updateAllPiecesCoveringSquares(){
        for(int i = 0; i < this.dimensions;i++){
            for(int j = 0; j < this.dimensions;j++){
                if(!this.squares[i][j].isEmpty()){
                    this.squares[i][j].getPiece().changeSquaresCovered(this);
                }
            }
        }
    }

    public boolean kingCanMoveSomewhere(PieceColor pieceColor){
        Square currentSquare = this.getOwnKingSquare(pieceColor);

        for(int i =currentSquare.getFile()-1; i <= currentSquare.getFile() + 1; i++) {
            for (int j = currentSquare.getRank() - 1; j <= currentSquare.getRank() + 1; j++) {
                //ignore the square the king is currently on
                if ((i == currentSquare.getFile()) && (j == currentSquare.getRank())) {
                    continue;
                }
                if(this.getSquare(i,j)!= null && this.getSquare(i,j).getCoveredByOppositeColor(pieceColor) < 1){

                    return true;
                }

            }
        }
        return false;
    }

    public boolean kingIsInCheck(PieceColor pieceColor){
        Square currentSquare = this.getOwnKingSquare(pieceColor);
        if(currentSquare.getCoveredByOppositeColor(pieceColor) > 0){
            return true;
        }
        return false;


    }

    public boolean getWhiteKingIsInCheck() {
        return whiteKingIsInCheck;
    }

    public void setWhiteKingIsInCheck(boolean whiteKingIsInCheck) {
        this.whiteKingIsInCheck = whiteKingIsInCheck;
    }

    public boolean getBlackKingIsInCheck() {
        return blackKingIsInCheck;
    }

    public void setBlackKingIsInCheck(boolean blackKingIsInCheck) {
        this.blackKingIsInCheck = blackKingIsInCheck;
    }
}
