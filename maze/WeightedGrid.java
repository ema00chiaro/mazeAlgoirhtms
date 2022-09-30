package maze;

import java.util.Random;

public class WeightedGrid extends Grid{

	public WeightedGrid(int rows, int cols) {
		super(rows, cols);
	}

	@Override
	protected void prepareGrid(){
		for (int i = 0; i < getRows(); i++){
			for (int j = 0; j < getCols(); j++){
				getGrid()[i][j] = new WeightedCell(i, j, new Random().nextInt(20)+1);
			}
		}
	}
	
}
