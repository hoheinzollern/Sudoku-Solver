package sudokusolver.solvers

abstract class SearchAlgorithm {
	private var usePropagation : Boolean = false
	private var propagationAlgorithm : PropagationAlgorithm = null
 
	private def setPropagationAlgorithm(propagationAlgorithm : PropagationAlgorithm) {
	  this.usePropagation = true
	  this.propagationAlgorithm = propagationAlgorithm
	}
	
 	def execute() {
 	  
 	}
  
 	def execute(propagationAlgorithm : PropagationAlgorithm) {
 	  setPropagationAlgorithm(propagationAlgorithm)
 	}
	//TODO
}
