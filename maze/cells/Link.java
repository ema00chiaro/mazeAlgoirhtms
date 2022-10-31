package maze.cells;

public class Link {

	private Cell x;
	private Cell y;
	private double weight;

	public Link(Cell x, Cell y, double weight) {
		this.x = x;
		this.y = y;
		this.weight = weight;
	}

	public Link(Cell x, Cell y) {
		this(x, y, 1);
	}
	
	public Cell opposite(Cell cell) {
		if(cell.equals(x))
			return y;
		return x;
	}

	public double getWeight() {
		return weight;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((x == null) ? 0 : x.hashCode()) + ((y == null) ? 0 : y.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Link other = (Link) obj;
		if (Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight))
			return false;
		if ((x.equals(other.x) && y.equals(other.y)) || (x.equals(other.y) && y.equals(other.x)))
			return true;
		return false;
	}
}
