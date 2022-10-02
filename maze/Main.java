package maze;

import maze.cells.Cell;
import maze.cells.WeightedCell;
import maze.factories.*;
import maze.generationAlgorithms.*;

class tests{
	public static void main (String[] args) {
		int rows = 4;
		int cols = 4;

		//Grid grid = new Grid(rows, cols, new WeightedCellFactory(20));
		Grid grid = new Grid(rows, cols, new WeightedCellFactory(20));
		PrimSimplified.buildMaze(grid);
		grid.displayGrid();
		grid.braid();
		Cell start = grid.getCellAt(0, 0);
		Cell target = grid.getCellAt(rows-1, cols-1);

		grid.displayGrid();
		grid.displayDistances(start);
		grid.displayDistanceBetween(start, target);
		grid.displayPathBetween(start, target);
		((WeightedCell)grid.getCellAt(1,0)).setWeight(1000);
		grid.displayDistances(start);
		grid.displayPathBetween(start, target);

	}
}