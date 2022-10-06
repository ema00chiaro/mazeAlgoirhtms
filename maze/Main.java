package maze;

import maze.cells.Cell;
import maze.cells.WeightedCell;
import maze.distances.Distances;
import maze.distances.WeightedDistances;
import maze.factories.StandardCellFactory;
import maze.factories.WeightedCellFactory;
import maze.generationAlgorithms.*;

class tests{
	public static void main (String[] args) {
		int rows = 5;
		int cols = 5;

		//Grid grid = new Grid(rows, cols, new WeightedCellFactory(20));
		Grid grid = new Grid(rows, cols, new WeightedCellFactory(20));
		//grid.setDistances(new WeightedDistances());
		HuntAndKill.buildMaze(grid);
		//GrowingTree.buildMaze(grid, list -> Utility.getRandomElement(list));
		//grid.displayGrid();
		//grid.braid();
		Cell start = grid.getCellAt(0, 0);
		Cell target = grid.getCellAt(rows-1, cols-1);

		grid.displayDistances(new Distances().distancesFrom(start).pathTo(target));
		grid.displayColoredDistances(new Distances().aStarSearch(grid,start, target, (s,t) -> Math.sqrt(Math.pow(s.getRow() - t.getRow(),2) + Math.pow(s.getCol() - t.getCol(), 2))));
		// grid.displayGrid();
		// // grid.displayDistances(start);
		// grid.displayDistances(start);
		// grid.displayDistanceBetween(start, target);
		// grid.displayPathBetween(start, target);
		// //grid.getCellAt(0,1).setWeight(1000);
		// //grid.displayDistances(start);
		// //grid.displayPathBetween(start, target);
	}
}