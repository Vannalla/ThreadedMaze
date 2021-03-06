
package maze;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import maze.generate.BinaryTreeAlgorithm;
import maze.generate.RecursiveBackTracker;
import maze.generate.EllerAlgorithm;
//import maze.search.LeftFirstSearch;
//import maze.search.RightFirstSearch;

public class MazeControls extends JPanel {

	/**
	 * 
	 */

	private static final String[] AVAILABLE_ALGORITHMS = { RecursiveBackTracker.NAME, BinaryTreeAlgorithm.NAME,
			EllerAlgorithm.NAME };

	private static final int MIN_NUM_ROWS = 3;
	private static final int MIN_NUM_COLS = 3;
	private static final int MAX_NUM_ROWS = 100;
	private static final int MAX_NUM_COLS = 100;
	private static final int STEP = 1;
	private static final int SPACE_BETWEEN_COMPONENTS = 20;
	private static final int SPACE_AFTER_TITLE = 5;

	public static final int INIT_NUM_ROWS = 10;
	public static final int INIT_NUM_COLS = 10;

	// Speed defines the number of movements per second
	private static final int MIN_SPEED = 1;
	private static final int MAX_SPEED = 101;
	private static final long ONE_SECOND = 1000;
	private static final Dimension SPINNER_DIMENSION = new Dimension(50, 24);
	private static final Dimension MAZE_CONTROLS_DIMENSION = new Dimension(190, 600); // y originally = 550

	private MazeBoard mazeBoard;
	private JSlider speedSlider;
	private JSpinner numColsSpinner;
	private JSpinner numRowsSpinner;

	public MazeControls(MazeBoard board) {
		super();
		Box box = Box.createVerticalBox();
		this.setPreferredSize(MAZE_CONTROLS_DIMENSION);
		this.setMaximumSize(MAZE_CONTROLS_DIMENSION);
		this.mazeBoard = board;
		this.mazeBoard.setStepDelay(MIN_SPEED);
		box.add(Box.createRigidArea(new Dimension(0, 2 * SPACE_BETWEEN_COMPONENTS)));

		// ALGORITHM SELECTOR
		JLabel algorithmsTitle = new JLabel("Generator algorithm");
		algorithmsTitle.setAlignmentX(CENTER_ALIGNMENT);
		box.add(algorithmsTitle);
		box.add(Box.createRigidArea(new Dimension(0, SPACE_AFTER_TITLE)));
		JComboBox<String> algorithmSelector = new JComboBox<String>(AVAILABLE_ALGORITHMS);
		algorithmSelector.setSelectedItem(RecursiveBackTracker.NAME);
		algorithmSelector.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String selectedAlgorithm = (String) algorithmSelector.getSelectedItem(); // drop down menu on generate
				MazeControls.this.mazeBoard.setAlgorithm(selectedAlgorithm);
			}

		});
		algorithmSelector.getActionListeners()[0].actionPerformed(null);
		;
		box.add(algorithmSelector);
		box.add(Box.createRigidArea(new Dimension(0, SPACE_BETWEEN_COMPONENTS)));

		// NUM ROWS SPINNER
		JLabel numRowsTitle = new JLabel("Number of rows");
		numRowsTitle.setAlignmentX(CENTER_ALIGNMENT);
		box.add(numRowsTitle);
		box.add(Box.createRigidArea(new Dimension(0, SPACE_AFTER_TITLE)));
		SpinnerNumberModel rowsSpinnerModel = new SpinnerNumberModel(INIT_NUM_ROWS, MIN_NUM_ROWS, MAX_NUM_ROWS, STEP);
		this.numRowsSpinner = new JSpinner(rowsSpinnerModel);
		this.numRowsSpinner.setPreferredSize(SPINNER_DIMENSION);
		this.numRowsSpinner.setMaximumSize(SPINNER_DIMENSION);
		this.numRowsSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				// This will not update the state until reset button.
				int numRows = (int) MazeControls.this.numRowsSpinner.getValue();
				MazeControls.this.mazeBoard.setNumRows(numRows);
			}
		});
		this.numRowsSpinner.getChangeListeners()[0].stateChanged(null);
		box.add(this.numRowsSpinner);
		box.add(Box.createRigidArea(new Dimension(0, SPACE_BETWEEN_COMPONENTS)));

		// NUM COLS SPINNER
		JLabel colsSpinnerTitle = new JLabel("Number of columns");
		colsSpinnerTitle.setAlignmentX(CENTER_ALIGNMENT);
		box.add(colsSpinnerTitle);
		box.add(Box.createRigidArea(new Dimension(0, SPACE_AFTER_TITLE)));
		SpinnerNumberModel colsSpinnerModel = new SpinnerNumberModel(INIT_NUM_COLS, MIN_NUM_COLS, MAX_NUM_COLS, STEP);
		this.numColsSpinner = new JSpinner(colsSpinnerModel);
		this.numColsSpinner.setPreferredSize(SPINNER_DIMENSION);
		this.numColsSpinner.setMaximumSize(SPINNER_DIMENSION);
		this.numColsSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				// This will not update the state until reset button.
				int numCols = (int) MazeControls.this.numColsSpinner.getValue();
				MazeControls.this.mazeBoard.setNumCols(numCols);
			}

		});
		this.numColsSpinner.getChangeListeners()[0].stateChanged(null);
		box.add(this.numColsSpinner);
		box.add(Box.createRigidArea(new Dimension(0, SPACE_BETWEEN_COMPONENTS)));

		// SPEED SLIDER
		JLabel speedSliderTitle = new JLabel("Speed");
		speedSliderTitle.setAlignmentX(CENTER_ALIGNMENT);
		box.add(speedSliderTitle);
		box.add(Box.createRigidArea(new Dimension(0, SPACE_AFTER_TITLE)));
		this.speedSlider = new JSlider(JSlider.VERTICAL, MazeControls.MIN_SPEED, MazeControls.MAX_SPEED,
				MazeControls.MIN_SPEED);
		this.speedSlider.setPaintTicks(true);
		this.speedSlider.setMajorTickSpacing(10); // originally 5
		this.speedSlider.setMinorTickSpacing(5); // originally 5
		this.speedSlider.setPaintLabels(true);
		this.speedSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				// This will immediately update the speed.
				int speed = MazeControls.this.speedSlider.getValue();
				MazeControls.this.mazeBoard.setStepDelay(MazeControls.ONE_SECOND / speed);
			}

		});
		this.speedSlider.getChangeListeners()[0].stateChanged(null);
		box.add(this.speedSlider);
		box.add(Box.createRigidArea(new Dimension(0, SPACE_BETWEEN_COMPONENTS)));

		// BEGIN BUTTON
		JButton generateBTN = new JButton("Generate");
		generateBTN.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				MazeControls.this.mazeBoard.setup();
				MazeControls.this.mazeBoard.startBuild();
			}

		});
		generateBTN.setAlignmentX(CENTER_ALIGNMENT);
		box.add(generateBTN);
		this.add(box);
		box.add(Box.createRigidArea(new Dimension(0, SPACE_BETWEEN_COMPONENTS)));

		// SOLVE THE MAZE BUTTON

		JButton solveBTN = new JButton("Solve");
		solveBTN.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				MazeControls.this.mazeBoard.searchSetup();
				MazeControls.this.mazeBoard.searchMaze();
			}

		});
		solveBTN.setAlignmentX(CENTER_ALIGNMENT);
		box.add(solveBTN);
		this.add(box);
		box.add(Box.createRigidArea(new Dimension(0, SPACE_BETWEEN_COMPONENTS)));
	}

}
