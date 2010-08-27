package sudokusolver.solvers

import sudokusolver.utilities

/**
 * This class must provide the basic support to the family
 * of the Arc Consistency algorithms.
 * 
 * Mac means Mantain Arc Consistency.
 */

class Mac extends PropagationAlgorithm {
	override def prop(item: utilities.Couple) = {
	  var failure = false
	  var k = item.next
	  while (k.isValid && !failure) {
	 	  revise(item, k)
	 	  if (this.domains.get(k).isEmpty) failure = true
	 	  k = k.next
	  }
	  if (!failure) {
	 	  failure = arc(item.next)
	  }
	  failure
	}
	
	def arc(item : utilities.Couple) = {
		var failure = false
		var i = item
		while (!i.isLatest) {
			this.domains = this.problem.getConstraints.makeArcConsistencyFrom(i, this.domains)
			if (this.domains.checkEmptyDomains) failure = true
			i = i.next
		}
		failure
	}
}
