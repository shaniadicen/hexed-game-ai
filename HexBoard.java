import java.util.ArrayList;

public class HexBoard {
	HexCell[][] hex;
	boolean[] isHexed = new boolean[2]; // team hexed or not G|R

	public HexBoard() {
		hex = new HexCell[9][7];
		isHexed[0] = false;
		isHexed[1] = false;
	}

	public HexBoard(HexCell[][] h) {
		hex = h;

	}

	public boolean bothHexed() {
		if (isHexed[0] == true && isHexed[1] == true) {
			System.out.println("Game Ends!");
			return true;
		} else {
			return false;
		}
	}

	public ArrayList<Index> generateMoves(char turn) {
		ArrayList<Index> moves = new ArrayList<Index>();
		ArrayList<Index> opIndex = new ArrayList<Index>();

		opIndex = getOpponentIndexes(negateColor(turn));

		for (int i = 0; i < opIndex.size(); i++) {

			if (checkNorth(opIndex.get(i), turn) == true) {
				moves.add(new Index(opIndex.get(i).getCol(), (opIndex.get(i).getRow() - 1), "N"));
			}

			if (checkSouth(opIndex.get(i), turn) == true) {
				moves.add(new Index(opIndex.get(i).getCol(), (opIndex.get(i).getRow() + 1), "S"));
			}

			int moveRow = opIndex.get(i).getRow();
			if (opIndex.get(i).getCol() % 2 != 0) {
				moveRow++;
			}

			if (checkSouthEast(opIndex.get(i), turn)) {
				moves.add(new Index(opIndex.get(i).getCol() - 1, moveRow, "SE"));

			}

			moveRow = opIndex.get(i).getRow();
			if (opIndex.get(i).getCol() % 2 != 0) {
				moveRow++;
			}

			if (checkSouthWest(opIndex.get(i), turn)) {
				moves.add(new Index(opIndex.get(i).getCol() + 1, moveRow, "SW"));

			}

			moveRow = opIndex.get(i).getRow();
			if (opIndex.get(i).getCol() % 2 == 0) {
				moveRow--;
			}

			if (checkNorthEast(opIndex.get(i), turn)) {
				moves.add(new Index(opIndex.get(i).getCol() - 1, moveRow, "NE"));
			}

			moveRow = opIndex.get(i).getRow();

			if (opIndex.get(i).getCol() % 2 == 0) {
				moveRow--;
			}

			if (checkNorthWest(opIndex.get(i), turn)) {
				moves.add(new Index(opIndex.get(i).getCol() + 1, moveRow, "NW"));

			}

		}

		return moves;
	}

	public void showPossibleMoves(ArrayList<Index> moves){
		System.out.println("\nPossible Moves: ");
		for (int j = 0; j < moves.size(); j++) {
			System.out.println("c:" + (moves.get(j).getCol()) + " r:"
					+ (moves.get(j).getRow() + " direction: " + moves.get(j).getDirection()));
		}
	}

	public void fixSandwichedColor(ArrayList<Index> move, char turn) {
		ArrayList<Index> sandwiched = getInBetweenCells(move, turn);
		for (int k = 0; k < sandwiched.size(); k++) {
			hex[sandwiched.get(k).getCol()][sandwiched.get(k).getRow()].setColor(turn);
		}
	}

