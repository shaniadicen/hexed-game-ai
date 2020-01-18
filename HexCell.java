
public class HexCell {
	private HexCell[] neighbors; // 0=N 1=NE 2=SE 3=S 4=SW 5=NW
	private char color;
	private boolean validCell; // 0=invalid 1=valid
	private int col;
	private int row;

	public HexCell() {
		validCell = true;
		neighbors = new HexCell[6]; // 0=N 1=NE 2=SE 3=S 4=SW 5=NW
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public void setRow(int r) {
		this.row = r;
	}

	public void setCol(int c) {
		this.col = c;
	}

	/**
	 * @return the neighbors
	 */
	public HexCell[] getNeighbors() {
		return neighbors;
	}

	/**
	 * @param neighbors
	 *            the neighbors to set
	 */
	public void setNeighbors(HexCell[] neighbors) {
		this.neighbors = neighbors;
	}

	/**
	 * @return the color
	 */
	public char getColor() {
		return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(char color) {
		this.color = color;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HexCell [Column= " + col + ", Row= " + row + ", Color=" + color + "]";
	}

	/**
	 * @return the validCell
	 */
	public boolean isValidCell() {
		return validCell;
	}

	/**
	 * @param validCell
	 * the validCell to set
	 */
	public void setValidCell(boolean validCell) {
		this.validCell = validCell;
	}

	public boolean hasPlank() {
		boolean flag = false;
		
		return flag;
	}
}
