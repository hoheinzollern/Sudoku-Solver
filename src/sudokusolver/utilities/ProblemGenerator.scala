
package sudokusolver.utilities

object ProblemGenerator {
	
	def buildProblem(level: Int): sudokusolver.Sudoku = {
		return new sudokusolver.Sudoku(sudokusolver.Core.getConstraintMatrix)
	}

}