	public ArrayList<Index> getInBetweenCells(ArrayList<Index> move, char turn) {
		ArrayList<Index> sandwiched = new ArrayList<Index>();

		for (int j = 0; j < move.size(); j++) {
			if (move.get(j).getDirection().equals("N")) {
				// Going North
				for (int i = move.get(j).getRow() + 1; i < 7; i++) {
					if (hex[move.get(j).getCol()][i].getColor() == negateColor(turn)) {
						sandwiched.add(new Index(move.get(j).getCol(), i, "N"));
					} else if (hex[move.get(j).getCol()][i].getColor() == '\u0000'
							|| hex[move.get(j).getCol()][i].getColor() == turn) {
						break;
					}
				}
			} else if (move.get(j).getDirection().equals("S")) {
				// Going South
				for (int i = move.get(j).getRow() - 1; i >= 0; i--) {
					if (hex[move.get(j).getCol()][i].getColor() == negateColor(turn)) {
						sandwiched.add(new Index(move.get(j).getCol(), i, "S"));
					} else if (hex[move.get(j).getCol()][i].getColor() == '\u0000'
							|| hex[move.get(j).getCol()][i].getColor() == turn) {
						break;
					}
				}
			} else if (move.get(j).getDirection().equals("SE")) {
				// Going South East
				int row = move.get(j).getRow();
				for (int col = move.get(j).getCol() + 1; col < 9; col++) {
					if (col % 2 != 0) {
						row--;
					} else if (col % 2 == 0) {
						int temp = row;
						row = temp;
					}

					if (row < 7 && row >= 0) {
						if (hex[col][row].getColor() == negateColor(turn)) {
							sandwiched.add(new Index(col, row, "SE"));
						} else if (hex[col][row].getColor() == '\u0000' || hex[col][row].getColor() == turn) {
							break;
						}
					} else {
						break;
					}

				}

			} else if (move.get(j).getDirection().equals("SW")) {
				// Going South West
				int row = move.get(j).getRow();
				for (int col = move.get(j).getCol() - 1; col >= 0; col--) {

					if (col % 2 != 0) {
						row--;
					} else if (col % 2 == 0) {
						int temp = row;
						row = temp;
					}

					if (row < 7 && row >= 0) {
						if (hex[col][row].getColor() == negateColor(turn)) {
							sandwiched.add(new Index(col, row, "SW"));
						} else if (hex[col][row].getColor() == '\u0000' || hex[col][row].getColor() == turn) {
							break;
						}

					} else {
						break;
					}

				}
			} else if (move.get(j).getDirection().equals("NE")) {
				// Going North East
				int row = move.get(j).getRow();
				for (int col = move.get(j).getCol() + 1; col < 9; col++) {
					if (col % 2 == 0) {
						row++;
					} else if (col % 2 != 0) {
						int temp = row;
						row = temp;
					}

					if (row < 7 && row >= 0) {
						if (hex[col][row].getColor() == negateColor(turn)) {
							sandwiched.add(new Index(col, row, "NE"));
						} else if (hex[col][row].getColor() == '\u0000' || hex[col][row].getColor() == turn) {

							break;
						}
					} else {
						break;
					}

				}

			} else if (move.get(j).getDirection().equals("NW")) {
				// Going North West
				int row = move.get(j).getRow();

				for (int col = move.get(j).getCol() - 1; col >= 0; col--) {

					if (col % 2 == 0) {
						row++;
					} else if (col % 2 != 0) {
						int temp = row;
						row = temp;
					}

					if (row >= 0 && row < 7) {
						if (hex[col][row].getColor() == negateColor(turn)) {
							sandwiched.add(new Index(col, row, "NW"));
						} else if (hex[col][row].getColor() == '\u0000' || hex[col][row].getColor() == turn) {
							break;
						}
					} else {
						break;
					}

				}
			}
		}
		return sandwiched;
	}

	public boolean checkNorthWest(Index h, char teamColor) {
		boolean valid = false;

		int row = h.getRow();
		int moveRow = h.getRow();

		if (h.getCol() % 2 == 0) {
			moveRow--;
		}
		for (int col = h.getCol() - 1; col >= 0; col--) {

			if (col % 2 == 0) {
				row++;
			} else if (col % 2 != 0) {
				int temp = row;
				row = temp;
			}

			if (row < 7 && row >= 0) {
				if (hex[col][row].getColor() == teamColor) {
					if (doesExist(h.getCol() + 1, moveRow)) {
						if (hex[h.getCol() + 1][moveRow].getColor() == '\u0000') {
							if (hex[h.getCol() - 1][moveRow].isValidCell() == true) {
								valid = true;
								break;
							}
						}
					}
				} else if (hex[col][row].getColor() == '\u0000') {
					valid = false;
					break;
				}
			} else {
				valid = false;
				break;
			}
		}
		return valid;
	}

