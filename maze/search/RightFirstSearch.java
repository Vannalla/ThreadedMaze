package maze.search;

import maze.Maze;
import maze.MazeCell;



public class RightFirstSearch extends Search{
    public static final String NAME = "Right First Search";
    private Maze maze;
    private int currentId;
    private int direction = 0; // 1 = down  2 = up 3 =left  4 = right

    public RightFirstSearch(int numRows, int numCols, Maze maze) {
		this.maze = maze;
		this.numCols = numCols;
		this.numRows = numRows;
		this.currentId = 0;
    }

    public void start() {
        super.start();
        MazeCell current = this.maze.getCell(this.currentId);
        current.paintBlue();
    }
  
    public Maze getMaze() {
		return this.maze;
	}

    @Override
	public boolean finished() {
		return this.currentId == this.numCols*this.numRows;
	}

    public void step() throws UninitializedSearchException {
        super.step();
        MazeCell previous = this.maze.getCell(this.currentId);
		MazeCell current = this.maze.getCell(this.currentId);
        if (!current.hasWallDown() && direction != 2) {
            previous = current;
            previous.paintBlue();
            current = this.getCellBelow(this.currentId);
            current.paintBlue();
            direction = 1;
           // return;
        }else if(!current.hasWallRight()){
            previous = current;
            previous.paintBlue();
            current = this.getCellRight(this.currentId);
            current.paintBlue();
            direction = 4;
           // return;
        }else if(!current.hasWallLeft()){
            previous = current;
            previous.paintBlue();
            current = this.getCellLeft(this.currentId);
            current.paintBlue();
            direction = 3;
           // return;
        }else if(!current.hasWallUp()){
            previous = current;
            previous.paintBlue();
            current = this.getCellAbove(this.currentId);
            current.paintBlue();
            direction = 2;
           // return;
        }
        if (this.finished()) {
            // add code to save in global time variable to compare to left and return in a pop up
		}
}

private MazeCell getCellAbove(int currentId) {
    return this.maze.getCell(currentId-this.numCols);
}

private MazeCell getCellBelow(int currentId) {
    return this.maze.getCell(currentId+this.numCols);
}

private MazeCell getCellRight(int currentId) {
    return this.maze.getCell(currentId+1);
}

private MazeCell getCellLeft(int currentId) {
    return this.maze.getCell(currentId-1);
}


}
