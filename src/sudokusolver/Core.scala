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
		var board = new Array[Array[Int]](9, 9)
		// Initialize board to correct values
		for (i <- 0 to 8) {
			for (j <- 0 to 8) {
				board(i)(j) = ((j + i * 3 + i / 3) % 9 + 1)
			}
		}
		// Shuffle values between rows and columns
		for (h <- 0 to 8) {
			// FIXME: use correct methods
			val i = (Math.random * 100).asInstanceOf[Int] % 3
			val j = (Math.random * 100).asInstanceOf[Int] % 3
			val k = (Math.random * 100).asInstanceOf[Int] % 3
			if (i != j) {
				println ("i= " + i + " j= " + j + " k= " + k)
				val swap = board(i + j*3)
				board(i + j*3) = board(i + k*3)
				board(i + k*3) = swap		
			}
		}
		var sudoku = new Sudoku
		sudoku.setBoard(board)
		return sudoku
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