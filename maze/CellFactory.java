package maze;

public interface CellFactory<T extends Cell> {
	public T[][] getGrid(int rows, int cols);
	public T cellInstance(int row, int col);
}
