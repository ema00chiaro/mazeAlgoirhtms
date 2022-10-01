package maze;

import maze.cells.Cell;
import maze.distances.Distances;
import maze.distances.WeightedDistances;
import maze.factories.StandardCellFactory;
import maze.generationAlgorithms.*;

class tests{
	public static void main (String[] args) {
		int rows = 7;
		int cols = 7;

		//Grid grid = new Grid(rows, cols, new WeightedCellFactory(20));
		Grid grid = new Grid(rows, cols, new StandardCellFactory());
		PrimSimplified.buildMaze(grid);
		grid.displayGrid();
		grid.braid();
		Cell start = grid.getCellAt(0, 0);
		Cell target = grid.getCellAt(rows-1, cols-1);
		Distances d = new WeightedDistances(start);
		d.distances();

		grid.displayGrid();
		//grid.displayPathBetween(start, target);
		grid.displayDistances(start, d);
		//((WeightedCell)grid.getCellAt(1,0)).setWeight(100);
		grid.displayDistanceBetween(start, target, d);
		grid.displayPathBetween(start, target, d);

	}
}