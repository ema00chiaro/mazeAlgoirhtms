package maze;

public class AldousBorder {

	public static void buildMaze(Grid grid){
		Cell cell = grid.randomCell();
		int unvisited = grid.size()-1;

		while (unvisited > 0){
			Cell neighbour = cell.getRandomNeighbour();

			if(!neighbour.hasLinks()){
				cell.link(neighbour);
				unvisited -= 1;
			}
			cell = neighbour;
		}

	}
}
