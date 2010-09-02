package sudokusolver.solvers.propagation

import sudokusolver.utilities

class PartialLookAhead extends PropagationAlgorithm {	
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
	  			failure = darc(item.next)
	  		}
	  	}
	  	failure
	}
	
	def darc(item : utilities.Couple) = {
		var failure = false
		var i = item
		while (!i.isLatest && !failure) {
			if (!this.board.isNotNull(i)) {
				this.domains = this.problem.getConstraints.makeDirectionalConsistencyFrom(i, this.domains)
				if (this.domains.checkEmptyDomains) failure = true
			}
			i = i.next
		}
		failure
	}
}
