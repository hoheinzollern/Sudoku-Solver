package sudokusolver.solvers

import sudokusolver.utilities

class ForwardChecking extends PropagationAlgorithm {
	override def prop(item: utilities.Couple) = {
		var failure = false
		if (!item.isLatest) {
			println("Forward checking con item " + item.printCouple)
			var stop = false
			var k = item.next
			while (!stop && !failure) {
				revise(item, k)
				if (this.domains.get(k).isEmpty) failure = true
				if (!k.isLatest) k = k.next
				else stop = true
			}
		}
		failure
	}
}
