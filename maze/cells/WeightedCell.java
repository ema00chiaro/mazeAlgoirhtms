package maze.cells;

public class WeightedCell extends Cell{

	public WeightedCell(int row, int col) {
		super(row, col, 1);
	}

	public WeightedCell(int row, int col, int weight) {
		super(row, col, weight);
	}

	@Override
	public int getWeight() {
		// TODO Auto-generated method stub
		return super.getWeight();
	}

	
}
