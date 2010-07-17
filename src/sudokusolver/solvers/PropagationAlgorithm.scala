package sudokusolver.solvers

import sudokusolver.utilities

class PropagationAlgorithm {
	private val domains : Array[utilities.Domain] = new Array[utilities.Domain](81)
 
	def prop(itemNumber: Int, domains: Array[utilities.Domain]) {
	  //TODO
	}
 
 	def getDomain(index : Int) = {
 	  this.domains(index)
 	}
}
