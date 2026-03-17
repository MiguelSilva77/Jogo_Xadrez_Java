package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {
	private int turn;
	private Collor currentPlayer;
	private Board board;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	
	private boolean check;
	private boolean checkMate;
	private ChessPiece enPassantVunerable;
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
	
	public ChessPiece getEnPassantVunerable() {
		return enPassantVunerable;
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
		ChessPiece movedPiece = (ChessPiece)board.piece(target);
		
		//enPassantMove
		if(movedPiece instanceof Pawn && (target.getRow() == source.getRow() + 2) || (target.getRow() == source.getRow() - 2) ) {
			enPassantVunerable = movedPiece;
		} else {
			enPassantVunerable = null;
		}
		
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
		Piece capturedPiece;
		capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		
		if(capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		//special move castling kingside rook
		if(p instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			Position targuetT = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
			board.placePiece(rook, targuetT);
			rook.incriseMoveCount();
		}
		
		// special move castling queenside rook
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			Position targuetT = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
			board.placePiece(rook, targuetT);
			rook.incriseMoveCount();
		}
		
		if(p instanceof Pawn) {
			if(source.getColumn() != target.getColumn() && capturedPiece == null) {
				Position pawnPosition;
				if(p.getColor() == Collor.WHITE) {
					pawnPosition = new Position(target.getRow() + 1, target.getColumn());
				}else {
					pawnPosition = new Position(target.getRow() - 1, target.getColumn());
				}
				capturedPiece = board.removePiece(pawnPosition);
				capturedPieces.add(capturedPiece);
				piecesOnTheBoard.remove(capturedPiece);
				
			}
		}
		
		return capturedPiece;
	}
	
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece p = (ChessPiece)board.removePiece(target);
		p.decriseMoveCount();
		board.placePiece(p, source);
		
		if(capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);

		}
		//special move castling kingside rook
		if(p instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			Position targuetT = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece)board.removePiece(targuetT);
			board.placePiece(rook, sourceT);
			rook.decriseMoveCount();
		}
		
		// special move castling queenside rook
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			Position targuetT = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece) board.removePiece(targuetT);
			board.placePiece(rook, sourceT);
			rook.decriseMoveCount();
		}
		
		if (p instanceof Pawn) {
			if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVunerable) {
				ChessPiece panw = (ChessPiece)board.removePiece(target);
				Position pawnPosition;
				if (p.getColor() == Collor.WHITE) {
					pawnPosition = new Position(3, target.getColumn());
				} else {
					pawnPosition = new Position(4, target.getColumn());
				}
				board.placePiece(panw, pawnPosition);
				
			}
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
		placeNewPiece('a', 1, new Rook(board, Collor.WHITE));
        placeNewPiece('b', 1, new Knight(board, Collor.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Collor.WHITE));
        placeNewPiece('d', 1, new Queen(board, Collor.WHITE));
        placeNewPiece('e', 1, new King(board, Collor.WHITE, this));
        placeNewPiece('f', 1, new Bishop(board, Collor.WHITE));
        placeNewPiece('g', 1, new Knight(board, Collor.WHITE));
        placeNewPiece('h', 1, new Rook(board, Collor.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Collor.WHITE, this));
        placeNewPiece('b', 2, new Pawn(board, Collor.WHITE, this));
        placeNewPiece('c', 2, new Pawn(board, Collor.WHITE, this));
        placeNewPiece('d', 2, new Pawn(board, Collor.WHITE, this));
        placeNewPiece('e', 2, new Pawn(board, Collor.WHITE, this));
        placeNewPiece('f', 2, new Pawn(board, Collor.WHITE, this));
        placeNewPiece('g', 2, new Pawn(board, Collor.WHITE, this));
        placeNewPiece('h', 2, new Pawn(board, Collor.WHITE, this));

        placeNewPiece('a', 8, new Rook(board, Collor.BLACK));
        placeNewPiece('b', 8, new Knight(board, Collor.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Collor.BLACK));
        placeNewPiece('d', 8, new Queen(board, Collor.BLACK));
        placeNewPiece('e', 8, new King(board, Collor.BLACK, this));
        placeNewPiece('f', 8, new Bishop(board, Collor.BLACK));
        placeNewPiece('g', 8, new Knight(board, Collor.BLACK));
        placeNewPiece('h', 8, new Rook(board, Collor.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Collor.BLACK, this));
        placeNewPiece('b', 7, new Pawn(board, Collor.BLACK, this));
        placeNewPiece('c', 7, new Pawn(board, Collor.BLACK, this));
        placeNewPiece('d', 7, new Pawn(board, Collor.BLACK, this));
        placeNewPiece('e', 7, new Pawn(board, Collor.BLACK, this));
        placeNewPiece('f', 7, new Pawn(board, Collor.BLACK, this));
        placeNewPiece('g', 7, new Pawn(board, Collor.BLACK, this));
        placeNewPiece('h', 7, new Pawn(board, Collor.BLACK, this));
	}

}
