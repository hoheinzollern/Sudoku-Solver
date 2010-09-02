package sudokusolver

import java.io.FileWriter
import java.util.Random
import scala.swing.Dialog
/**
 * Core class: has functionality for:
 * - sudoku generation
 * - sudoku loading/saving (optional)
 * - solving a sudoku scheme
 */
object Core {
	private var constraints = new utilities.BinaryConstraintContainer
	private var sudoku : Sudoku = null
	private var logger : Logger = null
	//constraints.checkConstraintMatrix()

	def getConstraintMatrix() = constraints
	
	/**
	 * Builds a new sudoku instance given a difficulty level
	 * 
	 * @param level describes the difficulty level of the generated game
	 * 	- 1 normal
	 *  - 2 medium
	 *  - 3 hard  
	 */
	def makeSudoku(level: Int) {
		var time = System.currentTimeMillis
		var board = Dance.randomSudoku
		var time2 = System.currentTimeMillis
		var bb = new utilities.Board
		bb.setBoard(board)
		sudoku.setBoard(bb)
	}
	
	def setSudoku(sudoku: Sudoku) {
		this.sudoku = sudoku
	}
	
	def getSudoku = sudoku
	
	def setLogger(logger: Logger) {
		this.logger = logger
	}
	
	def log(message: String) {
		logger.log(message)
	}
	
	/**
	 * Naive algorithm for the construction of sudoku boards, use it for testing purposes.
	 */
	def makeSudokuNaive(level: Int): Sudoku = {
		val random = new Random
		var board = new utilities.Board
		// Initialize board to correct values
		for (i <- 0 to 8) {
			for (j <- 0 to 8) {
				board.setValue(i, j, ((j + i * 6 + i / 3) % 9 + 1))
			}
		}

		// Shuffle values between rows
		for (h <- 0 to 1000) {
			val i = random.nextInt(3)
			val j = random.nextInt(3)
			val k = random.nextInt(3)
			val choose = random.nextInt(2)
			if (choose == 0 && j != k) {
				val swap = board.getInternalArray(i*3 + j)
				board.setInternalArray((i*3 + j), board.getInternalArray(i*3 + k))
				board.setInternalArray((i*3 + k), swap)
			} else if (j != k) {
				for (l <- 0 to 8) {
					val swap = board.getValue(l, (i*3 + j))
					board.setValue(l, (i*3 + j), board.getValue(l, (i*3 + k)))
					board.setValue(l, (i*3 + k), swap)
				}
			}
		}

		var sudoku = new Sudoku(getConstraintMatrix)
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
		val board = new utilities.Board
		for (i <- 0 to 8) {
			for (j <- 0 to 8) {
				board.setValue(i, j, Character.digit(chars(i*9 + j), 10))
			}
		}
		val sudoku = new Sudoku(getConstraintMatrix)
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
				Integer.toString(board.getValue(i, j)).getChars(0, 1, chars, i * 9 + j)
			}
		}
		out.write(chars)
		out.close
	}
	
	var elapsedTime: Long = 0
	var visitedNodes: Int = 0
	
	def startSolver(searchCode : Int, propagationCode: Int) {
		var solver = searchCode match {
			case 0 => 
			propagationCode match {
				case 0 => new sudokusolver.solvers.ForwardCheckingOnly(sudoku)
				case 1 => new sudokusolver.solvers.PartialLookAheadOnly(sudoku)
				case 2 => new sudokusolver.solvers.MacOnly(sudoku)
				case _ => throw new exceptions.CommandNotFoundException
			}
			case 1 => 
			propagationCode match {
				case 0 => new sudokusolver.solvers.BacktrackingSearchOnly(sudoku) 
				case 1 => new sudokusolver.solvers.BacktrackingSearchAndForwardChecking(sudoku)
				case 2 => new sudokusolver.solvers.BacktrackingSearchAndPartialLookAhead(sudoku)
				case 3 => new sudokusolver.solvers.BacktrackingSearchAndMac(sudoku)
				case _ => throw new exceptions.CommandNotFoundException
			}
			case 2 => new sudokusolver.solvers.DancingLinks(sudoku)
			case _ => throw new exceptions.CommandNotFoundException
		}
    	solver.start
    	elapsedTime = solver.getTimeElapsed
    	visitedNodes = solver.getVisitCount
	}
}