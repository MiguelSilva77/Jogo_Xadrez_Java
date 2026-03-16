package chess.pieces;


import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Collor;

public class King extends ChessPiece {

	private ChessMatch chessMatch;
	
	public King(Board board, Collor color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}

	@Override
	public String toString() {
		return "K";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0);

		// above
		p.setValeus(position.getRow() - 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// below
		p.setValeus(position.getRow() + 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// left
		p.setValeus(position.getRow(), position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// right
		p.setValeus(position.getRow(), position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// nw
		p.setValeus(position.getRow() - 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// ne
		p.setValeus(position.getRow() - 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// sw
		p.setValeus(position.getRow() + 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// se
		p.setValeus(position.getRow() + 1 , position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//special move castling
		if(moveCount == 0 && !chessMatch.getCheck()) {
			//king castling
			Position posiK = new Position(position.getRow(), position.getColumn() + 3);
			if(testRook(posiK)) {
				Position po1 = new Position(position.getRow(), position.getColumn() + 1);
				Position po2 = new Position(position.getRow(), position.getColumn() + 2);
				if(getBoard().piece(po1) == null && getBoard().piece(po2) == null) {
					mat[position.getRow()][position.getColumn() + 2] = true;
				}

			}
			//queen castling
			Position posiQ = new Position(position.getRow(), position.getColumn() - 4);
			if(testRook(posiQ)) {
				Position po1 = new Position(position.getRow(), position.getColumn() - 1);
				Position po2 = new Position(position.getRow(), position.getColumn() - 2);
				Position po3 = new Position(position.getRow(), position.getColumn() - 3);
				if(getBoard().piece(po1) == null && getBoard().piece(po2) == null && getBoard().piece(po3) == null) {
					mat[position.getRow()][position.getColumn() - 2] = true;
				}

			}
		}
		return mat;
	}

	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p == null || p.getColor() != getColor();
	}
	
	private boolean testRook(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
	}
}
