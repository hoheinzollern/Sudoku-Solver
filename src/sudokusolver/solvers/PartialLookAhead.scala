package sudokusolver.solvers

import sudokusolver.utilities

class PartialLookAhead extends PropagationAlgorithm {	
	override def prop(item: utilities.Couple) = {
	  var failure = false
	  var k = item.next
	  while (k.isValid && !failure) {
	 	  revise(item, k)
	 	  if (this.domains.get(k).isEmpty) failure = true
	 	  k = k.next
	  }
	  if (!failure) {
	 	  failure = darc(item.next)
	  }
	  failure
	}
	
	def darc(item : utilities.Couple) = {
		var failure = false
		var i = item
		while (!i.isLatest) {
			this.domains = this.problem.getConstraints.makeDirectionalConsistencyFrom(i, this.domains)
			if (this.domains.checkEmptyDomains) failure = true
			i = i.next
		}
		failure
	}
}
