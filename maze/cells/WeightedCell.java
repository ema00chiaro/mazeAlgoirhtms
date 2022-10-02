package maze.cells;

import java.util.PriorityQueue;
import java.util.Queue;

import maze.distances.Distances;

public class WeightedCell extends Cell implements Comparable<Cell>{

	private int weight;

	public WeightedCell(int row, int col) {
		super(row, col);
		weight = 1;
	}

	public WeightedCell(int row, int col, int weight) {
		super(row, col);
		if(weight < 1){
			throw new RuntimeException("Errore: il peso di una cella deve essere minimo 1");
		}
		this.weight = weight;
	}

	public WeightedCell(Cell c){
		this(c.getRow(),c.getCol());
		weight = 1;
		links = c.links;
		north = c.north;
		south = c.south;
		east = c.east;
		west = c.west;
	}

	public WeightedCell(Cell c, int weight){
		this(c.getRow(),c.getCol(), weight);
		links = c.links;
		north = c.north;
		south = c.south;
		east = c.east;
		west = c.west;
	}

	public int getWeight(){
		return weight;
	}

	public void setWeight(int weight){
		this.weight = weight;
	}

	@Override
	public int compareTo(Cell o) {
		if(o instanceof WeightedCell){
			return getWeight() - ((WeightedCell)o).getWeight();
		}else{
			return getWeight() - 1;
		}
	}

	@Override
	public Distances distances(){
		Distances distances = new Distances(this);
		Queue<Cell> pending = new PriorityQueue<>((c1,c2) -> { //FIXME
				int w1 = 1, w2 = 1;
				if (c1 instanceof WeightedCell)
					w1 = ((WeightedCell)c1).getWeight();
				if (c2 instanceof WeightedCell)
					w2 = ((WeightedCell)c2).getWeight();
				return w1-w2;
			}
		);
		pending.add(this);
		while (!pending.isEmpty()){
			Cell cell = pending.poll();

			for (Cell linked : cell.getLinks()) {
				int weight = 1;
				if(linked instanceof WeightedCell){
					weight = ((WeightedCell)linked).getWeight();
				}
				int totalWeight = distances.distanceFromRoot(cell) + weight;
				if (!distances.contains(linked) || totalWeight < distances.distanceFromRoot(linked)){
					pending.add(linked);
					distances.setCellDistance(linked, totalWeight);
				}
			}
		}
		return distances;
	}

}
