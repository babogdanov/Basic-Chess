package Chess;

import Pieces.PieceColor;

import java.util.Scanner;

public class Test {

    //Just some string validation.
    static private int[] convertInputToMove(String input, Board board){
        if(input.length() != 5 || input.charAt(0) < 'A' || input.charAt(0) > 'H' ||
                input.charAt(2) != ' ' || input.charAt(3) < 'A' || input.charAt(3) > 'H' ||
                input.charAt(1) < '1' || input.charAt(1) > '8' ||
                input.charAt(4) < '1' || input.charAt(4) > '8'){
            return null;
        }
        int[] moveArray = new int[4];
        moveArray[0] = input.charAt(0) - 'A';
        moveArray[1] = board.getDimensions() - (input.charAt(1) - '0');
        moveArray[2] = input.charAt(3) - 'A';
        moveArray[3] = board.getDimensions() - (input.charAt(4) - '0');
        return moveArray;
    }


    public static void main(String[] args) {
        Player playerOne = new Player(Player.PlayerType.HUMAN, PieceColor.WHITE);
        Player playerTwo = new Player(Player.PlayerType.HUMAN,PieceColor.BLACK);
        Board board = new Board();
        board.printBoard();
        Game game = new Game(playerOne,playerTwo);
        game.setGameStatus(Game.GameStatus.ONGOING);

        Scanner kbd = new Scanner(System.in);
        int[] moveArray;
        game.getBoard().updateAllPiecesCoveringSquares();
        while(game.getGameStatus() == Game.GameStatus.ONGOING) {


            do {
                if (game.getCurrentTurn().getPieceColor() == PieceColor.WHITE) {
                    System.out.print("White ");
                } else {
                    System.out.print("Black ");
                }
                 do{
                     System.out.print(" to play. Enter square of the piece you want to move," +
                             " then the square you want to move it to. Input format is LetterDigit LetterDigit" +
                             "(e.g. D2 D4):" + ConsoleColors.BLACK + " ");
                     String move = kbd.nextLine();
                     moveArray = convertInputToMove(move,board);
                     if(moveArray == null)
                     System.out.println("Invalid input.");
                 }
                while(moveArray == null);


            }while (game.makeMove(moveArray[1], moveArray[0], moveArray[3], moveArray[2]) == false) ;

        }
        System.out.println(game.getGameStatus());


    }

}
