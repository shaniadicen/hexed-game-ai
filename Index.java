
public class Index {
	int col;
	int row;
	String direction;

	public Index(int col, int row) {
		this.col = col;
		this.row = row;
	}

	public Index(int col, int row, String d) {
		this.col = col;
		this.row = row;
		direction = d;
	}

	public Index() {
		row = 0;
		col = 0;
		direction = null;
	}

	/**
	 * @return the col
	 */
	public int getCol() {
		return col;
	}

	/**
	 * @param col
	 *            the col to set
	 */
	public void setCol(int col) {
		this.col = col;
	}

	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @param row
	 *            the row to set
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * @return the direction
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * @param direction
	 *            the direction to set
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}

}
