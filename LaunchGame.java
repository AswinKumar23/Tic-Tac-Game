package tic_Tac_Toe;

import java.util.Random;
import java.util.Scanner;

class Tictactoe{
	static char[][] board;
	public Tictactoe() {
		board = new char[3][3];
		initBoard();
	}
	void initBoard() {
		for(int i=0;i<board.length;i++) {
			for(int j=0;j<board.length;j++) {
				board[i][j]=' ';
			}
		}
	}
	
	void dispBoard() {
		System.out.println("-------------");
		for(int i=0;i<board.length;i++) {
			for(int j=0;j<board.length;j++) {
				System.out.print("| "+ board[i][j]+" "  );			
			}
			System.out.println("|");
			System.out.println("-------------");
		}
	}
	
	static void placeMark(int row, int col, char mark) {
		board[row][col]= mark;
	}
	
	Boolean checkrowWin() {
		for(int i=0;i<board.length;i++) {
			if(board[i][0]!=' ' && board[i][0]==board[i][1] && board[i][1]==board[i][2]) {
				return true;
			}
		}
		return false;
	}
	
	Boolean checkcolWin() {
		for(int i=0;i<board.length;i++) {
			if(board[0][i]!=' ' && board[0][i]==board[1][i] && board[1][i]==board[2][i]) {
				return true;
			}
		}
		return false;
	}
	
	Boolean checkdiagWin() {
		if((board[0][0]!=' ' && board[0][0]==board[1][1] && board[1][1]==board[2][2])
				||(board[0][2]!=' ' && board[0][2]==board[1][1] && board[1][1]==board[2][0])) {
			return true;
		}	
		return false;
	}
	
	Boolean checkdraw() {
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				if(board[i][j]==' ') {
					return false;
				}
			}
		}
		return true;
	}
}

abstract class Player{
	String  name;
	char mark;
	abstract void makeMove();
	Boolean isvalidMove(int row,int col) {
		if (row>=0&&row<3&&col>=0&&col<3) {
			if(Tictactoe.board[row][col]==' ') {
				return true;
			}
		}
		return false;
		
	}
	
}

class Humanplayer extends Player{
	Scanner scanner = new Scanner(System.in);
	public Humanplayer(String a, char b) {
		name = a;
		mark = b;
		
		
	}
	void makeMove() {
		int row;
		int col;
		do {
			System.out.println("Enter a valid move: ");
			row = scanner.nextInt();
			col = scanner.nextInt();
			} while (!isvalidMove(row,col));
		Tictactoe.placeMark(row, col, mark);
		
	}
	
	
}

class AIplayer extends Player{
	Scanner scanner = new Scanner(System.in);
	
	
	public AIplayer(String a, char b) {
		name = a;
		mark = b;
	}
	void makeMove() {
		int row;
		int col;
		Random r = new Random();
		do{
			row = r.nextInt(3);
			col = r.nextInt(3);
		}while(!isvalidMove(row, col));
		
		Tictactoe.placeMark(row, col, mark);
		
	}

	
}

public class LaunchGame {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		Tictactoe t = new Tictactoe();
		t.dispBoard();
		Humanplayer p2 = null ;
		Humanplayer p3 = null;
		Humanplayer p4 = null;
		AIplayer p1 = null;
		Player cp = null;
		System.out.println("Choose s for single player and m for multiplayer:");
		String option;
		do{
			option = scan.nextLine();
		}while(!option.equalsIgnoreCase("s") && !option.equalsIgnoreCase("m"));
		if(option.equalsIgnoreCase("s")) {
			System.out.print("Enter the player name: ");
			String name1 = scan.nextLine();
			char mark1;
			char mark2;
			System.out.print("choose X or O: ");
			while (true) {
			    String input = scan.nextLine().trim().toUpperCase(); // Read and trim input
			    if (input.equals("X") || input.equals("O")) {
			        mark1 = input.charAt(0); // Assign mark1 if valid
			        break; // Exit the loop
			    } else {
			        System.out.print("Invalid choice. Please choose X or O: ");
			    }
			}
			mark2 = (mark1 == 'X') ? 'O' : 'X';
			if (mark1 == 'X') {
		        p2 = new Humanplayer(name1, mark1); // Human gets X
		        p1 = new AIplayer("AI", mark2);     // AI gets O
		        cp = p2; // X plays first, so cp = human player
		    } else {
		        p1 = new AIplayer("AI", mark2);     // AI gets X
		        p2 = new Humanplayer(name1, mark1); // Human gets O
		        cp = p1; // X plays first, so cp = AI player
		    }
			
			
		}
		else {
			System.out.print("Enter the first player name: ");
			String name2 = scan.nextLine();
			System.out.print("Enter the second player name: ");
			String name3 = scan.nextLine();
			p3 = new Humanplayer(name2,'X');
			p4 = new Humanplayer(name3,'O');
			cp = p3;
		}
	
		while(true) {
			System.out.println(cp.name +"'s turn:");
			cp.makeMove();
			t.dispBoard();
			if(t.checkrowWin()||t.checkcolWin()||t.checkdiagWin()) {
				System.out.println(cp.name+ " has won the game.");
				break;
			}
			else if (t.checkdraw()) {
				System.out.println("The game is draw.");
				break;
			}
			if (option.equalsIgnoreCase("s")) { // Single-player turn switching
		        cp = (cp == p1) ? p2 : p1;
		    } else { // Multiplayer turn switching
		        cp = (cp == p3) ? p4 : p3;
		    }
		}
	}

}







