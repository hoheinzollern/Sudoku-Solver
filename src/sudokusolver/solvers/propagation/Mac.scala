package sudokusolver.solvers.propagation

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
		if (!item.isLatest) {
			var stop = false
	  		var k = item.next
			while (!stop && !failure) {
				revise(item, k)
				if (this.domains.get(k).isEmpty) failure = true
				if (!k.isLatest) k = k.next
				else stop = true
			}
			if (!failure) {
				failure = arc(item.next)
			}
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
