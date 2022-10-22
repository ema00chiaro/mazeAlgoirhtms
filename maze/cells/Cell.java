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

	public void link(Cell n, int weight){
		link(n,weight,true);
	}

	public void link(Cell n){
		link(n,1,true);
	}

	public void link(Cell n, boolean bidi){
		link(n,1,bidi);
	}
	
	public void link(Cell n, double weight, boolean bidi){
		links.put(n,weight);
		if(bidi){
			n.link(this,weight,false);
		}
	}

	public void unlink(Cell n){
		unlink(n,true);
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

	public boolean isLinkedTo(Cell n){
		return links.containsKey(n);
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

}
