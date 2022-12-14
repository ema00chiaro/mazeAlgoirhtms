package maze.generationAlgorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import maze.Grid;
import maze.Utility;
import maze.cells.Cell;

public class GrowingTree {
	
	public static void buildMaze(Grid grid, Function<List<Cell>,Cell> fun){
		Cell start = grid.getRandomCell();

		List<Cell> active = new ArrayList<>();
		active.add(start);
		while(!active.isEmpty()){
			Cell cell = fun.apply(active);

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
