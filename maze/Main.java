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
		Grid grid = new Grid(rows, cols, new UnweightedCellFactory());
		PrimSimplified.buildMaze(grid);
		grid.displayGrid();
		grid.braid();
		Cell start = grid.getCellAt(0, 0);
		Cell target = grid.getCellAt(rows-1, cols-1);
		System.out.println("ciao");
		grid.displayGrid();
		grid.displayDistances(start);
		grid.displayDistanceBetween(start, target);
		grid.displayPathBetween(start, target);

	}
}