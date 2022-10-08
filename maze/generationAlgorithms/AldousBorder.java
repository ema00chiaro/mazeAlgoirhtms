package maze.generationAlgorithms;

import maze.Grid;
import maze.cells.Cell;

public class AldousBorder {

	public static void buildMaze(Grid grid){
		Cell cell = grid.getRandomCell();
		int unvisited = grid.size()-1;

		while (unvisited > 0){
			Cell neighbour = grid.getRandomNeighbour(cell);

			if(!neighbour.hasLinks()){
				cell.link(neighbour);
				unvisited -= 1;
			}
			cell = neighbour;
		}

	}
}
