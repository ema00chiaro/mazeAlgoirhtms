package maze;

import maze.cells.Cell;
import maze.cells.WeightedCell;
import maze.distances.Distances;
import maze.factories.StandardCellFactory;
import maze.factories.WeightedCellFactory;
import maze.generationAlgorithms.*;

class tests{
	public static void main (String[] args) {
		int rows = 10;
		int cols = 10;

		//Grid grid = new Grid(rows, cols, new WeightedCellFactory(20));
		//Grid grid = new Grid(rows, cols, new WeightedCellFactory(20));
		Grid grid = new Grid(rows, cols, new WeightedCellFactory(10));
		//grid.setDistances(new WeightedDistances());
		HuntAndKill.buildMaze(grid);
		//GrowingTree.buildMaze(grid, list -> Utility.getRandomElement(list));
		//grid.displayGrid();
		grid.braid(1);
		Cell start = grid.getCellAt(0, 0);
		Cell target = grid.getCellAt(rows-1, cols-1);

		grid.displayDistances(Distances.DijkstraSimplified(start));
		grid.displayDistances(Distances.DijkstraSimplified(start).pathTo(target));
		grid.displayDistances(Distances.DijkstraWeights(start));
		grid.displayDistances(Distances.DijkstraWeights(start).pathTo(target));
		grid.displayColoredDistances(Distances.deadEndFilling(grid, start, target));
		grid.displayColoredDistances(Distances.DijkstraWeights(start).pathTo(target));
		grid.getCellAt(0,1).setWeight(1000);
		grid.displayColoredDistances(Distances.DijkstraWeights(start).pathTo(target));
	}
}