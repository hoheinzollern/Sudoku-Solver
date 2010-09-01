package sudokusolver.solvers

abstract class SearchAlgorithm {
	private var propagationAlgorithm : PropagationAlgorithm = null
	private var problem : sudokusolver.Sudoku = null

	def getPropagationAlgorithm() = propagationAlgorithm
	
	def getProblem() = problem
	
	def prop(item : sudokusolver.utilities.Couple) : Boolean = {
		this.propagationAlgorithm.prop(item)
	}
	
	/**
	 * Specific for every search algorithm
	 * @return
	 */
	def solve() : sudokusolver.Sudoku
	
	/**
	 * Used globally
	 * 
	 * @param problem
	 * @param propagationAlgorithm
	 * @return
	 */
 	def execute(problem : sudokusolver.Sudoku, propagationAlgorithm : PropagationAlgorithm) : sudokusolver.Sudoku = {
		this.problem = problem
		this.propagationAlgorithm = propagationAlgorithm
		solve
	}
}
