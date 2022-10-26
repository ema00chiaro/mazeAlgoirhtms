package maze.generationAlgorithms;

import java.util.HashSet;
import java.util.Set;

import maze.Grid;
import maze.Utility;
import maze.cells.Cell;

public class BinaryTree {
	
	public static void buildMaze(Grid grid){
		for (Cell cell : grid) {

			Set<Cell> neighbours = new HashSet<>();
			neighbours.add(grid.getNorth(cell));
			neighbours.add(grid.getEast(cell));
			neighbours.remove(null); // nel caso alcune celle non abbiano tutti i vicini necessari
			
			if (!neighbours.isEmpty()){
				cell.link(Utility.getRandomElement(neighbours));
			}
		}
	}
}
