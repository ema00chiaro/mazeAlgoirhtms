package maze.generationAlgorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import maze.Grid;
import maze.Utility;
import maze.cells.Cell;

public class PrimSimplified {

	public static void buildMaze(Grid grid){
		//Cell start = grid.getRandomCell();
		Cell start = grid.getGrid()[grid.getRows()/2][grid.getCols()/2];

		List<Cell> active = new ArrayList<>();
		active.add(start);
		while(!active.isEmpty()){
			//prendo un elemento casuale appartenente alla active list
			int index = Utility.getRandomNumber(active.size());
			Cell cell = active.get(index);

			Set<Cell> neighboursPool = grid.getNeighbours(cell);
			neighboursPool.removeIf( c -> c.hasLinks());

			if (!neighboursPool.isEmpty()){
				Cell neighbour = Utility.getRandomElement(neighboursPool);
				cell.link(neighbour);
				active.add(neighbour);
			}else{
				active.remove(index);
			}
		}
	}
}
