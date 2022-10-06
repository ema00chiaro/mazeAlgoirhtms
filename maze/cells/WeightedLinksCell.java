// package maze.cells;

// import java.util.HashMap;
// import java.util.Map;

// public class WeightedLinksCell extends WeightedCell implements Comparable<WeightedLinksCell>{
	
// 	private Map<Cell,Integer> linksCost;

// 	//funzioni di valutazione
// 	public int f = Integer.MAX_VALUE;
// 	public int g = Integer.MAX_VALUE;
// 	//euristica
// 	public int h;

// 	public WeightedLinksCell(int row, int col) {
// 		super(row, col);
// 		linksCost = new HashMap<>();
// 	}

// 	@Override
// 	public void link(Cell cell, boolean bidi) {
// 		linksCost.put(cell, 1);
// 		super.link(cell, bidi);
// 	}

// 	public void setLinkWeight(WeightedLinksCell cell, int weight){
// 		linksCost.put(cell, weight);
// 		cell.linksCost.put(this, weight);
// 	}

// 	public int getLinkWeight(WeightedLinksCell cell){
// 		return linksCost.get(cell);
// 	}

// 	@Override
// 	public void unlink(Cell cell, boolean bidi) {
// 		linksCost.remove(cell);
// 		super.unlink(cell, bidi);
// 	}

// 	@Override
// 	public int compareTo(WeightedLinksCell o) {
// 		return Double.compare(f, o.f);
// 	}
	

// }
