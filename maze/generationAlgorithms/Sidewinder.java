package maze.generationAlgorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import maze.Grid;
import maze.cells.Cell;

public class Sidewinder {

	public static void buildMaze(Grid grid){
		for (Cell[] cells : grid.getGrid()) {
			List<Cell> run = new ArrayList<>();

			for (Cell cell : cells) {
				run.add(cell);
				boolean eastern_limit = (Objects.isNull(grid.getEast(cell)));
				boolean nothern_limit = (Objects.isNull(grid.getNorth(cell)));

				boolean carve_north = eastern_limit || (!nothern_limit && (new Random().nextInt(3) == 0));

				if (carve_north){
					Cell randomCell = run.get(new Random().nextInt(run.size()));
					if (!nothern_limit) // nel caso della cella in alto a dx si ha carve_north = true, questo controllo è necessario
						randomCell.link(grid.getNorth(randomCell));
				}else{
					cell.link(grid.getEast(cell));
				}
				run.clear();
			}
		}
	}
}
