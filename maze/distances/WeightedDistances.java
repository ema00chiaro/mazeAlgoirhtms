package maze.distances;

import java.util.PriorityQueue;
import java.util.Queue;

import maze.cells.Cell;
import maze.cells.WeightedCell;

public class WeightedDistances extends Distances {
	
	@Override
	public Distances distancesFrom(Cell start){
		Distances distances = new Distances(start);
		Queue<Cell> pending = new PriorityQueue<>((c1,c2) -> c1.getWeight()-c2.getWeight()	);
		pending.add(start);
		while (!pending.isEmpty()){
			Cell cell = pending.poll();

			for (Cell linked : cell.getLinks()) {
				int weight = 1;
				if(linked instanceof WeightedCell){
					weight = ((WeightedCell)linked).getWeight();
				}
				double totalWeight = distances.distanceFromRoot(cell) + weight;
				if (!distances.contains(linked) || totalWeight < distances.distanceFromRoot(linked)){
					pending.add(linked);
					distances.setCellDistance(linked, totalWeight);
				}
			}
		}
		return distances;
	}
}
