package maze.generationAlgorithms;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import maze.Grid;
import maze.Utility;
import maze.cells.Cell;

public class HuntAndKill {
	public static void buildMaze(Grid grid){
		Cell current = grid.getRandomCell();

		while (!Objects.isNull(current)){
			Set<Cell> unvisitedNeighbours = grid.getNeighbours(current).stream().filter( c -> !c.hasLinks()).collect(Collectors.toSet());

			Cell neighbour;
			if(!unvisitedNeighbours.isEmpty()){
				neighbour = Utility.getRandomElement(unvisitedNeighbours);

				current.link(neighbour);
				current = neighbour;
			}else{
				current = null;
				for (Cell cell : grid) {
					Set<Cell> visitedNeighbours = grid.getNeighbours(cell).stream().filter(c -> c.hasLinks()).collect(Collectors.toSet());
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