	public boolean checkNorthEast(Index h, char teamColor) {
		boolean valid = false;
		int row = h.getRow();
		int moveRow = h.getRow();

		if (h.getCol() % 2 == 0) {
			moveRow--;
		}
		for (int col = h.getCol() + 1; col < 9; col++) {
			if (col % 2 == 0) {
				row++;
			} else if (col % 2 != 0) {
				int temp = row;
				row = temp;
			}

			if (row > 0 && row < 7) {
				if (hex[col][row].getColor() == teamColor) {
					if (doesExist(h.getCol() - 1, moveRow)) {
						if (hex[h.getCol() - 1][moveRow].getColor() == '\u0000') {
							if (hex[h.getCol() - 1][moveRow].isValidCell() == true) {
								valid = true;
								break;
							}
						}
					}
				} else if (hex[col][row].getColor() == '\u0000') {
					valid = false;
					break;
				}
			} else {
				valid = false;
				break;
			}

		}
		return valid;
	}

	/**
	 * Checks if south west of the opponent is in between a team color
	 *
	 * @param h
	 * @param teamColor
	 * @return
	 */
	public boolean checkSouthWest(Index h, char teamColor) {
		boolean valid = false;

		int row = h.getRow();
		int moveRow = h.getRow();
		if (h.getCol() % 2 != 0) {
			moveRow++;
		}
		for (int col = h.getCol() - 1; col >= 0; col--) {

			if (col % 2 != 0) {
				row--;
			} else if (col % 2 == 0) {
				int temp = row;
				row = temp;
			}

			if (row < 7 && row >= 0) {
				if (hex[col][row].getColor() == teamColor) {
					if (doesExist(h.getCol() + 1, moveRow)) {
						if (hex[h.getCol() + 1][moveRow].getColor() == '\u0000') {
							if (hex[h.getCol() + 1][moveRow].isValidCell() == true) {
								valid = true;
								break;
							}

						}
					}

				} else if (hex[col][row].getColor() == '\u0000') {
					valid = false;
					break;
				}
			} else {
				valid = false;
				break;
			}

		}

		return valid;
	}

	/**
	 * Checks south east of the opponent if it is in between a team color
	 *
	 * @param h
	 * @param teamColor
	 * @return
	 */

	public boolean checkSouthEast(Index h, char teamColor) {
		boolean valid = false;

		int row = h.getRow();

		int moveRow = h.getRow();
		if (h.getCol() % 2 != 0) {
			moveRow++;
		}
		for (int col = h.getCol() + 1; col < 9; col++) {

			if (col % 2 != 0) {
				row--;
			} else if (col % 2 == 0) {
				int temp = row;
				row = temp;
			}

			if (row >= 0 && row < 7) {
				if (hex[col][row].getColor() == teamColor) {
					if (doesExist(h.getCol() - 1, moveRow)) {
						if (hex[h.getCol() - 1][moveRow].getColor() == '\u0000') {
							if (hex[h.getCol() - 1][moveRow].isValidCell() == true) {
								valid = true;
								break;
							}
						}
					}

				} else if (hex[col][row].getColor() == '\u0000') {
					valid = false;
					break;
				}
			} else {
				valid = false;
				break;
			}
		}

		return valid;
	}

	/**
	 * Checks south of the opponent of it is in between a team color
	 *
	 * @param h
	 * @param teamColor
	 * @return
	 */

	public boolean checkSouth(Index h, char teamColor) {
		boolean valid = false;
		for (int i = h.getRow() - 1; i >= 0; i--) {

			if (hex[h.getCol()][i].getColor() == teamColor) {
				if (doesExist(h.getCol(), h.getRow() + 1)) {
					if (hex[h.getCol()][h.getRow() + 1].getColor() == '\u0000') {
						if (hex[h.getCol()][h.getRow() + 1].isValidCell() == true) {
							valid = true;
							break;
						}
					}
				}

			} else if (hex[h.getCol()][i].getColor() == '\u0000') {
				valid = false;
				break;
			}
		}
		return valid;
	}

	/**
	 * Checks north of the opponent of it is in between a team color
	 *
	 * @param h
	 * @param teamColor
	 * @return
	 */
	public boolean checkNorth(Index h, char teamColor) {
		boolean valid = false;
		for (int i = h.getRow() + 1; i < 7; i++) {
			if (hex[h.getCol()][i].getColor() == teamColor) {
				if (doesExist(h.getCol(), h.getRow() - 1)) {
					if (hex[h.getCol()][h.getRow() - 1].getColor() == '\u0000') {
						if (hex[h.getCol()][h.getRow() - 1].isValidCell() == true) {
							valid = true;
							break;
						}
					}
				}

			} else if (hex[h.getCol()][i].getColor() == '\u0000') {
				valid = false;
				break;
			}
		}
		return valid;
	}

