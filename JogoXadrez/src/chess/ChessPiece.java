package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece{
	
	private Collor color;
	protected int moveCount;
	

	public ChessPiece(Board board, Collor color) {
		super(board);
		this.color = color;
	}
	
	public int getMoveCount() {
		return moveCount;
	}

	public Collor getColor() {
		return color;
	}
	
	public void incriseMoveCount() {
		moveCount++;
	}
	
	public void decriseMoveCount() {
		moveCount--;
	}
	
	protected boolean isThereOponentPiece(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);
			return p != null && p.getColor() != color;
	}
	 
	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(position);
	}
	
	
}
