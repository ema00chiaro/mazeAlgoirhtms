package maze.distances;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

import maze.Grid;
import maze.cells.Cell;

public class Distances{

	protected Cell root; 
	protected Map<Cell,Double> cells;

	public Distances(){

	}

	protected Distances(Cell root){
		this.root = root;
		cells = new HashMap<>();
		cells.put(root, 0.0);
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

	public Distances aStarSearch(Grid g,Cell start, Cell target, BiFunction<Cell,Cell,Double> heuristic){
		class RouteElement implements Comparable<RouteElement>{

			Cell cell;
			double f = Double.MAX_VALUE;
			double g = Double.MAX_VALUE;

			public RouteElement(Cell cell) {
				this.cell = cell;
			}

			@Override
			public int compareTo(RouteElement o) {
				return Double.compare(f, o.f);
			}

			public double calculateHeuristic(RouteElement n){
				return heuristic.apply(cell, n.cell);
			}
		}
		Distances distances = new Distances(start);

		RouteElement from = new RouteElement(start);
		RouteElement to = new RouteElement(target);
		
		Queue<RouteElement> closed = new PriorityQueue<>();
		Queue<RouteElement> open = new PriorityQueue<>();

		from.f = from.g + from.calculateHeuristic(to);
		open.add(from);

		while(!open.isEmpty()){
			RouteElement element = open.peek();
			if(element == to){
				break;
			}

			for (Cell linked : element.cell.getLinks()) {
				RouteElement linkedElement = new RouteElement(linked);
				double totalWeight = element.g + element.cell.getWeightOfLink(linked);

				if(!open.contains(linkedElement) && !closed.contains(linkedElement)){
					distances.setCellDistance(linkedElement.cell, totalWeight);
					linkedElement.g = totalWeight;
					linkedElement.f = linkedElement.g + linkedElement.calculateHeuristic(to);
					open.add(linkedElement);

					if(closed.contains(linkedElement)){
						closed.remove(linkedElement);
						open.add(linkedElement);
					}
				}
				g.displayDistances(distances);
			}
			open.remove(element);
			closed.add(element);
		}

		return distances;
	}

	public double distanceFromRoot(Cell cell){
		return cells.get(cell);
	}

	public boolean contains(Cell cell){
		return cells.containsKey(cell);
	}

	public void setCellDistance(Cell cell, double newDistance){
		cells.put(cell, newDistance);
	}
}
