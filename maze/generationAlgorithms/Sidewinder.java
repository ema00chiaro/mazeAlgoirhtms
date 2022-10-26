package maze.generationAlgorithms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import maze.Grid;
import maze.Utility;
import maze.cells.Cell;

public class Sidewinder {

	public static void buildMaze(Grid grid){
		Iterator<Cell[]> rowsIterator = grid.rowsIterator();
		while (rowsIterator.hasNext()) {
			Cell[] row = rowsIterator.next();
			List<Cell> run = new ArrayList<>();

			for (Cell cell : row) {
				run.add(cell);
				boolean eastern_limit = (Objects.isNull(grid.getEast(cell)));
				boolean nothern_limit = (Objects.isNull(grid.getNorth(cell)));

				boolean carve_north = eastern_limit || (!nothern_limit && Utility.randomBoolean(3));

				if (carve_north){
					Cell randomCell = Utility.getRandomElement(run);
					if (!nothern_limit){ // caso in cui ci si trovi nella cella superiore destra
						randomCell.link(grid.getNorth(randomCell));
					}
					run.clear();
				}else{
					cell.link(grid.getEast(cell));
				}
			}
		}
	}
}
