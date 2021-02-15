package Chess;

import Pieces.*;

import java.util.ArrayList;
import java.util.List;


//TODO implement draw by insufficient material, en passant, castling, pawn promotion, maybe a simple bot.
//This is where most of the logic of validating moves is.
public class Game {
    enum GameStatus{
        ONGOING,
        WHITE_WINS,
        BLACK_WINS,
        DRAW_BY_STALEMATE,
        DRAW_BY_INSUFFICIENT_MATERIAL
    }
    private  Player playerOne, playerTwo;
    private  static Board board;
    private Player currentTurn;
    private  GameStatus gameStatus;
    private List<Move> movesPlayed;

    Game(Player playerOne, Player playerTwo){
        this.board = new Board();
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        if(playerOne.getPieceColor() == PieceColor.WHITE){
            currentTurn = playerOne;
        }
        else{
            currentTurn = playerTwo;
        }
        this.movesPlayed = new ArrayList<>();
    }

    //TODO wrap some of the logic of makeMove here to avoid duplicate code.
    public boolean validateMove(int startFile, int startRank, int endFile, int endRank){
        return false;
    }

    //If this method returns true, then the move was successful.
    //If it return false, then a new valid move is to be made.
    public boolean makeMove(int startFile, int startRank, int endFile, int endRank){


        Square startSquare = board.getSquare(startFile,startRank);
        Square endSquare = board.getSquare(endFile,endRank);
        Move move = new Move(currentTurn,startSquare,endSquare);
        Piece pieceToMove = move.getPieceToMove();

        if(board.kingIsInCheck(currentTurn.getPieceColor())) {

                // here we only attempt a move to see if it will escape the check.
            if(pieceToMove == null){
                System.out.println("No piece on that square.");
                return false;
            }

            if(pieceToMove.getPieceColor() != currentTurn.getPieceColor()){
                System.out.println("The piece on that square is not yours.");
                return false;
            }


            if(!pieceToMove.canMove(board,move.getStart(),move.getEnd())){
                System.out.println("Illegal move");
                return false;
            }

            // if a piece is going to be captured,
            // ensure the squares that piece had covered are no longer considered to be covered by it.
            if(endSquare.isEmpty() == false){
                endSquare.getPiece().freeCoveredSquares(board);
            }
            pieceToMove.setSquare(endSquare);
            board.getSquare(endSquare.getFile(),endSquare.getRank()).setPiece(pieceToMove);


            board.getSquare(startFile,startRank).setPiece(null);
            board.updateAllPiecesCoveringSquares();


            //If the move didn't prevent the check, revert it.
            if(board.kingIsInCheck(currentTurn.getPieceColor())){
                System.out.println("The king will still be in check if you do that.");
                pieceToMove.setSquare(startSquare);
                board.getSquare(startSquare.getFile(),startSquare.getRank()).setPiece(pieceToMove);

                board.getSquare(endFile,endRank).setPiece(null);
                //This counteracts the freeCoveredSquares call on line 81.
                board.updateAllPiecesCoveringSquares();
                //Check to make sure that the pawn can go 2 squares ahead on its first move.
                if(pieceToMove.getClass() == Pawn.class){
                    ((Pawn) pieceToMove).decrementTimesMoved();
                }

                return false;
            }
            else{
                if(this.currentTurn == this.playerOne)
                {
                    System.out.println("Player one's turn ends, player two's turn begins");
                    this.setCurrentTurn(playerTwo);
                }
                else{
                    System.out.println("Player two's turn ends, player one's turn begins");
                    this.setCurrentTurn(playerOne);
                }
                board.printBoard();

                if(!board.kingCanMoveSomewhere(currentTurn.getPieceColor())){
                    System.out.println("King cant move");
                    if(board.kingIsInCheck(currentTurn.getPieceColor())) {
                        if (this.currentTurn.getPieceColor() == PieceColor.WHITE) {
                            setGameStatus(GameStatus.BLACK_WINS);
                            return true;
                        } else {
                            setGameStatus(GameStatus.WHITE_WINS);
                            return true;
                        }
                    }
                    else{
                        setGameStatus(GameStatus.DRAW_BY_STALEMATE);
                        return true;
                    }
                }
                if(board.kingIsInCheck(currentTurn.getPieceColor())){

                    System.out.println("You are in check and you must escape.");
                }
                return true;
            }

        }
        else
            if(pieceToMove == null){
                System.out.println("No piece on that square.");
                return false;

            }
            if(pieceToMove.getPieceColor() != currentTurn.getPieceColor()){
                System.out.println("The piece on that square is not yours.");
                return false;

            }

            if(!pieceToMove.canMove(board,move.getStart(),move.getEnd())
                    || (move.getEnd().getPiece() != null && move.getEnd().getPiece().getClass() == King.class)){

                System.out.println("Illegal move");
                return false;
            }

        // if a piece is going to be captured,
        // ensure the squares that piece had covered are no longer considered to be covered by it.
        if(endSquare.isEmpty() == false){
            endSquare.getPiece().freeCoveredSquares(board);
        }

            movesPlayed.add(move);
            endSquare = move.getEnd();
            endSquare.setPiece(null);
            Piece currPiece = move.getPieceToMove();

            //Logic of changing a piece's square (also changing the square's piece).
            currPiece.setSquare(endSquare);
            endSquare.setPiece(currPiece);
            move.getStart().setPiece(null);
            //A move might affect multiple pieces' covered squares, so we must check them all.
            board.updateAllPiecesCoveringSquares();


            //Prevent a move that will cause the king to be in check.
            if(board.kingIsInCheck(currentTurn.getPieceColor())){

                System.out.println("The king will be in check if you do that.");
                pieceToMove.setSquare(startSquare);
                board.getSquare(startSquare.getFile(),startSquare.getRank()).setPiece(pieceToMove);

                board.getSquare(endFile,endRank).setPiece(null);
                board.updateAllPiecesCoveringSquares();
                //Check to make sure that the pawn can go 2 squares ahead on its first move.
                if(pieceToMove.getClass() == Pawn.class) {
                    ((Pawn) pieceToMove).decrementTimesMoved();
                }
                return false;
            }

            if(this.currentTurn == this.playerOne)
            {
                System.out.println("Player one's turn ends, player two's turn begins");
                this.setCurrentTurn(playerTwo);
            }
            else{
                System.out.println("Player two's turn ends, player one's turn begins");
                this.setCurrentTurn(playerOne);
            }
            board.printBoard();


            if(!board.kingCanMoveSomewhere(currentTurn.getPieceColor())){
                if(board.kingIsInCheck(currentTurn.getPieceColor())) {
                    if (this.currentTurn.getPieceColor() == PieceColor.WHITE) {
                        setGameStatus(GameStatus.BLACK_WINS);
                        return true;
                    } else {
                        setGameStatus(GameStatus.WHITE_WINS);
                        return true;
                    }
                }

                //If the king is not in check, then any move at all by another piece is OK.
                else{
                    for(int i = 0; i <board.getDimensions();i++){
                        for(int j = 0; j < board.getDimensions();j++){
                            if(board.getSquare(i,j).isEmpty() == false
                                    && board.getSquare(i,j).getPiece().getPieceColor() == currentTurn.getPieceColor()
                            && board.getSquare(i,j).getPiece().getClass() != King.class){
                                List<Move> validMoves = board.getSquare(i,j).getPiece().canMoveAnywhere(board,currentTurn);
                                for(Move currMove: validMoves){

                                    Piece tempPiece = board.getSquare(i,j).getPiece();
                                    //Same logic as validating an ordinary move.
                                    if(currMove.getEnd().isEmpty() == false){
                                        currMove.getEnd().getPiece().freeCoveredSquares(board);
                                    }
                                    tempPiece.setSquare(currMove.getEnd());
                                    board.getSquare(currMove.getEnd().getFile(),currMove.getEnd().getRank()).
                                            setPiece(tempPiece);


                                    board.getSquare(currMove.getStart().getFile(),currMove.getStart().getRank()).
                                            setPiece(null);
                                    board.updateAllPiecesCoveringSquares();

                                    //If the move didn't cause a check, then we found a move that prevents stalemate
                                    // and the game can go on.
                                    if(!board.kingIsInCheck(currentTurn.getPieceColor())){
                                        tempPiece.setSquare(currMove.getStart());
                                        board.getSquare(currMove.getStart().getFile(),currMove.getStart().getRank())
                                                .setPiece(tempPiece);

                                        board.getSquare(currMove.getEnd().getFile(),currMove.getEnd().getRank())
                                                .setPiece(null);
                                        board.updateAllPiecesCoveringSquares();
                                        return true;
                                    }
                                        //If the move caused a check- revert and keep going.

                                        tempPiece.setSquare(currMove.getStart());
                                        board.getSquare(currMove.getStart().getFile(),currMove.getStart().getRank())
                                                .setPiece(tempPiece);

                                        board.getSquare(currMove.getEnd().getFile(),currMove.getEnd().getRank())
                                                .setPiece(null);

                                        board.updateAllPiecesCoveringSquares();

                                    }

                            }
                        }
                    }
                    //If control reaches here, that means there are no valid moves- so it's stalemate.
                    setGameStatus(GameStatus.DRAW_BY_STALEMATE);
                    return true;
                }
            }
            if(board.kingIsInCheck(currentTurn.getPieceColor())){
                System.out.println("Your king is in check and you must defend him.");
            }
        return true;

    }



    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public Board getBoard() {
        return board;
    }

    public void setCurrentTurn(Player player)
    {
        this.currentTurn = player;
    }
    public Player getCurrentTurn() {
        return currentTurn;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }
    public void setGameStatus(GameStatus gameStatus){
        this.gameStatus = gameStatus;
    }
    public List<Move> getMovesPlayed() {
        return movesPlayed;
    }
}
