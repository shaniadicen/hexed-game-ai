public class State{
	private int utility;
	private State parent;
	private char iden;
	private HexBoard board;
	private int depth;
	private Index move;

	public State(){
		this.utility = 0;
		this.parent = null;
		this.iden = 0;
		this.board = null;
		this.depth = 0;
		this.move = null;
	}

	public State(int u, State p, char i, HexBoard b, Index m){
		this.utility = u;
		this.parent = p;
		this.iden = i;
		this.board = b;
		this.depth = (this.parent == null)? 0 : this.parent.depth+1;
		this.move = m;
	}

	public int getUtility(){
		return this.utility;
	}

	public char getIden(){
		return this.iden;
	}

	public int getDepth(){
		return this.depth;
	}

	public State getParent(){
		return this.parent;
	}

	public Index getMove(){
		return this.move;
	}

	public HexBoard getBoard(){
		return this.board;
	}

	public void setUtility(int u){
		this.utility = u;
	}

	public void setIden(char i){
		this.iden = i;
	}

	public void setDepth(int d){
		this.depth = d;
	}

	public void setParent(State p){
		this.parent = p;
	}

	public void setMove(Index move){
		this.move = move;
	}

	public void setBoard(HexBoard board){
		this.board = board;
	}
}