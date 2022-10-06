// package maze.distances;

// import java.util.PriorityQueue;
// import java.util.Queue;
// import java.util.function.Function;

// import maze.cells.Cell;
// import maze.cells.WeightedLinksCell;

// public class Astar extends Distances{

// 	public Distances distanceBetween(WeightedLinksCell start, WeightedLinksCell target, Function<WeightedLinksCell, Integer> heuristic) {
// 		Distances distances = new Distances(start);
// 		Queue<WeightedLinksCell> closedQueue = new PriorityQueue<>();
// 		Queue<WeightedLinksCell> openQueue = new PriorityQueue<>();

// 		start.f = start.g + heuristic.apply(target);
// 		openQueue.add(start);

// 		while(!openQueue.isEmpty()){
// 			WeightedLinksCell cell = openQueue.peek();
// 			if(cell == target){
				
// 			}
			
// 			for (Cell c : cell.getLinks()) {
// 				WeightedLinksCell linked = (WeightedLinksCell)c;
// 				int totalWeight = cell.g + cell.getLinkWeight(linked);

// 				if(!openQueue.contains(linked) && !closedQueue.contains(linked)){
// 					linked.g = totalWeight;
// 					linked.f = linked.g + heuristic.apply(target);
// 					distances.setCellDistance(linked, totalWeight);
// 				}else{
// 					if(totalWeight < linked.g){
// 						linked.g = totalWeight;
// 						linked.f = linked.g + heuristic.apply(target);
// 						distances.setCellDistance(linked, totalWeight);

// 						if(closedQueue.contains(linked)){
// 							closedQueue.remove(linked);
// 							openQueue.add(linked);
// 						}
// 					}
// 				}
				
// 			}

// 			openQueue.remove(cell);
// 			closedQueue.add(cell);
// 		}
// 		return distances;
// 	}
	
// }
