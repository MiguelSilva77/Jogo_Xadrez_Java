package program;

import java.util.Scanner;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Main {
	public static void main(String[]args) {
		ChessMatch chessMatch = new ChessMatch();
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			UI.printBoard(chessMatch.getPieces());
			System.out.println();
			System.out.println("Source: ");
			ChessPosition source = UI.readChesssposition(sc);
			
			System.out.println();
			System.out.println("target: ");
			ChessPosition target = UI.readChesssposition(sc);
			
			ChessPiece capturedPiece = chessMatch.performeChassMovie(source, target);

		}
	}

}
