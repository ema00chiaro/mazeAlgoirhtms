package maze;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Distances {

	private Cell root;

	private Map<Cell,Integer> cells;

	public Distances(Cell root) {
		this.root = root;
		cells = new HashMap<>();
		cells.put(root, 0);
	}

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

	public Set<Cell> getCells(){
		return cells.keySet();
	}

	public Map<Cell,Integer> getDistancesMap(){
		return cells;
	}

	public Cell getRoot() {
		return root;
	}
}
