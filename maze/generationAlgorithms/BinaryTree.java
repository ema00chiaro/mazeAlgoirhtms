package maze.generationAlgorithms;

import java.util.HashSet;
import java.util.Set;

import maze.Grid;
import maze.Utility;
import maze.cells.Cell;

public class BinaryTree {
	
	public static void buildMaze(Grid grid){
		for (Cell cell : grid) {
			// List<Cell> neighbours = new ArrayList<>();
			// if (!Objects.isNull(cell.getNorth()))
			// 	neighbours.add(cell.getNorth());
			// if (!Objects.isNull(cell.getEast()))
			// 	neighbours.add(cell.getEast());

			Set<Cell> neighbours = new HashSet<>();
			neighbours.add(grid.getNorth(cell));
			neighbours.add(grid.getEast(cell));
			neighbours.remove(null);
			
			if (!neighbours.isEmpty()){
				cell.link(Utility.getRandomElement(neighbours));
			}
		}
	}
}
