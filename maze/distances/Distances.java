package maze.distances;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.BiFunction;

import maze.Grid;
import maze.Utility;
import maze.cells.Cell;

public class Distances{

	protected Cell root; 
	public Map<Cell,Double> distances;
	private Map<Cell,Cell> parents;

	private Distances(Cell root){
		this.root = root;
		distances = new HashMap<>();
		distances.put(root, 0.0);
		parents = new HashMap<>();
	}

	public static Distances dijkstraSimplified(Cell start){
		Distances mst = new Distances(start);
		List<Cell> frontier = new ArrayList<>();
		frontier.add(start);

		while (!frontier.isEmpty()){
			List<Cell> frontier_new = new ArrayList<>(); 
			for (Cell cell : frontier) {
				for (Cell linked : cell.getLinks()) {
					if (!mst.contains(linked)){
						mst.setDistance(linked, mst.distanceFromRoot(cell) + 1);
						mst.setParent(linked, cell);
						frontier_new.add(linked);
					}
				}
			}
			frontier = frontier_new;
		}
		return mst;
	}
	
	
	public static Distances dijkstraOriginal(Cell start){
		Distances mst = new Distances(start);
		Set<Cell> settled = new HashSet<>();
		Set<Cell> pending = new HashSet<>();

		pending.add(start);
		while (!pending.isEmpty()){
			Cell cell = pending.stream().min((c1,c2) -> Double.compare(mst.distanceFromRoot(c1), mst.distanceFromRoot(c2))).get();
			pending.remove(cell);
			settled.add(cell);

			for (Cell linked : cell.getLinks()) {
				if(!settled.contains(linked)){
					double totalWeight = mst.distanceFromRoot(cell) + cell.getLinkWeight(linked);
					if (!mst.contains(linked) || totalWeight < mst.distanceFromRoot(linked)){
						mst.setDistance(linked, totalWeight);
						mst.setParent(linked, cell);
						pending.add(linked);
					}
				}
			}
		}
		return mst;
	}

	private static Distances dijkstraPriority(Cell start, BiFunction<Cell,Cell,Double> computeDistance){
		Distances mst = new Distances(start);
		Queue<Cell> pending = new PriorityQueue<>((c1,c2) -> {
			return Double.compare(mst.distanceFromRoot(c1), mst.distanceFromRoot(c2));
		});
		Set<Cell> settled = new HashSet<>();

		pending.offer(start);
		while (!pending.isEmpty()){
			Cell cell = pending.poll();

			if(!settled.contains(cell)){
				settled.add(cell);
				for (Cell linked : cell.getLinks()) {
					if(!settled.contains(linked)){
						double totalWeight = mst.distanceFromRoot(cell) + computeDistance.apply(cell, linked);
						if (!mst.contains(linked) || totalWeight < mst.distanceFromRoot(linked)){
							mst.setDistance(linked, totalWeight);
							mst.setParent(linked, cell);
							pending.offer(linked);
						}
					}
				}
			}
		}
		return mst;
	}
	
	public static Distances dijkstraWeights(Cell start){
		BiFunction<Cell,Cell,Double> f = (cell, linked) -> {
			return linked.getWeight();
		};
		return dijkstraPriority(start, f);
	}

	public static Distances dijkstraLinks(Cell start){
		BiFunction<Cell,Cell,Double> f = (cell, linked) -> {
			return cell.getLinkWeight(linked);
		};
		return dijkstraPriority(start, f);
	}
	
	// public static Distances BFS(Cell start, Cell target){
	// 	Distances mst = new Distances(start);
	// 	Queue<Cell> pending = new LinkedList<>();

	// 	pending.add(start);
	// 	while(!pending.isEmpty()){
	// 		Cell cell = pending.poll();
	// 		if(cell.equals(target)){
	// 			break;
	// 		}
	// 		for (Cell linked : cell.getLinks()) {
	// 			if(!mst.contains(linked)){
	// 				mst.setDistance(linked, mst.distanceFromRoot(cell)+1);
	// 				pending.add(linked);
	// 			}
	// 		}
	// 	}

	// 	return mst.pathTo(target);
	// }

	public Distances pathTo(Cell target){
		Distances mst = new Distances(root);
		Cell current = target;
		mst.setDistance(current, distanceFromRoot(current));
		current = getParent(current);
		while(!Objects.isNull(current)){
			mst.setDistance(current, distanceFromRoot(current));
			current = getParent(current);
		}
		return mst;
	}

	// public static Distances deadEndFilling(Grid g, Cell start, Cell target){
	// 	Distances mst = new Distances(start);
	// 	for (Cell cell : g) {
	// 		mst.setCellDistance(cell, 0);
	// 	}

	// 	Set<Cell> deadends = g.findDeadends();
	// 	Map<Cell,Set<Cell>> toRelink = new HashMap<>();
	// 	deadends.remove(start);
	// 	deadends.remove(target);

	// 	mst.remove(deadends);

	// 	for (Cell cell : deadends) {
	// 		Cell previous = cell;
	// 		Cell next = new ArrayList<>(cell.getLinks()).get(0);
	// 		while(next.getLinks().size() <= 2 && !next.equals(start)&& !next.equals(target)){
	// 			mst.remove(next);
	// 			previous = next;
	// 			next = next.getLinks().stream().filter( c -> mst.contains(c) ).toList().get(0);
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

	// 	return mst;
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

	// private void remove(Collection<Cell> toRemove) {
	// 	toRemove.stream().forEach( c -> cells.remove(c));
	// }	

	// private void remove(Cell toRemove) {
	// 	cells.remove(toRemove);
	// }

	public double distanceFromRoot(Cell cell){
		return distances.get(cell);
	}

	public boolean contains(Cell cell){
		return distances.containsKey(cell);
	}

	private void setDistance(Cell cell, double newDistance){
		distances.put(cell, newDistance);
	}

	private void setParent(Cell child, Cell parent){
		parents.put(child, parent);
	}

	private Cell getParent(Cell child){
		return parents.get(child);
	}
}
