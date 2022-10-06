package maze.prove;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import maze.Utility;

public abstract class NodeProva {
	private int row,col;
	private int weight;
	private Map<NodeProva, Integer> links;

	public NodeProva(int row, int col, int weight) {
		this.row = row;
		this.col = col;
		this.weight = weight;
		links = new HashMap<>();
	}

	public void link(NodeProva n, int weight){
		//NOT bidirectional
		link(n,weight,false);
	}

	public void link(NodeProva n){
		link(n,1,true);
	}

	public void link(NodeProva n, boolean bidi){
		link(n,1,bidi);
	}
	
	public void link(NodeProva n, int weight, boolean bidi){
		links.put(n,weight);
		if(bidi){
			n.link(this,weight,false);
		}
	}

	public void unlink(NodeProva n){
		//NOT bidirectional
		unlink(n,false);
	}

	public void unlink(NodeProva n, boolean bidi){
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

	public Set<NodeProva> getLinks() {
		return links.keySet();
	}

	protected int getWeight() {
		return weight;
	}

	public boolean hasLinks(){
		return !links.isEmpty();
	}

	public boolean isLinkedTo(NodeProva n){
		return links.containsKey(n);
	}

	protected void setWeight(int weight) {
		this.weight = weight;
	}

	//------------------------------------------------------------------------------------------------------------------------------------------
	protected NodeProva north,south,east,west; 
	
	public NodeProva getRandomNeighbour(){
		Set<NodeProva> pool = new HashSet<>();
		pool.add(north);
		pool.add(south);
		pool.add(east);
		pool.add(west);
		pool.remove(null); //nel caso una cella non abbia alcuni vicini
		return Utility.getRandomElement(pool);

	}

	public Set<NodeProva> getNeighbours(){
		Set<NodeProva> pool = new HashSet<>();
		pool.add(north);
		pool.add(south);
		pool.add(east);
		pool.add(west);
		pool.remove(null); //nel caso una cella non abbia alcuni vicini
		return pool;
	}

	public NodeProva getNorth() {
		return north;
	}

	public void setNorth(NodeProva north) {
		this.north = north;
	}

	public NodeProva getSouth() {
		return south;
	}

	public void setSouth(NodeProva south) {
		this.south = south;
	}

	public NodeProva getEast() {
		return east;
	}

	public void setEast(NodeProva east) {
		this.east = east;
	}

	public NodeProva getWest() {
		return west;
	}

	public void setWest(NodeProva west) {
		this.west = west;
	}
}
