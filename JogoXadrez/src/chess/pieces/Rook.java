package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Collor;

public class Rook extends ChessPiece{

	public Rook(Board board, Collor color) {
		super(board, color);
	}
	
	@Override
	public String toString() {
		return "R";
	}
	
	//
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position aux = new Position(0,0);
		
		//above
		aux.setValeus(position.getRow() -1, position.getColumn());
		while(getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true;
			aux.setRow(aux.getRow() -1);
		}
		if(getBoard().positionExists(aux) && isThereOponentPiece(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true;
		}
		
		//left
		aux.setValeus(position.getRow(), position.getColumn() - 1);
		while(getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true;
			aux.setColumn(aux.getColumn() -1);
		}
		if(getBoard().positionExists(aux) && isThereOponentPiece(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true;
		}
		
		//right
		aux.setValeus(position.getRow(), position.getColumn() + 1);
		while (getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true;
			aux.setColumn(aux.getColumn() + 1);
		}
		if (getBoard().positionExists(aux) && isThereOponentPiece(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true;
		}
		
		//below
		aux.setValeus(position.getRow() + 1, position.getColumn());
		while (getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true;
			aux.setRow(aux.getRow() + 1);
		}
		if (getBoard().positionExists(aux) && isThereOponentPiece(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true;
		}
		
		
		return mat;
	}
	
	
			
	
	
}
