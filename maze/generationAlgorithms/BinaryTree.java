package maze.generationAlgorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import maze.Cell;
import maze.Grid;

public class BinaryTree {
	
	public static void buildMaze(Grid grid){
		for (Cell cell : grid) {
			List<Cell> neighbours = new ArrayList<>();
			if (!Objects.isNull(cell.getNorth()))
				neighbours.add(cell.getNorth());
			if (!Objects.isNull(cell.getEast()))
				neighbours.add(cell.getEast());

			if (!neighbours.isEmpty()){
				Random rand = new Random();
				int r = rand.nextInt(neighbours.size());
				cell.link(neighbours.get(r));
			}
		}
	}
}
