package sudokusolver.solvers

abstract class GenericSolver {
	protected var searchAlgorithm : SearchAlgorithm
	protected var propagationAlgorithm : PropagationAlgorithm
 
	def setSearchAlgorithm(searchAlgorithm : SearchAlgorithm) {
	  this.searchAlgorithm = searchAlgorithm
	}
 
	def setPropagationAlgorithm(propagationAlgorithm : PropagationAlgorithm) {
	  this.propagationAlgorithm = propagationAlgorithm
	}
 
	def solve() {
	  //TODO
	}
}
