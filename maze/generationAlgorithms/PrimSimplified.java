package maze.generationAlgorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import maze.Grid;
import maze.Utility;
import maze.cells.Cell;

public class PrimSimplified {

	public static void buildMaze(Grid grid){

		Cell start = grid.getRandomCell();

		List<Cell> active = new ArrayList<>();
		active.add(start);
		while(!active.isEmpty()){
			Cell cell = Utility.getRandomElement(active);

			Set<Cell> neighboursPool = grid.getNeighbours(cell);
			neighboursPool.removeIf( c -> c.hasLinks());

			if (!neighboursPool.isEmpty()){
				Cell neighbour = Utility.getRandomElement(neighboursPool);
				cell.link(neighbour);
				active.add(neighbour);
			}else{
				active.remove(cell);
			}
		}
		
	}
}
