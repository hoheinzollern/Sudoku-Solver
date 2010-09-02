package sudokusolver.solvers

abstract class SearchAlgorithm {
	private var propagationAlgorithm : PropagationAlgorithm = null
	private var problem : sudokusolver.Sudoku = null
	private var visitCount = 0
	private var start: Long = 0
	private var end: Long = 0
	
	def nodeVisited {
		visitCount += 1
	}
	
	def getVisitCount = visitCount
	
	def getTimeElapsed = end-start

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
		start = System.currentTimeMillis
		this.problem = problem
		this.propagationAlgorithm = propagationAlgorithm
		var result = solve
		end = System.currentTimeMillis
		result
	}
}
