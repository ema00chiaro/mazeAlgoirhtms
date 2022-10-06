package maze.cells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import maze.Utility;

public class Cell{
	private int row,col;
	private int weight;
	private Map<Cell, Integer> links;

	public Cell(int row, int col, int weight) {
		this.row = row;
		this.col = col;
		this.weight = weight;
		links = new HashMap<>();
	}

	public void link(Cell n, int weight){
		//NOT bidirectional
		link(n,weight,false);
	}

	public void link(Cell n){
		link(n,1,true);
	}

	public void link(Cell n, boolean bidi){
		link(n,1,bidi);
	}
	
	public void link(Cell n, int weight, boolean bidi){
		links.put(n,weight);
		if(bidi){
			n.link(this,weight,false);
		}
	}

	public void unlink(Cell n){
		//NOT bidirectional
		unlink(n,false);
	}

	public void unlink(Cell n, boolean bidi){
		links.remove(n);
		if(bidi){
			n.unlink(this, false);
		}
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public Set<Cell> getLinks() {
		return links.keySet();
	}

	protected int getWeight() {
		return weight;
	}

	public boolean hasLinks(){
		return !links.isEmpty();
	}

	public boolean isLinkedTo(Cell n){
		return links.containsKey(n);
	}

	protected void setWeight(int weight) {
		this.weight = weight;
	}

	//------------------------------------------------------------------------------------------------------------------------------------------
	protected Cell north,south,east,west; 
	
	public Cell getRandomNeighbour(){
		Set<Cell> pool = new HashSet<>();
		pool.add(north);
		pool.add(south);
		pool.add(east);
		pool.add(west);
		pool.remove(null); //nel caso una cella non abbia alcuni vicini
		return Utility.getRandomElement(pool);

	}

	public Set<Cell> getNeighbours(){
		Set<Cell> pool = new HashSet<>();
		pool.add(north);
		pool.add(south);
		pool.add(east);
		pool.add(west);
		pool.remove(null); //nel caso una cella non abbia alcuni vicini
		return pool;
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
