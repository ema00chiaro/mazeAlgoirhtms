package maze.factories;

import java.util.Random;

import maze.cells.Cell;
import maze.cells.WeightedCell;

public class WeightedCellFactory implements CellFactory{

	private int maxCellWeight;

	public WeightedCellFactory(int maxCellWeight){
		this.maxCellWeight = maxCellWeight;
	}

	@Override
	public Cell[][] createGrid(int rows, int cols) {
		return new WeightedCell[rows][cols];
	}

	@Override
	public Cell createCell(int row, int col) {
		return new WeightedCell(row, col, new Random().nextInt(maxCellWeight)+1);
	}
	
}
