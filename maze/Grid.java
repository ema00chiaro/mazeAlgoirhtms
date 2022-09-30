package maze;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class Grid implements Iterable<Cell>{

	private int rows, cols;
	private Cell[][] grid;
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";

	public Grid(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		grid = new Cell[rows][cols];
		prepareGrid();
		configureCells();
	}

	protected void prepareGrid(){
		for (int i = 0; i < rows; i++){
			for (int j = 0; j < cols; j++){
				grid[i][j] = new Cell(i, j);
			}
		}
	}
	
	private void configureCells() {
		for (int i = 0; i < rows; i++){
			for (int j = 0; j < cols; j++){
				grid[i][j].setNorth(getCellAt(i-1,j));
				grid[i][j].setSouth(getCellAt(i+1,j));
				grid[i][j].setEast(getCellAt(i,j+1));
				grid[i][j].setWest(getCellAt(i,j-1));
			}
		}
	}

	public Cell randomCell(){
		Random rand = new Random();
		int r = rand.nextInt(rows-1);
		int c = rand.nextInt(cols-1);
		return grid[r][c];
	}

	public int size(){
		return rows*cols;
	}

	public Cell getCellAt(int i, int j) {
		if ((i >= 0 && i <= rows-1) && (j >= 0 && j <= cols-1))
			return grid[i][j];
		else
			return null;
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	public void displayGrid(){
		displayGrid( c -> "   ");
	}

	private void displayGrid(Function<Cell,String> f) {
		System.out.print("+");
		for(int j = 0; j < cols; j++){
			System.out.print("---+");
		}
		System.out.println();

		for(int i = 0; i < rows; i++){
			String top = "|";
			String bottom = "+";
			for(int j = 0; j < cols; j++){
				String body = f.apply(grid[i][j]);
				String east_boundary = ((grid[i][j].isLinkedTo(grid[i][j].getEast())? " " : "|"));
				top += body + east_boundary;
				String south_boundary = ((grid[i][j].isLinkedTo(grid[i][j].getSouth())? "   " : "---"));
				String corner = "+";
				bottom += south_boundary + corner;
			}
			System.out.println(top);
			System.out.println(bottom);
		}
	}

	public void displayDistances(Cell start){
		Distances distances = start.distances();
		displayGrid(c -> " " + Integer.toString(distances.distanceFromRoot(c), 36) + " ");
	}
	
	public void displayDistanceBetween(Cell start, Cell target){
		Distances distances = start.distances().pathTo(target);
		Function<Cell, String> f = cell -> {
			if (distances.contains(cell)){
				return " " + Integer.toString(distances.distanceFromRoot(cell), 36) + " ";
			}else{
				return "   ";
			}
		};
		displayGrid(f);
	}

	public void displayPathBetween(Cell start, Cell target){
		Distances distances = start.distances().pathTo(target);
		Function<Cell, String> f = cell -> {
			if (distances.contains(cell)){
				return ANSI_GREEN_BACKGROUND + "   " + ANSI_RESET;
			}else{
				return "   ";
			}
		};
		displayGrid(f);
	}

	// private Function<Cell,String> setPathContent(){
		
	// }

	public Cell[][] getGrid(){
		return grid;
	}

	@Override
	public Iterator<Cell> iterator() {
		class GridIterator implements Iterator<Cell> {

			private int i = 0;
			private int j = -1; //nella prima iterazione dovrà ritornare [0,0]
		
			@Override
			public boolean hasNext() {
				if (j+1 <= cols-1){
					j = j+1;
					return true;
				}else{
					if (i+1 <= rows-1){
						i = i+1;
						j = 0;
						return true;
					}else{
						return false;
					}
				}
			}
		
			@Override
			public Cell next() {
				return grid[i][j];
			}
		}
		return new GridIterator();
	}

	public List<Cell> findDeadends(){
		List<Cell> deadends = new ArrayList<>();

		for (Cell cell : this) {
			if (cell.getLinks().size() == 1)
				deadends.add(cell);
		}
		return deadends;
	}

	public void braid(){
		for (Cell cell : findDeadends()) {
			if(cell.getLinks().size() == 1 && Utility.randomBoolean(2)){ //arrivati in un vicolo cieco aggiungiamo un ciclo al 50%
				List<Cell> neighbours = cell.getNeighbours();
				neighbours.removeAll(cell.getLinks());

				List<Cell> best = neighbours;
				best.removeIf( c -> cell.getLinks().size() != 1);
				if (best.isEmpty())
					best = neighbours;

				Cell neighbour = best.get(new Random().nextInt(best.size()));
				cell.link(neighbour);
			}
		}
	}
	
}