package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Collor;

public class Pawn extends ChessPiece {

	private ChessMatch chessMatch;

	public Pawn(Board board, Collor color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}

	@Override
	public String toString() {
		return "P";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position aux = new Position(0, 0);
		// condição para peças brancas
		if (getColor() == Collor.WHITE) {
			// testa se a movimento está livre
			aux.setValeus(position.getRow() - 1, position.getColumn());
			if (getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
				mat[aux.getRow()][aux.getColumn()] = true;
			}
			// tezta se a movimento está livre em caso de ser o primeiro movimento da peça
			Position aux2 = new Position(0, 0);
			aux.setValeus(position.getRow() - 1, position.getColumn());
			aux2.setValeus(position.getRow() - 2, position.getColumn());
			if (getBoard().positionExists(aux2) && !getBoard().thereIsAPiece(aux2) && getBoard().positionExists(aux)
					&& !getBoard().thereIsAPiece(aux) && getMoveCount() == 0) {
				mat[aux2.getRow()][aux2.getColumn()] = true;
			}

			// testes se a movimento está livre para capturar uma peça
			aux.setValeus(position.getRow() - 1, position.getColumn() - 1);
			if (getBoard().positionExists(aux) && getBoard().thereIsAPiece(aux)) {
				mat[aux.getRow()][aux.getColumn()] = true;
			}

			aux.setValeus(position.getRow() - 1, position.getColumn() + 1);
			if (getBoard().positionExists(aux) && getBoard().thereIsAPiece(aux)) {
				mat[aux.getRow()][aux.getColumn()] = true;
			}
			
			// enPassant move white
			if (position.getRow() == 3) {
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				if (getBoard().positionExists(left) && isThereOponentPiece(left)
						&& getBoard().piece(left) == chessMatch.getEnPassantVunerable()) {
					mat[left.getRow() - 1][left.getColumn()] = true;
				}
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				if (getBoard().positionExists(right) && isThereOponentPiece(right)
						&& getBoard().piece(right) == chessMatch.getEnPassantVunerable()) {
					mat[right.getRow() - 1][right.getColumn()] = true;
				}
			}
		} else {
			// mesma condição poara oeças pretas
			aux.setValeus(position.getRow() + 1, position.getColumn());
			if (getBoard().positionExists(aux) && !getBoard().thereIsAPiece(aux)) {
				mat[aux.getRow()][aux.getColumn()] = true;
			}

			Position aux2 = new Position(0, 0);
			aux.setValeus(position.getRow() + 1, position.getColumn());
			aux2.setValeus(position.getRow() + 2, position.getColumn());
			if (getBoard().positionExists(aux2) && !getBoard().thereIsAPiece(aux2) && getBoard().positionExists(aux)
					&& !getBoard().thereIsAPiece(aux) && getMoveCount() == 0) {
				mat[aux2.getRow()][aux2.getColumn()] = true;
			}

			aux.setValeus(position.getRow() + 1, position.getColumn() - 1);
			if (getBoard().positionExists(aux) && getBoard().thereIsAPiece(aux)) {
				mat[aux.getRow()][aux.getColumn()] = true;
			}

			aux.setValeus(position.getRow() + 1, position.getColumn() + 1);
			if (getBoard().positionExists(aux) && getBoard().thereIsAPiece(aux)) {
				mat[aux.getRow()][aux.getColumn()] = true;
			}

			// enPassant move black
			if (position.getRow() == 4) {
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				if (getBoard().positionExists(left) && isThereOponentPiece(left)
						&& getBoard().piece(left) == chessMatch.getEnPassantVunerable()) {
					mat[left.getRow() + 1][left.getColumn()] = true;
				}
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				if (getBoard().positionExists(right) && isThereOponentPiece(right)
						&& getBoard().piece(right) == chessMatch.getEnPassantVunerable()) {
					mat[right.getRow() + 1][right.getColumn()] = true;
				}
			}
		}
		return mat;
	}

}
