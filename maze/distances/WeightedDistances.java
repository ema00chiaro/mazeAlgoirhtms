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

	//questa roba era dentro la classe cella
	// public Distances distances(Cell start){
	// 	StandardDistances weights = new StandardDistances(start);
	// 	Queue<WeightedCell> pending = new PriorityQueue<>();
	// 	pending.add(start);

	// 	while (!pending.isEmpty()){
	// 		Cell cell = pending.poll();
	// 		for (Cell c : cell.getLinks()) {
	// 			WeightedCell linked;/* = (WeightedCell)c; //FIXME */
	// 			if(c instanceof WeightedCell){
	// 				linked = (WeightedCell)c;
	// 			}else{
	// 				linked = new WeightedCell(c);
	// 			}
	// 			int totalWeight = weights.distanceFromRoot(cell) + linked.getWeight();
	// 			if (!weights.contains(linked) || totalWeight < weights.distanceFromRoot(linked)){
	// 				pending.add(linked);
	// 				weights.setCellDistance(linked, totalWeight);
	// 			}
	// 		}
	// 	}
	// 	return weights;
	// }
}
