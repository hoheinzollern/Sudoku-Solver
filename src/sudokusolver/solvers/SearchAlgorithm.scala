package sudokusolver.solvers

abstract class SearchAlgorithm {
	private var propagationAlgorithm : PropagationAlgorithm = null
	private var problem : sudokusolver.Sudoku = null

	def solve() : sudokusolver.utilities.Board
	
 	def execute(problem : sudokusolver.Sudoku, propagationAlgorithm : PropagationAlgorithm) : sudokusolver.utilities.Board = {
		this.problem = problem
		this.propagationAlgorithm = propagationAlgorithm
		solve
	}
}
