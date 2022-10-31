package maze;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import maze.cells.Cell;
import maze.distances.Distances;

public class Grid implements Iterable<Cell>{

	protected int rows, cols;
	protected Cell[][] grid;
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	private Random rand = new Random();

	public Grid(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		grid = new Cell[rows][cols];
		prepareGrid();
	}

	public void prepareGrid(){
		for (int i = 0; i < rows; i++){
			for (int j = 0; j < cols; j++){
				grid[i][j] = new Cell(i,j);
			}
		}
	}

	public Cell getNorth(Cell c){
		return getCellAt(c.getRow()-1, c.getCol());
	}

	public Cell getEast(Cell c){
		return getCellAt(c.getRow(), c.getCol()+1);
	}

	public Cell getWest(Cell c){
		return getCellAt(c.getRow(), c.getCol()-1);
	}

	public Cell getSouth(Cell c){
		return getCellAt(c.getRow()+1, c.getCol());
	}

	public Cell getCellAt(int i, int j) {
		if ((i >= 0 && i <= rows-1) && (j >= 0 && j <= cols-1))
			return grid[i][j];
		else
			return null;
	}

	public Set<Cell> getNeighbours(Cell c){
		Set<Cell> neighbours = new HashSet<>();
		neighbours.add(getNorth(c));
		neighbours.add(getSouth(c));
		neighbours.add(getEast(c));
		neighbours.add(getWest(c));
		neighbours.remove(null);
		return neighbours;
	}

	public Cell getRandomNeighbour(Cell c){
		return Utility.getRandomElement(getNeighbours(c));
	}

	public Cell getRandomCell(){
		int r = rand.nextInt(rows-1);
		int c = rand.nextInt(cols-1);
		return grid[r][c];
	}

	public int size(){
		return rows*cols;
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

	private void displayGrid(Function<Cell,String> cellContent) {
		System.out.print("+");
		for(int j = 0; j < cols; j++){
			System.out.print("---+");
		}
		System.out.println();

		for(int i = 0; i < rows; i++){
			String top = "|";
			String bottom = "+";
			for(int j = 0; j < cols; j++){
				String body = cellContent.apply(grid[i][j]);
				String east_boundary = ((grid[i][j].isLinkedTo(getEast(grid[i][j]))? " " : "|"));
				top += body + east_boundary;
				String south_boundary = ((grid[i][j].isLinkedTo(getSouth(grid[i][j]))? "   " : "---"));
				String corner = "+";
				bottom += south_boundary + corner;
			}
			System.out.println(top);
			System.out.println(bottom);
		}
	}

	public void displayDistances(Distances distances){
		Function<Cell, String> f = cell -> {
			if (distances.contains(cell)){
				return " " + Integer.toString((int)distances.distanceFromRoot(cell), 36) + " ";
			}else{
				return "   ";
			}
		};
		displayGrid(f);
	}

	public void displayColoredDistances(Distances distances){
		Function<Cell, String> f = cell -> {
			if (distances.contains(cell)){
				return ANSI_GREEN_BACKGROUND + "   " + ANSI_RESET;
			}else{
				return "   ";
			}
		};
		displayGrid(f);
	}

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

	public Iterator<Cell[]> rowsIterator() {
		class RowsIterator implements Iterator<Cell[]> {

			private int i = -1;
		
			@Override
			public boolean hasNext() {
				if (i+1 < rows){
					i++;
					return true;
				}
				return false;
			}
		
			@Override
			public Cell[] next() {
				return grid[i];
			}
		}
		return new RowsIterator();
	}

	public Set<Cell> findDeadends(){
		Set<Cell> deadends = new HashSet<>();

		for (Cell cell : this) {
			if (cell.getLinks().size() == 1)
				deadends.add(cell);
		}
		return deadends;
	}

	public int findIntersections(){
		int ret = 0;

		for (Cell cell : this) {
			if (cell.getLinks().size() > 2)
				ret ++;
		}
		return ret;
	}

	public int findDirectionChanges(){
		int ret = 0;

		for (Cell cell : this) {
			if(!Objects.isNull(getNorth(cell)) && Objects.isNull(getSouth(cell))){
				if(!Objects.isNull(getEast(cell)) && Objects.isNull(getWest(cell))){
					ret++;
				}else if(Objects.isNull(getEast(cell)) && !Objects.isNull(getWest(cell))){
					ret++;
				}
			} else if(!Objects.isNull(getSouth(cell)) && Objects.isNull(getNorth(cell))){
				if(!Objects.isNull(getEast(cell)) && Objects.isNull(getWest(cell))){
					ret++;
				}else if(Objects.isNull(getEast(cell)) && !Objects.isNull(getWest(cell))){
					ret++;
				}
			} else if(!Objects.isNull(getEast(cell)) && Objects.isNull(getWest(cell))){
				if(!Objects.isNull(getNorth(cell)) && Objects.isNull(getSouth(cell))){
					ret++;
				}else if(Objects.isNull(getNorth(cell)) && !Objects.isNull(getSouth(cell))){
					ret++;
				}
			} else if(!Objects.isNull(getWest(cell)) && Objects.isNull(getEast(cell))){
				if(!Objects.isNull(getNorth(cell)) && Objects.isNull(getSouth(cell))){
					ret++;
				}else if(Objects.isNull(getNorth(cell)) && !Objects.isNull(getSouth(cell))){
					ret++;
				}
			}
		}
		return ret;
	}

	public void braid(int p){
		for (Cell cell : findDeadends()) {
			//arrivati in un vicolo cieco aggiungiamo un ciclo con probabilita' p
			if(cell.getLinks().size() == 1 && Utility.randomBoolean(p)){ 
				Set<Cell> neighbours = getNeighbours(cell);
				neighbours.removeAll(cell.getLinkedCells());

				Set<Cell> best = neighbours;
				best.removeIf( c -> cell.getLinks().size() != 1);
				if (best.isEmpty())
					best = neighbours;

				Cell neighbour = Utility.getRandomElement(best);
				cell.link(neighbour);
			}
		}
	}
	
}