package sudokusolver.solvers

import sudokusolver.utilities

class ForwardChecking extends PropagationAlgorithm {
	def prop(item: utilities.Couple, domains: Array[Array[utilities.Domain]]) {
	  this.domains = domains
	  var failure = false
	  var k = item.next
	  while (k.isValid && !failure) {
	 	  revise(item, k)
	 	  if (this.domains(k.getX)(k.getY).isEmpty) failure = true
	 	  k = k.next
	  }
	  failure
	}
}
