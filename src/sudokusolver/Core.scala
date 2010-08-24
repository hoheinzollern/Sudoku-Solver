package sudokusolver

import java.io.FileWriter
import scala.swing.Dialog
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
				board(i)(j) = ((j + i * 6 + i / 3) % 9 + 1)
			}
		}

		// Shuffle values between rows
		for (h <- 0 to 1000) {
			// FIXME: use correct methods
			val i = (Math.random * 100).asInstanceOf[Int] % 3
			val j = (Math.random * 100).asInstanceOf[Int] % 3
			val k = (Math.random * 100).asInstanceOf[Int] % 3
			val choose = (Math.random * 100).asInstanceOf[Int] % 3
			if (choose == 0 && j != k) {
				println ("i= " + i + " j= " + j + " k= " + k)
				val swap = board(i*3 + j)
				board(i*3 + j) = board(i*3 + k)
				board(i*3 + k) = swap
			} else if (j != k) {
				println ("i= " + i + " j= " + j + " k= " + k)
				for (l <- 0 to 8) {
					val swap = board(l)(i*3 + j)
					board(l)(i*3 + j) = board(l)(i*3 + k)
					board(l)(i*3 + k) = swap
				}
			}
		}

		var sudoku = new Sudoku
		sudoku.setBoard(board)
		if (sudoku.checkConstraints == false) {
			Dialog.showMessage(null, "Constraint verification failed", "Error", Dialog.Message.Error)
		}
		return sudoku
	}
	
	/**
	 * Loads a new Sudoku from a sudoku file (represented as a sequence of 
	 * digits in range [0-9])
	 * 
	 * @param fileName the name of the file
	 * @param line the line from which you choose to get the sudoku
	 */
	def loadSudoku(fileName: String, line: Int = 0): Sudoku = {
		val source = scala.io.Source.fromFile(fileName)
		val line = source.getLine(0)
		source.close
		
		var chars = new Array[Char](9*9)
		line.getChars(0, 9, chars, 0)
		val board = new Array[Array[Int]](9,9)
		for (i <- 0 to 8) {
			for (j <- 0 to 8) {
				board(i)(j) = Character.digit(chars(i*9 + j), 10)
			}
		}
		val sudoku = new Sudoku
		sudoku.setBoard(board)
		return sudoku
	}
	
	/**
	 * Writes a sudoku to an XML file (stream?)
	 */
	def saveSudoku(fileName: String, sudoku: Sudoku) {
		val board = sudoku.getBoard
		val out = new FileWriter(fileName)
		var chars = new Array[Char](9*9)
		for (i <- 0 to 8) {
			for (j <- 0 to 8) {
				Integer.toString(board(i)(j)).getChars(0, 1, chars, i * 9 + j)
			}
		}
		out.write(chars)
		out.close
	}
	
	def solve(sudoku: Sudoku, solver: solvers.GenericSolver): Sudoku = {
		solver.setProblem(sudoku)
		solver.solve()
	}

}