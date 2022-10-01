package maze.factories;

import maze.cells.Cell;

public interface CellFactory {
	public Cell[][] createGrid(int rows, int cols);
	public Cell createCell(int row, int col);
}
