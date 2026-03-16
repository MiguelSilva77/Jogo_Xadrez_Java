package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Collor;

public class Queen extends ChessPiece{

	public Queen(Board board, Collor color) {
		super(board, color);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "Q";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position aux = new Position(0,0);
		
		//nw
				aux.setValeus(position.getRow() -1, position.getColumn() - 1);
				while(getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
					mat[aux.getRow()][aux.getColumn()] = true;
					aux.setValeus(aux.getRow() - 1 ,aux.getColumn() - 1);
				}
				if(getBoard().positionExists(aux) && isThereOponentPiece(aux)) {
					mat[aux.getRow()][aux.getColumn()] = true;
				}
				
				//se
				aux.setValeus(position.getRow() + 1, position.getColumn() + 1);
				while(getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
					mat[aux.getRow()][aux.getColumn()] = true;
					aux.setValeus(aux.getRow() + 1 ,aux.getColumn() + 1);		
					}
				if(getBoard().positionExists(aux) && isThereOponentPiece(aux)) {
					mat[aux.getRow()][aux.getColumn()] = true;
				}
				
				//ne
				aux.setValeus(position.getRow() -1, position.getColumn() + 1);
				while (getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
					mat[aux.getRow()][aux.getColumn()] = true;
					aux.setValeus(aux.getRow() - 1 ,aux.getColumn() + 1);
					}
				if (getBoard().positionExists(aux) && isThereOponentPiece(aux)) {
					mat[aux.getRow()][aux.getColumn()] = true;
				}
				
				//sw
				aux.setValeus(position.getRow() + 1, position.getColumn() - 1);
				while (getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
					mat[aux.getRow()][aux.getColumn()] = true;
					aux.setValeus(aux.getRow() + 1 ,aux.getColumn() - 1);
				}
				if (getBoard().positionExists(aux) && isThereOponentPiece(aux)) {
					mat[aux.getRow()][aux.getColumn()] = true;
				}
		
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
