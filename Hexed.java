import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Hexed {
	HexBoard hex = new HexBoard();
	char firstMove;
	char teamColor;
	char turn;
	int redScore;
	int greenScore;
	boolean initialMove = true;
	State state;
	Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		Hexed program = new Hexed();
		program.run();
	}

	public void run() {
		start();
	}

	public void start() {
		hex.generateGrid();
		System.out.print("====Start Game====\n");
		System.out.print("Column: ");
		int col = Integer.parseInt(sc.nextLine());
		System.out.print("Row: ");
		int row = Integer.parseInt(sc.nextLine());
		System.out.print("Color[g,r]: ");
		char color = sc.nextLine().charAt(0);
		hex.initializeGame(col, row, color);
		System.out.print("First Move[g,r]: ");
		firstMove = sc.nextLine().charAt(0);
		turn = firstMove;
		System.out.println("==================");
		System.out.print("Team Color[g,r]: ");
		teamColor = sc.nextLine().charAt(0);

		hex.printHexGrid();
		System.out.println("==================");
		boolean cont = true;

		int utility = (teamColor==color)? -100 : 100;
		state = new State(utility,null,teamColor,hex,null);
		int limit = 6;

		do {
			int[] numberOfMoves = playGame(limit,state);
			int teamNum = numberOfMoves[0];
			int opNum = numberOfMoves[1];
			if (bothHexed(teamNum, opNum) == true) {
				cont = false;
			}
			hex.printHexGrid();
			System.out.println("===Scores===\nRed: " + redScore + "\nGreen: " + greenScore);
		} while (cont);
	}

	public Index makeMove(State state, int limit){
		Stack<State> frontier = new Stack<State>();
		ArrayList<State> children = generateChildren(state);
		Index move = null;
		int maxUtility = -100;
		for(int i=0;i<children.size();i++){
			frontier.push(children.get(i));
		}
		do{
			State currState = frontier.pop();
			if(currState.getDepth()<=limit){
				children = generateChildren(currState);
				for(int i=0; i<children.size(); i++){
					frontier.push(children.get(i));
				}
				if((currState.getMove()== null && currState.getParent().getMove()==null) || currState.getDepth() == limit){
					currState.setUtility(computeUtility(currState.getBoard()));
					while(currState.getParent()!=state){
						if(currState.getParent().getIden()==teamColor){
							if(currState.getParent().getUtility() < currState.getUtility()) currState.getParent().setUtility(currState.getUtility());
						}
						else{
							if(currState.getParent().getUtility() > currState.getUtility()) currState.getParent().setUtility(currState.getUtility());
						}
						currState = currState.getParent();
						if(currState.getParent() == state){
							maxUtility = Math.max(maxUtility, currState.getUtility());
							if(maxUtility == currState.getUtility()){
								move = currState.getMove();
							}
						}
					}
				}
			}
		}while(!frontier.isEmpty());
		return move;
	}

	public ArrayList<State> generateChildren(State parent){
		ArrayList<State> children = new ArrayList<State>();
		ArrayList<Index> legalMoves = parent.getBoard().generateMoves(parent.getIden());
		char childColor = parent.getBoard().negateColor(parent.getIden());
		if(legalMoves.size()==0){
			int utility = (parent.getIden()==teamColor)?  100 : -100;
			HexBoard childBoard = createBoard(parent.getBoard().getHex());
			children.add(new State(utility,parent,childColor,childBoard,null));
		}
		else {
			for(int i=0; i<legalMoves.size(); i++){
				int utility = (parent.getIden()==teamColor)?  100 : -100;
				HexBoard childBoard = createBoard(parent.getBoard().getHex());
				ArrayList<Index> moves = getChosenMove(legalMoves, legalMoves.get(i).getCol(), legalMoves.get(i).getRow());
				childBoard.getHex()[legalMoves.get(i).getCol()][legalMoves.get(i).getRow()].setColor(childColor);
				childBoard.fixSandwichedColor(moves, parent.getIden());
				children.add(new State(utility,parent,childColor,childBoard,legalMoves.get(i)));
			}
		}
		return children;
	}

	public int computeUtility(HexBoard board){
		char oppColor = board.negateColor(teamColor);
		int oppTiles = 0;
		int teamTiles = 0;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 7; j++) {
				if(board.getHex()[i][j].getColor() == teamColor) teamTiles++;
				if(board.getHex()[i][j].getColor() == oppColor) oppTiles++;
			}
		}
		return teamTiles - oppTiles;
	}

	public HexBoard createBoard(HexCell[][] hexcells){
		HexBoard newBoard = new HexBoard();
		HexCell[][] cells = new HexCell[9][7];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 7; j++) {
				cells[i][j] = new HexCell();
				cells[i][j].setCol(i);
				cells[i][j].setRow(j);
				cells[i][j].setColor(hexcells[i][j].getColor());
				cells[i][j].setValidCell(hexcells[i][j].isValidCell());
			}
		}
		newBoard.setHex(cells);
		return newBoard;
	}

	public void generateScores() {
		redScore = 0;
		greenScore = 0;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 7; j++) {
				if (hex.hex[i][j].getColor() == 'r') {
					redScore++;
				} else if (hex.hex[i][j].getColor() == 'g') {
					greenScore++;
				}
			}
		}
	}

	public int[] playGame(int limit,State state) {
		ArrayList<Index> opponentMoves = new ArrayList<Index>();
		ArrayList<Index> teamMoves = new ArrayList<Index>();
		ArrayList<Index> moves = new ArrayList<Index>();

		if (teamColor == turn) {// our turn
			teamMoves = hex.generateMoves(turn);
			hex.showPossibleMoves(teamMoves);
			if (isHexed(teamMoves)) {
				turn = hex.negateColor(turn);
				opponentMoves = hex.generateMoves(turn);
				hex.showPossibleMoves(opponentMoves);
			} else {
				limit += 2;
				Index move = makeMove(state,limit);
				if(move==null) move = teamMoves.get(0);
				moves = getChosenMove(teamMoves, move.getCol(), move.getRow());
				System.out.println("\nMove:");
				System.out.println("col: " + move.getCol() + " ; row:" + move.getRow());
				hex.hex[move.getCol()][move.getRow()].setColor(teamColor);
				hex.fixSandwichedColor(moves, teamColor);
				generateScores();
				turn = hex.negateColor(turn);
			}

		} else {
			opponentMoves = hex.generateMoves(turn);
			hex.showPossibleMoves(opponentMoves);
			if (isHexed(opponentMoves)) {
				turn = hex.negateColor(turn);
				teamMoves = hex.generateMoves(turn);
				hex.showPossibleMoves(teamMoves);
			} else {
				opponentMoves = hex.generateMoves(turn);
				Index opMove = opponentMoves.get((int) (Math.random() * opponentMoves.size()-1));
				int opCol = opMove.getCol();
				int opRow = opMove.getRow();
				for (int x = 0; x < opponentMoves.size(); x++) {
					if (opponentMoves.get(x).getCol() == opCol && opponentMoves.get(x).getRow() == opRow) {
						opMove = new Index(opCol, opRow, opponentMoves.get(x).getDirection());
					}
				}

				hex.hex[opCol][opRow].setColor(hex.negateColor(teamColor));

				moves = getChosenMove(opponentMoves, opCol, opRow);
				hex.fixSandwichedColor(moves, hex.negateColor(teamColor));
				 generateScores();
				turn = hex.negateColor(turn);
			}
		}
		state = new State(-100,state,turn,hex,null);

		int[] numMoves = { teamMoves.size(), opponentMoves.size() };
		return numMoves;
	}


	public ArrayList<Index> getChosenMove(ArrayList<Index> pMoves, int col, int row) {
		ArrayList<Index> moves = new ArrayList<Index>();
		for (int i = 0; i < pMoves.size(); i++) {
			if (col == pMoves.get(i).getCol() && row == pMoves.get(i).getRow()) {
				moves.add(pMoves.get(i));
			}
		}

		return moves;
	}

	public boolean isHexed(ArrayList<Index> moves) {
		if (moves.size() == 0) {
			System.out.println("Hexed!");
			return true;
		} else {
			return false;
		}
	}

	public boolean bothHexed(int team, int op) {
		if (team == 0 && op == 0) {
			System.out.println("Game Ends!");
			return true;
		} else {
			return false;
		}
	}

	public int evaluate(HexBoard board) {

		return firstMove;
	}

	public boolean isMovesLeft(HexBoard board) {
		if (board.bothHexed()) {
			return false;
		}
		return true;
	}

	public Index findBestMove(HexBoard board) {
		Index bestMove = new Index();
		int bestVal = -1000;
		bestMove.row = -1;
		bestMove.col = -1;

		return bestMove;

	}
}
