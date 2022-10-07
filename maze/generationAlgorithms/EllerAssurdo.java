// package maze.generationAlgorithms;

// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
// import java.util.Objects;
// import java.util.Set;

// import maze.Grid;
// import maze.Utility;
// import maze.cells.Cell;

// public class EllerAssurdo {
	
// 	static class SetManager {
// 		Map<Integer,Set<Cell>> colToSet = new HashMap<>();
// 		Map<Cell,Integer> cellToSet = new HashMap<>();
// 		int nextSet = 0;

// 		public SetManager(){

// 		}

// 		public int getNextSet(){
// 			return nextSet++;
// 		}
// 	}
// 	public static void buildMaze(Grid grid){
// 		SetManager sup = new SetManager();
// 		int currentRow = 0;
// 		boolean lastRow = false;

// 		for (Cell[] cells : grid.getGrid()) {
// 			lastRow = currentRow == grid.getRows()-1;
// 			setupRow(currentRow, sup, grid);
// 			if (!lastRow){
// 				for (Cell cell : cells) {
// 					//randomMerge();
// 					if(!Objects.isNull(grid.getWest(cell))){
// 						boolean shouldLink = Utility.randomBoolean(2) && (sup.cellToSet.get(cell) != sup.cellToSet.get(grid.getWest(cell)));

// 						if(shouldLink){
// 							int winner = sup.cellToSet.get(grid.getWest(cell));
// 							cell.link(grid.getWest(cell));
							
// 							sup.cellToSet.replace(cell, winner);
// 						}
// 					}
// 				}
// 				//carveSouth();
// 				for (var entry : sup.colToSet.entrySet()) {
// 					int r = currentRow;
// 					boolean carved = false;
// 					// entry.getValue().removeIf(c -> c.getRow() != r);
// 					// if(entry.getValue().isEmpty()){
// 					// 	sup.colToSet.remove(entry.getKey());
// 					// }
// 					for (Cell cell : entry.getValue().stream().filter(c -> c.getRow() == r).toList()) {
// 						if(Utility.randomBoolean(3)){
// 							try{
// 								cell.link(grid.getSouth(cell));
// 							}catch(Exception e){
// 								System.out.println("sosu");
// 							}
// 							sup.cellToSet.put(grid.getSouth(cell), entry.getKey());
// 							sup.colToSet.get(entry.getKey()).add(grid.getSouth(cell));
// 							carved = true;
// 						}
// 					}
// 					if(!carved){
// 						List<Cell> pool = entry.getValue().stream().filter(c -> c.getRow() == r).toList();
// 						if(!pool.isEmpty()){
// 							Cell toCarve = Utility.getRandomElement(pool);
// 							toCarve.link(grid.getSouth(toCarve));
// 							sup.cellToSet.put(grid.getSouth(toCarve), entry.getKey());
// 							sup.colToSet.get(entry.getKey()).add(grid.getSouth(toCarve));
// 						}
// 					}
// 				}
// 			}else{
// 				grid.displayGrid();
// 			}
// 			currentRow++;
// 		}
// 	}

// 	private static void setupRow(int row, SetManager sup, Grid grid) {
// 		Cell[] selectedRow = grid.getGrid()[row];
// 		for (Cell cell : selectedRow) {
// 			if(Objects.isNull(sup.cellToSet.get(cell))){
// 				sup.cellToSet.put(cell, sup.getNextSet());
// 			}
// 		}
// 	}
		

// }

