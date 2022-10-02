package maze.distances;

import java.util.HashMap;
import java.util.Map;
import maze.cells.Cell;

public class Distances{

	private Cell root;
	private Map<Cell,Integer> cells;

	public Distances(Cell root) {
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

	public Distances pathTo(Cell target){
		Cell current = target;

		Distances breadcrumbs = new Distances(root);
		breadcrumbs.cells.put(current, distanceFromRoot(current));
		
		while(!current.equals(root)){
			for (Cell linked : current.getLinks()) {
				if(distanceFromRoot(linked) < distanceFromRoot(current)){ // da rivedere
					breadcrumbs.cells.put(linked, distanceFromRoot(linked));
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
