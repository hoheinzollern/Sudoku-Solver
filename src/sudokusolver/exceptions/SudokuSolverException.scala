package sudokusolver.exceptions

class SudokuSolverException extends Exception {
	private var message : String = ""
 
	protected def setSudokuSolverMessage(message : String) {
	  this.message = message
	}
 
 	def getSudokuSolverMessage() = {
 	  this.message
 	}
}
