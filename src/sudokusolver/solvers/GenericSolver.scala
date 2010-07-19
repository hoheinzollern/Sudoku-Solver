package sudokusolver.solvers

/**
 * This abstract class represents the structure of a generic solver.
 * The subject of this class is to create the basic framework to obtain 
 * a complete solver with any possible kind of search / propagation methods
 */

abstract class GenericSolver {
	protected var searchAlgorithm : SearchAlgorithm = null
	protected var propagationAlgorithm : PropagationAlgorithm = null
 
	def setSearchAlgorithm(searchAlgorithm : SearchAlgorithm) {
	  this.searchAlgorithm = searchAlgorithm
	}
 
	def setPropagationAlgorithm(propagationAlgorithm : PropagationAlgorithm) {
	  this.propagationAlgorithm = propagationAlgorithm
	}
	
	/**
	* This method starts the selected algorithms and returns the solution as
	* a stepList 
	*/
	def solve() {
	  if (this.searchAlgorithm != null) {
	    
	  }
	  // TODO
	}
}
