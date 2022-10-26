package maze.distances;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.BiFunction;

import javax.swing.text.Utilities;

import maze.Grid;
import maze.Utility;
import maze.cells.Cell;

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

	
	//TODO: remove
	public static Distances SPFA(Cell start){
		Distances distances = new Distances(start);
		Queue<Cell> pending = new LinkedList<>();
		Set<Cell> alreadyVisited = new HashSet<>();
		
		pending.add(start);
		while (!pending.isEmpty()){
			Cell cell = pending.poll();
			for (Cell linked : cell.getLinks()) {
				double totalWeight = distances.distanceFromRoot(cell) + cell.getLinkWeight(linked);
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

			for (Cell linked : cell.getLinks()) {
				if(!settled.contains(linked)){
					double totalWeight = distances.distanceFromRoot(cell) + cell.getLinkWeight(linked);
					if (!distances.contains(linked) || totalWeight < distances.distanceFromRoot(linked)){
						distances.setCellDistance(linked, totalWeight);
						pending.add(linked);
					}
				}
			}
		}
		return distances;
	}

	private static Distances DijkstraPriority(Cell start, BiFunction<Cell,Cell,Double> computeDistance){
		Distances distances = new Distances(start);
		Queue<Cell> pending = new PriorityQueue<>((c1,c2) -> Double.compare(distances.distanceFromRoot(c1), distances.distanceFromRoot(c2)));
		Set<Cell> settled = new HashSet<>();

		pending.offer(start);
		while (!pending.isEmpty()){
			Cell cell = pending.poll();

			if(!settled.contains(cell)){
				settled.add(cell);
				for (Cell linked : cell.getLinks()) {
					if(!settled.contains(linked)){
						double totalWeight = distances.distanceFromRoot(cell) + computeDistance.apply(cell, linked);
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
		BiFunction<Cell,Cell,Double> f = (cell, linked) -> {
			return linked.getWeight();
		};
		return DijkstraPriority(start, f);
	}

	public static Distances DijkstraLinks(Cell start){
		BiFunction<Cell,Cell,Double> f = (cell, linked) -> {
			return cell.getLinkWeight(linked);
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
			for (Cell linked : cell.getLinks()) {
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
			for (Cell linked : current.getLinks()) {
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
			Cell next = Utility.getRandomElement(previous.getLinks()); //i collegamenti conterranno sempre un singolo elemento
			while(next.getLinks().size() <= 2 && !next.equals(start) && !next.equals(target)){
				next.unlink(previous);
				previous = next;
				next = Utility.getRandomElement(previous.getLinks());
			}
			next.unlink(previous);
		}

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

	public int numOfCellsInPath(){
		return cells.size();
	}
}
