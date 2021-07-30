package maze.search;

import maze.Maze;
import maze.MazeCell;


public class LeftFirstSearch extends Search{

    public static final String NAME = "Left First Search";

    private Maze maze;
    private int currentId;


    public LeftFirstSearch(int numRows, int numCols, Maze maze) {
		this.maze = maze;
		this.numCols = numCols;
		this.numRows = numRows;
		this.currentId = 0;
	}

    public void start() {
        super.start();
        MazeCell current = this.maze.getCell(this.currentId);
        current.paintBlue();
        this.currentId++;
    }
	

    public Maze getMaze() {
		return this.maze;
	}

    @Override
	public boolean finished() {
		return this.currentId == this.numCols*this.numRows;
	}

}
