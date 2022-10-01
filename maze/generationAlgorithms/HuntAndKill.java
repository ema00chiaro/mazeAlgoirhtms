package maze.generationAlgorithms;

import java.util.List;
import java.util.Objects;

import maze.Grid;
import maze.Utility;
import maze.cells.Cell;

public class HuntAndKill {
	public static void buildMaze(Grid grid){
		Cell current = grid.randomCell();

		while (!Objects.isNull(current)){
			List<Cell> unvisitedNeighbours = current.getNeighbours().stream().filter( c -> !c.hasLinks()).toList();

			Cell neighbour;
			if(!unvisitedNeighbours.isEmpty()){
				neighbour = Utility.getRandomElement(unvisitedNeighbours);

				current.link(neighbour);
				current = neighbour;
			}else{
				current = null;
				for (Cell cell : grid) {
					List<Cell> visitedNeighbours = cell.getNeighbours().stream().filter(c -> c.hasLinks()).toList();
					if (!cell.hasLinks() && !visitedNeighbours.isEmpty()){
						current = cell;
						neighbour = Utility.getRandomElement(visitedNeighbours);
						current.link(neighbour);
						break;
					}
				}
			}
		}

	}
}
