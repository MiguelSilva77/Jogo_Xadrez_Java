package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Pawn;
import chess.pieces.Rook;

public class ChessMatch {
	private int turn;
	private Collor currentPlayer;
	private Board board;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	
	private boolean check;
	private boolean checkMate;
	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Collor.WHITE;
		initialSetup();
		check = false;
	}

	public int getTurn() {
		return turn;
	}

	public Collor getCurrentPlayer() {
		return currentPlayer;
	}

	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
	}

	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}

	public ChessPiece performeChassMovie(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetposition(source, target);
		Piece capturedPiece = makeMove(source, target);
		
		if(testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("you can´t put check in yourselfe");
		}
		
		check = (testCheck(opponent(currentPlayer))) ? true : false;
		
		if(testCheckMate(opponent(currentPlayer))) {
			checkMate = true;
		}else {
			nextTurn();
		}
		return (ChessPiece) capturedPiece;
	}

	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is no a piece on source position");
		}
		if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) {
			throw new ChessException("the chosen piece isn´t yours");
		}
		if (!board.piece(position).isThereanyPossibleMove()) {
			throw new ChessException("There is no possible move for the chosen piece");
		}
	}

	private void validateTargetposition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) {
			throw new ChessException("Chosen piece can`t move to target position");
		}
	}

	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Collor.WHITE) ? Collor.BLACK : Collor.WHITE;
	}

	private Piece makeMove(Position source, Position target) {
		ChessPiece p = (ChessPiece)board.removePiece(source);
		p.incriseMoveCount();
		Piece p1 = board.removePiece(target);
		board.placePiece(p, target);
		
		if(p1 != null) {
			piecesOnTheBoard.remove(p1);
			capturedPieces.add(p1);
		}
		return p1;
	}
	
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece p = (ChessPiece)board.removePiece(target);
		p.decriseMoveCount();
		board.placePiece(p, source);
		
		if(capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.remove(capturedPiece);

		}
	}
	
	private Collor opponent(Collor color) {
		return (color == color.WHITE) ? color.BLACK : color.WHITE;
	}
	
	private ChessPiece king(Collor color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x ->((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for(Piece p : list) {
			if(p instanceof King) {
				return (ChessPiece)p;
			}
		}
	throw new IllegalStateException();
	}
	
	private boolean testCheck(Collor color) {
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream()
				.filter(x -> ((ChessPiece) x).getColor() == opponent(color)).collect(Collectors.toList());
		for (Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testCheckMate(Collor color) {
		if(!testCheck(color)) {
			return false;
		}
		List<Piece> list =  piecesOnTheBoard.stream()
				.filter(x -> ((ChessPiece) x).getColor() ==color).collect(Collectors.toList());
		for(Piece p:list) {
			boolean [][] mat = p.possibleMoves();
			for(int i=0; i<board.getRows(); i++) {
				for(int j=0; j<board.getColumns(); j++) {
					if(mat[i][j]) {
						Position source = ((ChessPiece)p).getChessPosition().toPosition();
						Position target = new Position(i, j);
						Piece capturedPiece = makeMove(source, target);
						boolean testCheck = testCheck(color);
						undoMove(source, target, capturedPiece);
						if(!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	

	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}

	private void initialSetup() {
		/*placeNewPiece('c', 1, new Rook(board, Collor.WHITE));
		placeNewPiece('c', 2, new Rook(board, Collor.WHITE));
		placeNewPiece('d', 2, new Rook(board, Collor.WHITE));
		placeNewPiece('e', 2, new Rook(board, Collor.WHITE));
		placeNewPiece('e', 1, new Rook(board, Collor.WHITE));
		

		placeNewPiece('c', 7, new Rook(board, Collor.BLACK));
		placeNewPiece('c', 8, new Rook(board, Collor.BLACK));
		placeNewPiece('d', 7, new Rook(board, Collor.BLACK));
		placeNewPiece('e', 7, new Rook(board, Collor.BLACK));
		placeNewPiece('e', 8, new Rook(board, Collor.BLACK));
		*/
		placeNewPiece('a', 1, new Pawn(board, Collor.WHITE));
	    placeNewPiece('b', 1, new Pawn(board, Collor.WHITE));
	    placeNewPiece('c', 1, new Pawn(board, Collor.WHITE));
	    placeNewPiece('d', 1, new Pawn(board, Collor.WHITE));
	    placeNewPiece('e', 1, new Pawn(board, Collor.WHITE));
	    placeNewPiece('f', 1, new King(board, Collor.WHITE));
	    
		placeNewPiece('a', 8, new Pawn(board, Collor.BLACK));
	    placeNewPiece('b', 8, new Pawn(board, Collor.BLACK));
	    placeNewPiece('c', 8, new Pawn(board, Collor.BLACK));
	    placeNewPiece('d', 8, new Pawn(board, Collor.BLACK));
	    placeNewPiece('e', 8, new Pawn(board, Collor.BLACK));
	    placeNewPiece('f', 8, new King(board, Collor.BLACK));
	}

}
