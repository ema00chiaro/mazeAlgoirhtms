package maze;

public class UnweightedCellFactory implements CellFactory<Cell>{

	@Override
	public Cell[][] getGrid(int rows, int cols) {
		return new Cell[rows][cols];
	}

	@Override
	public Cell cellInstance(int row, int col) {
		return new Cell(row, col);
	}
}
