package sudokusolver

/**
 * Core class: has functionality for:
 * - sudoku generation
 * - sudoku loading/saving (optional)
 * - solving a sudoku scheme
 */
object Core {
	
	/**
	 * Builds a new sudoku instance given a difficulty level
	 * 
	 * @param level describes the difficulty level of the generated game
	 * 	- 1 normal
	 *  - 2 medium
	 *  - 3 hard  
	 */
	def makeSudoku(level: Int): Sudoku = {
		// FIXME: implement
		throw new exceptions.NotImplementedException
	}
	
	/**
	 * Loads a new Sudoku from an XML file (stream?)
	 */
	def loadSudoku(fileName: String): Sudoku = {
		// FIXME: implement and investigate on the suggested procedure
		throw new exceptions.NotImplementedException
	}
	
	/**
	 * Writes a sudoku to an XML file (stream?)
	 */
	def saveSudoku(fileName: String, sudoku: Sudoku) {
		// TODO
		throw new exceptions.NotImplementedException
	}
	
	def solve(sudoku: Sudoku, solver: solvers.GenericSolver): Sudoku = {
		solver.setProblem(sudoku)
		solver.solve()
	}

}