
package maze;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class MazeCell extends JPanel{

	/**
	 * 
	 */

	private Coordinate coord;
	
	protected boolean hasWallUp;
	protected boolean hasWallDown;
	protected boolean hasWallLeft;
	protected boolean hasWallRight;
	private boolean	visited;
	private boolean	blue;
	private boolean	red;
	private boolean green;
	
	public void paintComponent(Graphics graphics, int height, int width) {
		super.paintComponent(graphics);
		int x = this.coord.getX() * width;
		int y = this.coord.getY() * height;
		Color oldColor = graphics.getColor();
		if (this.blue) {
			graphics.setColor(new Color(28, 185, 253, 84));
			graphics.fillRect(x, y, width, height);
		}
		else if (this.red) {
			graphics.setColor(new Color(215, 20, 20, 227));
			graphics.fillRect(x, y, width, height);
		}
		else if (this.green) {
			graphics.setColor(new Color(20, 215, 26, 176));
			graphics.fillRect(x, y, width, height);
		}
		graphics.setColor(oldColor);
		if (this.hasWallUp) {
			graphics.drawLine(x, y, x+width, y);
		}
		if (this.hasWallDown) {
			graphics.drawLine(x, y+height, x+width, y+height);
		}
		if (this.hasWallLeft) {
			graphics.drawLine(x, y, x, y+height);
		}
		if (this.hasWallRight) {
			graphics.drawLine(x+width, y, x+width, y+height);
		}
	}
	
	public void paintBlue() {
		this.blue = true;
		this.red = false;
		this.green = false;
	}
	
	public void paintRed() {
		this.blue = false;
		this.red = true;
		this.green = false;
	}
	
	public void paintGreen() {
		this.blue = false;
		this.red = false;
		this.green = true;
	}
	
	public void removePaint() {
		this.blue = false;
		this.red = false;
		this.green = false;
	}
	
	public boolean hasWallUp() {
		return this.hasWallUp;
	}
	
	public boolean hasWallDown() {
		return this.hasWallDown;
	}
	
	public boolean hasWallLeft() {
		return this.hasWallLeft;
	}
	
	public boolean hasWallRight() {
		return this.hasWallRight;
	}
	
	public void addWallUp() {
		this.hasWallUp = true;
	}
	
	public void addWallDown() {
		this.hasWallDown = true;
	}
	
	public void addWallLeft() {
		this.hasWallLeft = true;
	}
	
	public void addWallRight() {
		this.hasWallRight = true;
	}
	
	public void removeWallUp() {
		this.hasWallUp = false;
	}
	
	public void removeWallDown() {
		this.hasWallDown = false;
	}
	
	public void removeWallLeft() {
		this.hasWallLeft = false;
	}
	
	public void removeWallRight() {
		this.hasWallRight = false;
	}
	
	public MazeCell(int row, int col, boolean withWalls) {
		super();
		this.coord = new Coordinate(row, col);
		this.visited = false;
		this.removePaint();
		if (withWalls) {
			this.hasWallUp = true;
			this.hasWallDown = true;
			this.hasWallLeft = true;
			this.hasWallRight = true;
		}
		else {
			this.hasWallUp = false;
			this.hasWallDown = false;
			this.hasWallLeft = false;
			this.hasWallRight = false;
		}
	}
	
	public Coordinate getCoordinate() {
		return this.coord;
	}
	
	public void markVisited() {
		this.visited = true;
	}
	
	public boolean hasBeenVisited() {
		return this.visited;
	}
	
	public int getRow() {
		return this.coord.getX();
	}

	public int getCol() {
		return this.coord.getY();
	}
	
	public boolean isContiguous(MazeCell other) {
		return this.coord.isContiguous(other.coord);
	}
	
	public boolean isBelow(MazeCell other) {
		return this.coord.getY() > other.coord.getY();
	}
	
	public boolean isAbove(MazeCell other) {
		return this.coord.getY() < other.coord.getY();
	}
	
	public boolean isLeftFrom(MazeCell other) {
		return this.coord.getX() < other.coord.getX();
	}
	
	public boolean isRightFrom(MazeCell other) {
		return this.coord.getX() > other.coord.getX();
	}
	
	public String toString() {
		return "Cell("+this.coord+")";
	}
}
