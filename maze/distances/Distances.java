package maze.distances;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

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

	public static Distances DijkstraSimplified(Cell start){
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

	public static Distances DijkstraWeights(Cell start){
		Distances distances = new Distances(start);
		Queue<Cell> pending = new PriorityQueue<>((c1,c2) -> c1.getWeight()-c2.getWeight()	);
		pending.add(start);
		while (!pending.isEmpty()){
			Cell cell = pending.poll();

			for (Cell linked : cell.getLinks()) {
				double totalWeight = distances.distanceFromRoot(cell) + linked.getWeight();
				if (!distances.contains(linked) || totalWeight < distances.distanceFromRoot(linked)){
					pending.add(linked);
					distances.setCellDistance(linked, totalWeight);
				}
			}
		}
		return distances;
	}

	public static Distances Dijkstra(Cell start){
		return null;
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

	public static Distances deadEndFilling(Grid g, Cell start, Cell target){
		Distances distances = new Distances(start);
		for (Cell cell : g) {
			distances.setCellDistance(cell, 0);
		}

		Set<Cell> deadends = g.findDeadends();
		Map<Cell,Set<Cell>> toRelink = new HashMap<>();
		deadends.remove(start);
		deadends.remove(target);

		distances.remove(deadends);

		for (Cell cell : deadends) {
			Cell previous = cell;
			Cell next = new ArrayList<>(cell.getLinks()).get(0);
			while(next.getLinks().size() <= 2 && !next.equals(start)&& !next.equals(target)){
				distances.remove(next);
				previous = next;
				next = next.getLinks().stream().filter( c -> distances.contains(c) ).toList().get(0);
			}
			
			if(!toRelink.containsKey(next)){
				toRelink.put(next, new HashSet<>());
			}
			toRelink.get(next).add(previous);
			
			next.unlink(previous);
		}

		for (var entry : toRelink.entrySet()) {
			for (Cell cell : entry.getValue()) {
				entry.getKey().link(cell);
			}
		}

		return distances;
	}

	private void remove(Collection<Cell> toRemove) {
		toRemove.stream().forEach( c -> cells.remove(c));
	}	

	private void remove(Cell toRemove) {
		cells.remove(toRemove);
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