	public ArrayList<Index> getOpponentIndexes(char opColor) { // column,row
		ArrayList<Index> opponentIndexes = new ArrayList<Index>();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 7; j++) {
				if (hex[i][j].getColor() == opColor) {
					opponentIndexes.add(new Index(i, j));
				}
			}
		}

		return opponentIndexes;
	}

	public HexCell[][] initializeGame(int col, int row, char color) {

		hex[col][row].setColor(color);

		if (col % 2 == 0) { // if col is odd
			if (color == 'g') {
				hex[col + 1][row - 1].setColor('r'); // 54
				hex[col + 1][row - 2].setColor('g'); // 53
				hex[col][row - 2].setColor('r'); // 43
				hex[col - 1][row - 2].setColor('g'); // 33
				hex[col - 1][row - 1].setColor('r'); // 34
				// col: 4 row: 5
			} else if (color == 'r') {
				hex[col + 1][row - 1].setColor('g'); // 54
				hex[col + 1][row - 2].setColor('r'); // 53
				hex[col][row - 2].setColor('g'); // 43
				hex[col - 1][row - 2].setColor('r'); // 33
				hex[col - 1][row - 1].setColor('g'); // 34
			} else {
				System.out.println("color invalid");
			}
		} else {
			if (color == 'g') {
				hex[col + 1][row].setColor('r');
				hex[col - 1][row].setColor('r');
				hex[col - 1][row - 1].setColor('g');
				hex[col + 1][row - 1].setColor('g');
				hex[col][row - 2].setColor('r');
			} else if (color == 'r') {
				hex[col + 1][row].setColor('g');
				hex[col - 1][row].setColor('g');
				hex[col - 1][row - 1].setColor('r');
				hex[col + 1][row - 1].setColor('r');
				hex[col][row - 2].setColor('g');
			} else {
				System.out.println("color invalid");
			}
		}

		return hex;
	}

	/**
	 * @return the hex
	 */
	public HexCell[][] getHex() {
		return hex;
	}

	/**
	 * @param hex
	 *            the hex to set
	 */
	public void setHex(HexCell[][] hex) {
		this.hex = hex;
	}

	public char negateColor(char c) {
		if (c == 'g') {
			return 'r';
		} else {
			return 'g';
		}
	}

	public boolean doesExist(int col, int row) {
		boolean yes = false;
		ArrayList<Index> temp = new ArrayList<Index>();

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 7; j++) {
				if (i % 2 != 0 && j == 6) {
				} else {
					Index id = new Index();
					id.setCol(i);
					id.setRow(j);
					temp.add(id);
				}
			}
		}

		for (int x = 0; x < temp.size(); x++) {
			if ((temp.get(x).getCol() == col) && (temp.get(x).getRow() == row)) {
				yes = true;
				return yes;
			}
		}

		return yes;
	}

	public void printHexGrid() {
		for (int i = 6; i >= 0; i--) {
			for (int j = 0; j < 9; j++) {
				if (hex[j][i].isValidCell()) {

					if (hex[j][i].getColor() == '\u0000') {
						System.out.print("* ");
					} else {
						System.out.print(hex[j][i].getColor());
					}

				} else {
					System.out.print(" ");

				}

				System.out.print("\t");
			}

			System.out.println();
		}
	}

	public HexCell[][] generateGrid() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 7; j++) {
				hex[i][j] = new HexCell();
				hex[i][j].setCol(i);
				hex[i][j].setRow(j);

				if (i % 2 != 0 && j == 6) {
					hex[i][j].setValidCell(false);
				}
			}
		}

		return hex;
	}

	// lists all occupied cell with col,row and color
	public void printOccupiedCell() {
		for (int c = 0; c < 9; c++) {
			for (int r = 0; r < 7; r++) {
				if (hex[c][r].getColor() != '\u0000') {
					System.out.println(c + "," + r + "," + hex[c][r].getColor());
				}
			}
		}
	}

	/**
	 * @return the isHexed
	 */
	public boolean[] getIsHexed() {
		return isHexed;
	}

	/**
	 * @param isHexed
	 *            the isHexed to set
	 */
	public void setIsHexed(boolean[] isHexed) {
		this.isHexed = isHexed;
	}

}
