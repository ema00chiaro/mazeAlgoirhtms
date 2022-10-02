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
