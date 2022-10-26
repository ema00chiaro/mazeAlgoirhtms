package maze.factories;

import java.util.Random;

import maze.cells.Cell;

public class WeightedCellFactory implements CellFactory{

	private int maxCellWeight;

	public WeightedCellFactory(int maxCellWeight){
		this.maxCellWeight = maxCellWeight;
	}

	@Override
	public Cell[][] createGrid(int rows, int cols) {
		return new Cell[rows][cols];
	}

	@Override
	public Cell createCell(int row, int col) {
		return new Cell(row, col, new Random().nextInt(maxCellWeight)+1);
	}
	
}
