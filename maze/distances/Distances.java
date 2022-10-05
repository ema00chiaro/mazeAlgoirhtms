package maze.distances;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import maze.cells.Cell;

public class Distances{

	protected Cell root; 
	protected Map<Cell,Integer> cells;

	public Distances(){

	}

	protected Distances(Cell root){
		this.root = root;
		cells = new HashMap<>();
		cells.put(root, 0);
	}

	// public static Distances standardDistances(Cell start){
	// 	Distances distances = new Distances(start);
	// 	List<Cell> frontier = new ArrayList<>();
	// 	frontier.add(start);

	// 	while (!frontier.isEmpty()){
	// 		List<Cell> frontier_new = new ArrayList<>(); 
	// 		for (Cell cell : frontier) {
	// 			for (Cell linked : cell.getLinks()) {
	// 				if (!distances.contains(linked)){
	// 					distances.setCellDistance(linked, distances.distanceFromRoot(cell) + 1);
	// 					frontier_new.add(linked);
	// 				}
	// 			}
	// 		}
	// 		frontier = frontier_new;
	// 	}
	// 	return distances;
	// }

	// public static Distances standardDistances(Cell start){
	// 	Distances distances = new Distances(start);
	// 	List<Cell> frontier = new ArrayList<>();
	// 	frontier.add(start);

	// 	while (!frontier.isEmpty()){
	// 		List<Cell> frontier_new = new ArrayList<>(); 
	// 		for (Cell cell : frontier) {
	// 			for (Cell linked : cell.getLinks()) {
	// 				if (!distances.contains(linked)){
	// 					distances.setCellDistance(linked, distances.distanceFromRoot(cell) + 1);
	// 					frontier_new.add(linked);
	// 				}
	// 			}
	// 		}
	// 		frontier = frontier_new;
	// 	}
	// 	return distances;
	// }

	// public static Distances weightedDistances(Cell start){
	// 	Distances distances = new Distances(start);
	// 	Queue<Cell> pending = new PriorityQueue<>((c1,c2) -> { //FIXME
	// 			int w1 = 1, w2 = 1;
	// 			if (c1 instanceof WeightedCell)
	// 				w1 = ((WeightedCell)c1).getWeight();
	// 			if (c2 instanceof WeightedCell)
	// 				w2 = ((WeightedCell)c2).getWeight();
	// 			return w1-w2;
	// 		}
	// 	);
	// 	pending.add(start);
	// 	while (!pending.isEmpty()){
	// 		Cell cell = pending.poll();

	// 		for (Cell linked : cell.getLinks()) {
	// 			int weight = 1;
	// 			if(linked instanceof WeightedCell){
	// 				weight = ((WeightedCell)linked).getWeight();
	// 			}
	// 			int totalWeight = distances.distanceFromRoot(cell) + weight;
	// 			if (!distances.contains(linked) || totalWeight < distances.distanceFromRoot(linked)){
	// 				pending.add(linked);
	// 				distances.setCellDistance(linked, totalWeight);
	// 			}
	// 		}
	// 	}
	// 	return distances;
	// }

	public Distances distancesFrom(Cell start){
		Distances distances = new Distances(start);
		List<Cell> frontier = new ArrayList<>();
		frontier.add(start);

		while (!frontier.isEmpty()){
			List<Cell> frontier_new = new ArrayList<>(); 
			for (Cell cell : frontier) {
				for (Cell linked : cell.getLinks()) {
					if (!distances.contains(linked)){
						distances.setCellDistance(linked, distances.distanceFromRoot(cell) + 1);
						frontier_new.add(linked);
					}
				}
			}
			frontier = frontier_new;
		}
		return distances;
	}

	public Distances pathTo(Cell target){
		Cell current = target;
		Distances breadcrumbs = new Distances(root);
		breadcrumbs.setCellDistance(current, distanceFromRoot(current));
		
		while(!current.equals(root)){
			for (Cell linked : current.getLinks()) {
				if(distanceFromRoot(linked) < distanceFromRoot(current)){ // da rivedere
					breadcrumbs.setCellDistance(linked, distanceFromRoot(linked));
					current = linked;
					break;
				}
			}
		}
		return breadcrumbs;
	}

	public int distanceFromRoot(Cell cell){
		return cells.get(cell);
	}

	public boolean contains(Cell cell){
		return cells.containsKey(cell);
	}

	public void setCellDistance(Cell cell, int newDistance){
		cells.put(cell, newDistance);
	}
}
