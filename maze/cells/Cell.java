package maze.cells;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Cell{
	private int row,col;
	private double weight;
	private Map<Cell, Double> links;

	public Cell(int row, int col, int weight) {
		this.row = row;
		this.col = col;
		this.weight = weight;
		links = new HashMap<>();
	}

	public Cell(int row, int col){
		this(row,col,1);
	}

	public void link(Cell cell){
		link(cell,1,true);
	}

	public void link(Cell cell, boolean bidi){
		link(cell,1,bidi);
	}
	
	public void link(Cell cell, double weight, boolean bidi){
		links.put(cell,weight);
		if(bidi){
			cell.links.put(this, weight);
		}
	}

	public void unlink(Cell cell){
		unlink(cell,true);
	}

	public void unlink(Cell cell, boolean bidi){
		links.remove(cell);
		if(bidi){
			cell.links.remove(this);
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

	public double getLinkWeight(Cell linked) {
		return links.get(linked);
	}

	public void setLinkWeight(Cell linked, double weight) {
		links.put(linked, weight);
		linked.links.put(this, weight);
	}

	public double getWeight() {
		return weight;
	}

	public boolean hasLinks(){
		return !links.isEmpty();
	}

	public boolean isLinkedTo(Cell cell){
		return links.containsKey(cell);
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

}
