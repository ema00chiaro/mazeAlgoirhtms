package maze;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import maze.cells.Cell;
import maze.distances.Distances;
import maze.factories.StandardCellFactory;
import maze.factories.WeightedCellFactory;
import maze.generationAlgorithms.*;

class tests{
	public static void main (String[] args) {
		prova();
		//test();
	}

	private static void prova() {
		int rows = 5;
		int cols = 5;

		Grid grid = new Grid(rows, cols);
		// GrowingTree.buildMaze(grid, l -> l.stream().min( (c1,c2) -> {
		// 	Set<Cell> links1 = c1.getLinks();
		// 	Set<Cell> links2 = c2.getLinks();
		// 	return links1.size()-links2.size();
		// } ).get());
		PrimSimplified.buildMaze(grid);
		//grid.braid(5);
		//addRandomLinkWeights(grid, 3);
		Cell start = grid.getCellAt(0, 0);
		Cell target = grid.getCellAt(rows-1, cols-1);
		
		// Distances.deadEndFilling(grid, start, target);
		grid.displayGrid();
		grid.braid(3);
		grid.displayGrid();
		// grid.displayDistances(Distances.DijkstraSimplified(start));
		// grid.displayDistances(Distances.DijkstraSimplified(start).pathTo(target));
		// Distances.deadEndFilling(grid, start, target);
		// grid.displayGrid();
		// grid = new Grid(rows, cols);
		// HuntAndKill.buildMaze(grid);
		// start = grid.getCellAt(0, 0);
		// target = grid.getCellAt(rows-1, cols-1);
		// grid.braid(6);
		// grid.displayGrid();
		// Distances.deadEndFilling(grid, start, target);
		// grid.displayGrid();
		// grid.displayDistances(Distances.DijkstraSimplified(start).pathTo(target));
		// grid.displayDistances(Distances.BFS(start,target));
		// grid.displayDistances(Distances.DijkstraSimplified(start).pathTo(target));
		// grid.displayDistances(Distances.DijkstraWeights(start));
		// grid.displayDistances(Distances.DijkstraWeights(start).pathTo(target));
		// grid.displayDistances(Distances.Dijkstra(start));
		// grid.displayDistances(Distances.Dijkstra(start).pathTo(target));
		// grid.displayColoredDistances(Distances.deadEndFilling(grid, start, target));
		// grid.displayColoredDistances(Distances.DijkstraWeights(start).pathTo(target));
		// grid.getCellAt(0,1).setWeight(1000);
		// grid.displayDistances(Distances.DijkstraLinks(start));






		// Grid g = new Grid(2, 2, new StandardCellFactory());
		// g.getCellAt(0, 0).link(g.getEast(g.getCellAt(0, 0)));
		// g.getCellAt(0, 0).link(g.getSouth(g.getCellAt(0, 0)));
		// g.getCellAt(0, 1).link(g.getSouth(g.getCellAt(0, 1)));
		// g.getCellAt(1, 0).link(g.getEast(g.getCellAt(1, 0)));
		// addRandomLinkWeights(g, 10);
		// g.displayGrid();
		// g.displayDistances(Distances.Dijkstra(g.getCellAt(0, 0)));
		// g.displayDistances(Distances.SPFA(g.getCellAt(0, 0)));
	}

	private static void addRandomLinkWeights(Grid grid, int upperbound) {
		int l = 0;
		for (Cell cell : grid) {
			for (Cell link : cell.getLinks()) {
				if(cell.getLinkWeight(link) == 1){
					cell.setLinkWeight(link, Utility.getRandomNumber(upperbound)+2);
					l++;
				}
			}
		}
		System.out.println(l);
	}

	private static void addRandomCellWeights(Grid grid, int upperbound) {
		for (Cell cell : grid) {
			cell.setWeight(Utility.getRandomNumber(upperbound)+1);
		}
	}

	private static void test(){
		Grid g = null;
		int times = 1000;
		int d = 40;
		double de = 0;
		double inter = 0;
		double pathCell = 0;
		double twist = 0;
		for(int j = 0; j < times; j++){
				g = new Grid(d, d);
				GrowingTree.buildMaze(g, l -> {
					if(Utility.randomBoolean(2)){
						return Utility.getRandomElement(l);
					}else{
						return l.get(l.size()-1);
					}
				});
				// HuntAndKill.buildMaze(g);
				// Cell start = g.getCellAt(0, 0);
				// Cell target = g.getCellAt(d-1, d-1);
				// de += g.findDeadends().size();
				// inter += g.findIntersections();
				// twist += g.findDirectionChanges(); // NON FUNZIONA
				Cell start = g.getRandomCell();
				//g.displayDistances(Distances.DijkstraSimplified(start));
				start = Distances.DijkstraSimplified(start).cells.entrySet().stream().max((e1,e2) -> Double.compare(e1.getValue(), e2.getValue())).get().getKey();
				pathCell += Distances.DijkstraSimplified(start).cells.entrySet().stream().map(e -> e.getValue()).max((n1,n2) -> Double.compare(n1, n2)).get();
				//g.displayDistances(Distances.DijkstraSimplified(start));
		}
		de = de/times;
		de = de/(d*d)*100;
		inter = inter/times;
		inter = inter/(d*d)*100;
		pathCell = pathCell/times;
		pathCell = pathCell/(d*d)*100;
		twist = twist/times;
		twist = twist/(d*d)*100;
		System.out.println("de " + de);
		System.out.println("inter " + inter);
		System.out.println("pathCell " + pathCell);
		System.out.println("twist " + twist);
		
	}
}