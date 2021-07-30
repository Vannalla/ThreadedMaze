
package maze;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import maze.generate.BinaryTreeAlgorithm;
import maze.generate.EllerAlgorithm;
import maze.generate.Generator;
import maze.generate.RecursiveBackTracker;
import maze.generate.UninitializedGeneratorException;
import maze.search.Search;
import maze.search.UninitializedSearchException;
import maze.search.LeftFirstSearch;
import maze.search.RightFirstSearch;

public class MazeBoard extends JPanel {

	/**
	 * 
	 */

	private Maze maze;

	private Generator generator;

	private Search searchLeft;
	private Search searchRight;

	private int height;
	private int width;
	private int cellH;
	private int cellW;
	private int numRows;
	private int numCols;

	private long stepDelay;

	private String selectedAlgorithm;

	public MazeBoard(Generator generator) {
		super();
		this.maze = generator.getMaze();
		this.generator = generator;
		this.height = 200;
		this.width = 400;
		this.numRows = this.maze.getNumRows();
		this.numCols = this.maze.getNumCols();
		this.setPreferredSize(new Dimension(this.width, this.height));
	}

	private void updateDimensions() {
		this.height = this.getHeight();
		this.width = this.getWidth();
		this.cellH = this.height / this.maze.getNumRows();
		this.cellW = this.width / this.maze.getNumCols();
	}

	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		this.updateDimensions();
		this.maze.paint(graphics, this.cellH, this.cellW);
	}

	public void setStepDelay(long step_delay) {
		this.stepDelay = step_delay;
	}

	public void startBuild() {
		Thread starter = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					MazeBoard.this.generator.start();
					MazeBoard.this.repaint();
					Thread.sleep(MazeBoard.this.stepDelay);
					while (!MazeBoard.this.generator.finished()) {
						MazeBoard.this.generator.step();
						MazeBoard.this.repaint();
						Thread.sleep(MazeBoard.this.stepDelay);
					}
				} catch (InterruptedException ex) {
					System.out.println("Interupted");
				} catch (UninitializedGeneratorException ex) {
					System.out.println("Attempted to generate without initializing");
				}
			}

		});
		starter.start();
	}

	public void setup() {
		this.generator = new BinaryTreeAlgorithm(this.numRows, this.numCols);
		switch (this.selectedAlgorithm) {
			case (RecursiveBackTracker.NAME):
				this.generator = new RecursiveBackTracker(this.numRows, this.numCols);
				break;
			case (BinaryTreeAlgorithm.NAME):
				this.generator = new BinaryTreeAlgorithm(this.numRows, this.numCols);
				break;
			case (EllerAlgorithm.NAME):
				this.generator = new EllerAlgorithm(this.numRows, this.numCols);
				break;
		}
		this.maze = this.generator.getMaze();
	}

	public void searchSetup() {
		this.searchLeft = new LeftFirstSearch(this.numRows, this.numCols, this.maze);
		this.searchRight = new RightFirstSearch(this.numRows, this.numCols, this.maze);

		this.maze = this.searchLeft.getMaze();
		this.maze = this.searchRight.getMaze();
	}

	public void searchMaze() {
		// Thread searchingLeft = new Thread(new Runnable() {

		// @Override
		// public void run() {
		// MazeBoard.this.searchLeft.start();
		// while (!MazeBoard.this.searchLeft.finished()) {
		// try {
		// MazeBoard.this.searchLeft.step();
		// } catch (UninitializedSearchException ex) {
		// System.out.println("Attempted to search without initializing");
		// }
		// }
		// }

		// });
		Thread searchingRight = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					MazeBoard.this.searchRight.start();
					MazeBoard.this.repaint();
					Thread.sleep(MazeBoard.this.stepDelay);
					while (!MazeBoard.this.searchLeft.finished()) {
						MazeBoard.this.searchRight.step();
						MazeBoard.this.repaint();
						Thread.sleep(MazeBoard.this.stepDelay);
					}
				} catch (InterruptedException ex) {
					System.out.println("Interupted");
				} catch (UninitializedSearchException ex) {
					System.out.println("Attempted to search without initializing");
				}
			}
		});
		searchingRight.start();
		// searchingLeft.start();
	}

	public void setNumRows(int numRows) {
		this.numRows = numRows;
	}

	public void setNumCols(int numCols) {
		this.numCols = numCols;
	}

	public void setAlgorithm(String selectedAlgorithm) {
		this.selectedAlgorithm = selectedAlgorithm;

	}
}
