package maze;

public class UnweightedCellFactory implements CellFactory{

	@Override
	public Cell[][] createGrid(int rows, int cols) {
		return new Cell[rows][cols];
	}

	@Override
	public Cell createCell(int row, int col) {
		return new Cell(row, col);
	}
}
