package maze;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class WeightedCell extends Cell{

	private int weight;

	public WeightedCell(int row, int col) {
		super(row, col);
		weight = 1;
	}

	public WeightedCell(int row, int col, int weight) {
		super(row, col);
		this.weight = weight;
	}

	@Override
	public Distances distances(){
		Distances weights = new Distances(this);
		Set<Cell> pending = new HashSet<>();
		pending.add(this);

		while (!pending.isEmpty()){
			Cell cell = Collections.min(pending, (c1,c2) -> c1.getWeight()-c2.getWeight());
			pending.remove(cell);

			for (Cell linked : cell.getLinks()) {
				int totalWeight = weights.distanceFromRoot(cell) + linked.getWeight();
				if (!weights.contains(linked) || totalWeight < weights.distanceFromRoot(linked)){
					pending.add(linked);
					weights.setCellDistance(linked, totalWeight);
				}
			}
		}
		return weights;
	}

	@Override
	public int getWeight(){
		return weight;
	}

}
