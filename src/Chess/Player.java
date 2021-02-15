package Chess;

import Pieces.PieceColor;

//Only human players supported for now.
public  class Player {
    enum PlayerType{
        HUMAN,
        CPU
    }
    private PieceColor pieceColor;
    private PlayerType playerType;

    Player(PlayerType playerType, PieceColor pieceColor) {
        this.setPlayerType(playerType);
        this.setPieceColor(pieceColor);
    }

    public PieceColor getPieceColor() {
        return pieceColor;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPieceColor(PieceColor pieceColor) {
        this.pieceColor = pieceColor;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }
}
