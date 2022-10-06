package maze.cells;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

	public int getWeight() {
		return weight;
	}

	public boolean hasLinks(){
		return !links.isEmpty();
	}

	public boolean isLinkedTo(Cell n){
		return links.containsKey(n);
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

}
