package at.jku.cis.iVolunteer.core.dashboard;

public class Dashlet {
	private int x;
	private int y;
	private int rows;
	private int cols;
	private String id;
	private int minItemRows;
	private int minItemCols;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getMinItemRows() {
		return minItemRows;
	}

	public void setMinItemRows(int minItemRows) {
		this.minItemRows = minItemRows;
	}

	public int getMinItemCols() {
		return minItemCols;
	}

	public void setMinItemCols(int minItemCols) {
		this.minItemCols = minItemCols;
	}
}
