package maze;

import maze.generationAlgorithms.*;

class tests{
	public static void main (String[] args) {
		int rows = 7;
		int cols = 7;
		// Grid grid = new Grid(rows,cols);
		// // Cell c = grid.check_and_getCell(0,2);
		// Sidewinder.buildMaze(grid);
		// grid.displayGrid();
		//Grid grid = new Grid(rows, cols);
		//Grid grid = new Grid(rows, cols, new WeightedCellFactory(20));
		Grid grid = new Grid(rows, cols, new WeightedCellFactory(20));
		PrimSimplified.buildMaze(grid);
		//grid.displayGrid();
		grid.braid();
		Cell start = grid.getCellAt(0, 0);
		Cell target = grid.getCellAt(rows-1, cols-1);

		grid.displayGrid();
		grid.displayPathBetween(start, target);
		//grid.displayDistances(start);
		//grid.displayDistanceBetween(start, target);
		((WeightedCell)grid.getGrid()[1][0]).weight = 100;
		grid.displayPathBetween(start, target);

	}
}