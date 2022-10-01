package maze;

public interface CellFactory {
	public Cell[][] createGrid(int rows, int cols);
	public Cell createCell(int row, int col);
}
