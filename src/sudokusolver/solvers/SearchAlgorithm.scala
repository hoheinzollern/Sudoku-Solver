package sudokusolver.solvers

abstract class SearchAlgorithm {
	private var usePropagation : Boolean = false
	private var propagationAlgorithm : PropagationAlgorithm = null
 
	def setPropagationAlgorithm(propagationAlgorithm : PropagationAlgorithm) {
	  this.usePropagation = true
	  this.propagationAlgorithm = propagationAlgorithm
	}
	
	//TODO
}
