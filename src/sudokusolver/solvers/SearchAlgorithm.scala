package sudokusolver.solvers

abstract class SearchAlgorithm {
	private var usePropagation : Boolean = false
	private var propagationAlgorithm : PropagationAlgorithm = null
	private var problem : sudokusolver.Sudoku = null
 
	private def setProblem(problem : sudokusolver.Sudoku) {
		this.problem = problem
	}
	
	private def setPropagationAlgorithm(propagationAlgorithm : PropagationAlgorithm) {
	  this.usePropagation = true
	  this.propagationAlgorithm = propagationAlgorithm
	  this.propagationAlgorithm.setProblem(this.problem)
	}

 	def execute

 	def execute(problem : sudokusolver.Sudoku, propagationAlgorithm : PropagationAlgorithm) {
 	  setProblem(problem)
 	  setPropagationAlgorithm(propagationAlgorithm)
 	  execute
 	}
}
