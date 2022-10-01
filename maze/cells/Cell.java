package maze.cells;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class Cell{
	private int row,col;
	protected Set<Cell> links;
	protected Cell north,south,east,west;
	
	public Cell(int row, int col) {
		this.row = row;
		this.col = col;
		links = new HashSet<>();
	}
	
	public boolean link(Cell cell){
		boolean added = links.add(cell);
		if (added)
			cell.link(this);
		return added;
	}

	public boolean unlink(Cell cell){
		boolean removed = links.remove(cell);
		if(removed)
			cell.unlink(this);
		return removed;
	}
	
	//TODO
	public Cell getRandomNeighbour(){
		List<Cell> pool = new ArrayList<>();
		if (!Objects.isNull(north))
		pool.add(north);
		if (!Objects.isNull(south))
			pool.add(south);
		if (!Objects.isNull(east))
			pool.add(east);
		if (!Objects.isNull(west))
			pool.add(west);
		int r = new Random().nextInt(pool.size());
		return pool.get(r);
	}

	//TODO
	public List<Cell> getNeighbours(){
		List<Cell> l = new ArrayList<>();
		if (!Objects.isNull(north))
			l.add(north);
		if (!Objects.isNull(south))
			l.add(south);
		if (!Objects.isNull(east))
			l.add(east);
		if (!Objects.isNull(west))
		l.add(west);
		return l;
	}

	// //Dijkstra semplificato - da usare quando tutti i pesi degli archi sono 1
	// //verrà overridato in weighted cell
	// public Distances distances(){
	// 	Distances distances = new Distances(this);
	// 	List<Cell> frontier = new ArrayList<>();
	// 	frontier.add(this);

	// 	while (!frontier.isEmpty()){
	// 		List<Cell> frontier_new = new ArrayList<>(); 
	// 		for (Cell cell : frontier) {
	// 			for (Cell linked : cell.links) {
	// 				if (!distances.contains(linked)){ // da rivedere
	// 					distances.setCellDistance(linked, distances.distanceFromRoot(cell) + 1);
	// 					frontier_new.add(linked);
	// 				}
	// 			}
	// 		}
	// 		frontier = frontier_new;
	// 	}
	// 	return distances;
	// }

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
