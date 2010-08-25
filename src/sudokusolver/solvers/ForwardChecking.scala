package sudokusolver.solvers

import sudokusolver.utilities

class ForwardChecking extends PropagationAlgorithm {
	override def prop(item: utilities.Couple) = {
	  var failure = false
	  var k = item.next
	  while (k.isValid && !failure) {
	 	  revise(item, k)
	 	  if (this.domains.get(k).isEmpty) failure = true
	 	  k = k.next
	  }
	  failure
	}
}
