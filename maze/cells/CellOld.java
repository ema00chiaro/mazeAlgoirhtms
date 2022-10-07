package maze.cells;

import java.util.HashSet;
import java.util.Set;


public class CellOld{
	private int row,col;
	protected Set<CellOld> links;

	public CellOld(int row, int col) {
		this.row = row;
		this.col = col;
		links = new HashSet<>();
	}
	
	public void link(CellOld cell){
		link(cell, true);
	}

	public void link(CellOld cell, boolean bidi){
		links.add(cell);
		if (bidi)
			cell.link(this,false);
	}

	public void unlink(CellOld cell){
		unlink(cell, true);
	}

	public void unlink(CellOld cell, boolean bidi){
		links.remove(cell);
		if(bidi)
			cell.unlink(this,false);
	}

	public boolean hasLinks(){
		return !links.isEmpty();
	}

	public boolean isLinkedTo(CellOld cell){
		return links.contains(cell);
	}

	public Set<CellOld> getLinks() {
		return links;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
	
}
