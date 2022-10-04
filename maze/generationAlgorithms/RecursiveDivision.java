package maze.generationAlgorithms;

import maze.Grid;
import maze.Utility;
import maze.cells.Cell;

public class RecursiveDivision {
	
	public static void buildMaze(Grid grid){
		for (Cell cell : grid) {//si potrebbe non iterare su tutte le celle dato che il metodo link effettua un link reciproco
			for (Cell neighbour : cell.getNeighbours()) {
				cell.link(neighbour);
			}
		}

		divide(0,0,grid.getRows(),grid.getCols(), grid);
	}

	private static void divide(int row, int col, int height, int width, Grid grid) {
		if(height <= 1 || width <=1){// se l'area è troppo piccola allora non si puo divedere più
			
		}else{
			if(height > width){ //leggi a pagina 209 perche si divide in questo modo e non a random
				divide_horizontally(row,col,height,width, grid);
			}else{
				divide_vertically(row,col,height,width, grid);
			}
		}
	}

	private static void divide_horizontally(int row, int col, int height, int width, Grid grid) {
		int divideSouthOf = Utility.getRandomNumber(height-1);
		int passageAt = Utility.getRandomNumber(width);

		for(int i = 0; i < width; i++){
			if (passageAt != i){
				Cell cell = grid.getGrid()[row+divideSouthOf][col+i];
				cell.unlink(cell.getSouth());
			}
		}
		divide(row, col, divideSouthOf+1, width, grid);
		divide(row+divideSouthOf+1, col, height-divideSouthOf-1, width, grid);
	}

	private static void divide_vertically(int row, int col, int height, int width, Grid grid) {
		int divideEastOf = Utility.getRandomNumber(width-1);
		int passageAt = Utility.getRandomNumber(height);

		for(int i = 0; i < height; i++){
			if (passageAt != i){
				Cell cell = grid.getGrid()[row+i][col+divideEastOf];
				cell.unlink(cell.getEast());
			}
		}
		divide(row, col, height, divideEastOf+1, grid);
		divide(row, col+divideEastOf+1, height, width-divideEastOf-1, grid);
	}
}
