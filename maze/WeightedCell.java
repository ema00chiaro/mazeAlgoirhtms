package maze;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class WeightedCell extends Cell implements Comparable<Cell>{

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
		Set<WeightedCell> pending = new HashSet<>();
		pending.add(this);

		while (!pending.isEmpty()){
			Cell cell = Collections.min(pending);
			pending.remove(cell);

			for (Cell c : cell.getLinks()) {
				WeightedCell linked = (WeightedCell)c; //FIXME
				int totalWeight = weights.distanceFromRoot(cell) + linked.getWeight();
				if (!weights.contains(linked) || totalWeight < weights.distanceFromRoot(linked)){
					pending.add(linked);
					weights.setCellDistance(linked, totalWeight);
				}
			}
		}
		return weights;
	}

	public int getWeight(){
		return weight;
	}

	public void setWeight(int weight){
		this.weight = weight;
	}

	@Override
	public int compareTo(Cell o) {
		if(o instanceof WeightedCell){
			return getWeight() - ((WeightedCell)o).getWeight();
		}else{
			return getWeight() - 1;
		}
	}

}
