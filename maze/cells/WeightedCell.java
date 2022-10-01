package maze.cells;

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
		weight = 1;
		links = c.links;
		north = c.north;
		south = c.south;
		east = c.east;
		west = c.west;
	}

	// @Override
	// public Distances distances(){
	// 	Distances weights = new Distances(this);
	// 	Queue<WeightedCell> pending = new PriorityQueue<>();
	// 	pending.add(this);

	// 	while (!pending.isEmpty()){
	// 		Cell cell = pending.poll();

	// 		for (Cell c : cell.getLinks()) {
	// 			WeightedCell linked = (WeightedCell)c; //FIXME
	// 			// if(c instanceof WeightedCell){
	// 			// 	linked = (WeightedCell)c;
	// 			// }else{
	// 			// 	linked = new WeightedCell(c);
	// 			// }
	// 			int totalWeight = weights.distanceFromRoot(cell) + linked.getWeight();
	// 			if (!weights.contains(linked) || totalWeight < weights.distanceFromRoot(linked)){
	// 				pending.add(linked);
	// 				weights.setCellDistance(linked, totalWeight);
	// 			}
	// 		}
	// 	}
	// 	return weights;
	// }

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

}
