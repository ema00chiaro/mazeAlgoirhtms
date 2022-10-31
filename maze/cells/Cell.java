package maze.cells;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Cell{
	private int row,col;
	private double weight;
	private Set<Link> links;

	public Cell(int row, int col, int weight) {
		this.row = row;
		this.col = col;
		this.weight = weight;
		links = new HashSet<>();
	}

	public Cell(int row, int col){
		this(row,col,1);
	}

	public void link(Cell cell){
		Link l = new Link(this, cell);
		links.add(l);
		cell.links.add(l);
	}

	public void unlink(Cell cell){
		links.remove(getLinkTo(cell));
		cell.links.remove(cell.getLinkTo(this));
	}

	public Set<Cell> getLinkedCells() {
		return links.stream()
				.map(l -> l.opposite(this))
				.collect(Collectors.toSet());
	}
	
	public Link getLinkTo(Cell cell){
		for (Link link : links) {
			if(link.opposite(this).equals(cell)){
				return link;
			}
		}
		return null;
	}

	public boolean isLinkedTo(Cell cell){
		return !Objects.isNull(getLinkTo(cell));
	}
	
	public Set<Link> getLinks() {
		return links;
	}

	public boolean hasLinks(){
		return !links.isEmpty();
	}
	
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
	
	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
}
