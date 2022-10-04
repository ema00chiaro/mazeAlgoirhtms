package maze.generationAlgorithms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import maze.Grid;
import maze.Utility;
import maze.cells.Cell;

public class GrowingTree {
	
	public static void buildMaze(Grid grid, Function<List<Cell>,Cell> fun){
		List<Cell> pool = new ArrayList<>();
		for (Cell cell : grid) {
			pool.add(cell);
		}

		Cell start = Utility.getRandomElement(pool);

		Set<Cell> active = new HashSet<>();
		active.add(start);
		while(!active.isEmpty()){
			Cell cell = fun.apply(active.stream().toList());

			List<Cell> neighboursPool = cell.getNeighbours();
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
