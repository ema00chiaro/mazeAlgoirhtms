package maze;

import java.util.Objects;

public class DistanceGrid extends Grid{

	private Distances distances;
	
	public DistanceGrid(int rows, int cols) {
		super(rows, cols);
	}
	
	public String contentsOf(Cell cell){
		if (!Objects.isNull(distances) && distances.contains(cell))
			//return ANSI_GREEN_BACKGROUND + "   " + ANSI_RESET;
			return " " + Integer.toString(distances.distanceFromRoot(cell), 36) + " ";
		else
			return "   ";
		}
		
		public void computeDistances(Cell start){
			distances = start.distances();
		}
		
		public void computeDistances(Cell start, Cell target){
			distances = start.distances().pathTo(target);
		}
		
		public void setDistances(Distances distances) {
			this.distances = distances;
		}

}
