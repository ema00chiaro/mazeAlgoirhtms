package maze.generationAlgorithms;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import maze.Grid;
import maze.Utility;
import maze.cells.Cell;

public class Wilson{

	public static void buildMaze(Grid grid){
		List<Cell> unvisited = new ArrayList<>();
		for (Cell cell : grid) {
			unvisited.add(cell);
		}

		Cell first = Utility.getRandomElement(unvisited);
		unvisited.remove(first);

		while (!unvisited.isEmpty()){
			Cell cell = Utility.getRandomElement(unvisited);
			List<Cell> path = new LinkedList<>();
			path.add(cell);

			while (unvisited.contains(cell)){
				cell = grid.getRandomNeighbour(cell);
				int position = path.indexOf(cell);

				if (position >= 0){
					List<Cell> newPath = new LinkedList<>();
					for(int i = 0; i <= position; i++){
						newPath.add(path.get(i));
					}
					path = newPath;
				}else
					path.add(cell);
			}

			for (int i = 0; i < path.size()-1; i++){
				path.get(i).link(path.get(i+1));
				unvisited.remove(path.get(i));
			}
		}

	}
}
