package maze.generationAlgorithms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

import maze.Cell;
import maze.Grid;

public class Eller {

	static class RowState{
		Map<Integer,Set<Cell>> colToSet;
		Map<Cell,Integer> cellToSet;

		public RowState() {
			colToSet = new HashMap<>();
			cellToSet = new HashMap<>();
		}
	}

	public static void buildMaze(Grid grid){
		
	}


}
