package maze.cells;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import maze.Utility;

public class Cell{
	private int row,col;
	protected Set<Cell> links;
	protected Cell north,south,east,west;
	
	public Cell(int row, int col) {
		this.row = row;
		this.col = col;
		links = new HashSet<>();
	}
	
	public void link(Cell cell){
		link(cell, true);
	}

	public void link(Cell cell, boolean bidi){
		links.add(cell);
		if (bidi)
			cell.link(this,false);
	}

	public void unlink(Cell cell){
		unlink(cell, true);
	}

	public void unlink(Cell cell, boolean bidi){
		links.remove(cell);
		if(bidi)
			cell.unlink(this,false);
	}
	
	public Cell getRandomNeighbour(){
		Set<Cell> pool = new HashSet<>();
		pool.add(north);
		pool.add(south);
		pool.add(east);
		pool.add(west);
		pool.remove(null); //nel caso una cella non abbia alcuni vicini
		return Utility.getRandomElement(pool);

	}

	public List<Cell> getNeighbours(){
		Set<Cell> pool = new HashSet<>();
		pool.add(north);
		pool.add(south);
		pool.add(east);
		pool.add(west);
		pool.remove(null); //nel caso una cella non abbia alcuni vicini
		return new ArrayList<>(pool);
	}

	public boolean hasLinks(){
		return !links.isEmpty();
	}

	public boolean isLinkedTo(Cell cell){
		return links.contains(cell);
	}

	public Set<Cell> getLinks() {
		return links;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public Cell getNorth() {
		return north;
	}
	
	public void setNorth(Cell north) {
		this.north = north;
	}

	public Cell getSouth() {
		return south;
	}

	public void setSouth(Cell south) {
		this.south = south;
	}

	public Cell getEast() {
		return east;
	}

	public void setEast(Cell east) {
		this.east = east;
	}

	public Cell getWest() {
		return west;
	}

	public void setWest(Cell west) {
		this.west = west;
	}
}
