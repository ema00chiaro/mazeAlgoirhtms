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
		Map<Integer, Set<Cell>> cellsInSet;
		Map<Integer, Integer> setForCell;
		int nextSet;

		public RowState(int nextSet) {
			cellsInSet = new HashMap<>();
			setForCell = new HashMap<>();
			this.nextSet = nextSet;
		}

		public RowState(){
			this(0);
		}

		public void record(int set, Cell cell){
			setForCell.replace(cell.getCol(), set);

			if(Objects.isNull(cellsInSet.get(set))){
				cellsInSet.put(set, new HashSet<>());
			}
			cellsInSet.get(set).add(cell);
		}

		public Integer setFor(Cell cell){
			if(Objects.isNull(setForCell.get(cell.getCol()))){
				record(nextSet, cell);
				nextSet++;
			}
			return setForCell.get(cell.getCol());
		}

		public void merge(int winner,int loser){
			for (Cell cell : cellsInSet.get(loser)) {
				setForCell.replace(cell.getCol(),winner);
			}
			cellsInSet.remove(loser);
		}
		
		public RowState next(){
			return new RowState(nextSet);
		}

		public 
	}

	public static void buildMaze(Grid grid){
		
	}


}
