package maze.distances;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.BiFunction;

import maze.Grid;
import maze.Utility;
import maze.cells.Cell;
import maze.cells.Link;

public class Distances{

	protected Cell root; 
	public Map<Cell,Double> cells;

	private Distances(Cell root){
		this.root = root;
		cells = new HashMap<>();
		cells.put(root, 0.0);
	}

	public static Distances DijkstraSimplified(Cell start){
		Distances distances = new Distances(start);
		List<Cell> frontier = new ArrayList<>();
		frontier.add(start);

		while (!frontier.isEmpty()){
			List<Cell> frontier_new = new ArrayList<>(); 
			for (Cell cell : frontier) {
				for (Cell linked : cell.getLinkedCells()) {
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

	
	//TODO: remove
	public static Distances SPFA(Cell start){
		Distances distances = new Distances(start);
		Queue<Cell> pending = new LinkedList<>();
		Set<Cell> alreadyVisited = new HashSet<>();
		
		pending.add(start);
		while (!pending.isEmpty()){
			Cell cell = pending.poll();
			for (Link link : cell.getLinks()) {
				Cell linked = link.opposite(cell);
				double totalWeight = distances.distanceFromRoot(cell) + link.getWeight();
				if (!distances.contains(linked) || totalWeight < distances.distanceFromRoot(linked)){
					distances.setCellDistance(linked, totalWeight);
					if(!alreadyVisited.contains(linked)){
						alreadyVisited.add(linked);
						pending.add(linked);
					}
				}
			}
		}
		return distances;
	}
	
	
	public static Distances DijkstraOriginal(Cell start){
		Distances distances = new Distances(start);
		Set<Cell> settled = new HashSet<>();
		Set<Cell> pending = new HashSet<>();

		pending.add(start);
		while (!pending.isEmpty()){
			Cell cell = pending.stream().min((c1,c2) -> Double.compare(distances.distanceFromRoot(c1), distances.distanceFromRoot(c2))).get();
			pending.remove(cell);
			settled.add(cell);

			for (Link link : cell.getLinks()) {
				Cell linked = link.opposite(cell);
				if(!settled.contains(linked)){
					double totalWeight = distances.distanceFromRoot(cell) + link.getWeight();
					if (!distances.contains(linked) || totalWeight < distances.distanceFromRoot(linked)){
						distances.setCellDistance(linked, totalWeight);
						pending.add(linked);
					}
				}
			}
		}
		return distances;
	}

	private static Distances DijkstraPriority(Cell start, BiFunction<Cell,Link,Double> computeDistance){
		Distances distances = new Distances(start);
		Queue<Cell> pending = new PriorityQueue<>((c1,c2) -> Double.compare(distances.distanceFromRoot(c1), distances.distanceFromRoot(c2)));
		Set<Cell> settled = new HashSet<>();

		pending.offer(start);
		while (!pending.isEmpty()){
			Cell cell = pending.poll();

			if(!settled.contains(cell)){
				settled.add(cell);
				for (Link link : cell.getLinks()) {
					Cell linked = link.opposite(cell);
					if(!settled.contains(linked)){
						double totalWeight = distances.distanceFromRoot(cell) + computeDistance.apply(cell, link);
						if (!distances.contains(linked) || totalWeight < distances.distanceFromRoot(linked)){
							distances.setCellDistance(linked, totalWeight);
							pending.offer(linked);
						}
					}
				}
			}
		}
		return distances;
	}
	
	public static Distances DijkstraWeights(Cell start){
		BiFunction<Cell,Link,Double> f = (cell, link) -> {
			return cell.getWeight();
		};
		return DijkstraPriority(start, f);
	}

	public static Distances DijkstraLinks(Cell start){
		BiFunction<Cell,Link,Double> f = (cell, link) -> {
			return link.getWeight();
		};
		return DijkstraPriority(start, f);
	}
	
	public static Distances BFS(Cell start, Cell target){
		Distances distances = new Distances(start);
		Queue<Cell> pending = new LinkedList<>();

		pending.add(start);
		while(!pending.isEmpty()){
			Cell cell = pending.poll();
			if(cell.equals(target)){
				break;
			}
			for (Cell linked : cell.getLinkedCells()) {
				if(!distances.contains(linked)){
					distances.setCellDistance(linked, distances.distanceFromRoot(cell)+1);
					pending.add(linked);
				}
			}
		}

		return distances.pathTo(target);
	}

	public Distances pathTo(Cell target){
		Cell current = target;
		Distances breadcrumbs = new Distances(root);
		breadcrumbs.setCellDistance(current, distanceFromRoot(current));
		
		while(!current.equals(root)){
			for (Cell linked : current.getLinkedCells()) {
				if(distanceFromRoot(linked) < distanceFromRoot(current)){
					breadcrumbs.setCellDistance(linked, distanceFromRoot(linked));
					current = linked;
					break;
				}
			}
		}
		return breadcrumbs;
	}

	// public static Distances deadEndFilling(Grid g, Cell start, Cell target){
	// 	Distances distances = new Distances(start);
	// 	for (Cell cell : g) {
	// 		distances.setCellDistance(cell, 0);
	// 	}

	// 	Set<Cell> deadends = g.findDeadends();
	// 	Map<Cell,Set<Cell>> toRelink = new HashMap<>();
	// 	deadends.remove(start);
	// 	deadends.remove(target);

	// 	distances.remove(deadends);

	// 	for (Cell cell : deadends) {
	// 		Cell previous = cell;
	// 		Cell next = new ArrayList<>(cell.getLinks()).get(0);
	// 		while(next.getLinks().size() <= 2 && !next.equals(start)&& !next.equals(target)){
	// 			distances.remove(next);
	// 			previous = next;
	// 			next = next.getLinks().stream().filter( c -> distances.contains(c) ).toList().get(0);
	// 		}
			
	// 		if(!toRelink.containsKey(next)){
	// 			toRelink.put(next, new HashSet<>());
	// 		}
	// 		toRelink.get(next).add(previous);
			
	// 		next.unlink(previous);
	// 	}

	// 	for (var entry : toRelink.entrySet()) {
	// 		for (Cell cell : entry.getValue()) {
	// 			entry.getKey().link(cell);
	// 		}
	// 	}


	// 	return distances;
	// }

	public static void deadEndFilling(Grid g, Cell start, Cell target){

		Set<Cell> deadends = g.findDeadends();
		deadends.remove(start);
		deadends.remove(target);

		for (Cell cell : deadends) {
			Cell previous = cell;
			Cell next = Utility.getRandomElement(previous.getLinkedCells()); //i collegamenti conterranno sempre un singolo elemento
			while(next.getLinks().size() <= 2 && !next.equals(start) && !next.equals(target)){
				next.unlink(previous);
				
				previous = next;
				next = Utility.getRandomElement(previous.getLinkedCells());
			}
			next.unlink(previous);
		}

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

	public int numOfCellsInPath(){
		return cells.size();
	}
}
