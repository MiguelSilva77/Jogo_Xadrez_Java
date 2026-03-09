package chess;

import boardgame.Board;
import boardgame.Piece;

public abstract class ChessPiece extends Piece{
	
	private Collor color;

	public ChessPiece(Board board, Collor color) {
		super(board);
		this.color = color;
	}

	public Collor getColor() {
		return color;
	}

	
}
