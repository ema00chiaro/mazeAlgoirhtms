package maze.distances;

import java.util.PriorityQueue;
import java.util.Queue;

import maze.cells.Cell;
import maze.cells.WeightedCell;

public class WeightedDistances extends Distances {

	public WeightedDistances(Cell root) {
		super(root);
	}
	
	@Override
	public void distances(){

		Queue<Cell> pending = new PriorityQueue<>((c1,c2) -> { //FIXME
				int w1 = 1, w2 = 1;
				if (c1 instanceof WeightedCell)
					w1 = ((WeightedCell)c1).getWeight();
				if (c2 instanceof WeightedCell)
					w2 = ((WeightedCell)c2).getWeight();
				return w1-w2;
			}
		);
		pending.add(root);
		while (!pending.isEmpty()){
			Cell cell = pending.poll();

			for (Cell linked : cell.getLinks()) {
				int weight = 1;
				if(linked instanceof WeightedCell){
					weight = ((WeightedCell)linked).getWeight();
				}
				int totalWeight = distanceFromRoot(cell) + weight;
				if (!contains(linked) || totalWeight < distanceFromRoot(linked)){
					pending.add(linked);
					setCellDistance(linked, totalWeight);
				}
			}
		}
	}
}
