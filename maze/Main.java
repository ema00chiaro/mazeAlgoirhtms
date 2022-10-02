package maze;

import maze.cells.Cell;
import maze.cells.WeightedCell;
import maze.distances.Distances;
import maze.distances.WeightedDistances;
import maze.generationAlgorithms.*;

class tests{
	public static void main (String[] args) {
		int rows = 20;
		int cols = 40;

		//Grid grid = new Grid(rows, cols, new WeightedCellFactory(20));
		Grid grid = new WeightedGrid(rows, cols);
		grid.setDistances(new WeightedDistances());
		BinaryTree.buildMaze(grid);
		//grid.displayGrid();
		//grid.braid();
		Cell start = grid.getCellAt(0, 0);
		Cell target = grid.getCellAt(rows-1, cols-1);

		grid.displayGrid();
		// grid.displayDistances(start);
		grid.displayDistances(start);
		grid.displayDistanceBetween(start, target);
		grid.displayPathBetween(start, target);
		//((WeightedCell)grid.getCellAt(0,1)).setWeight(1000);
		//grid.displayDistances(start);
		//grid.displayPathBetween(start, target);

	}
}