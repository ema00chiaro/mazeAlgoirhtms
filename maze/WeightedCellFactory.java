package maze;

import java.util.Random;

public class WeightedCellFactory implements CellFactory<WeightedCell>{

	private int maxCellWeight;

	public WeightedCellFactory(int maxCellWeight){
		this.maxCellWeight = maxCellWeight;
	}

	@Override
	public WeightedCell[][] getGrid(int rows, int cols) {
		return new WeightedCell[rows][cols];
	}

	@Override
	public WeightedCell cellInstance(int row, int col) {
		return new WeightedCell(row, col, new Random().nextInt(maxCellWeight)+1);
	}
	
}
