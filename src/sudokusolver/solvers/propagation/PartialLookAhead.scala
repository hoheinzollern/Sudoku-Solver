package sudokusolver.solvers

import sudokusolver.utilities

class PartialLookAhead extends PropagationAlgorithm {	
	override def prop(item: utilities.Couple) = {
		println("Partial lookAhead iniziato!")
		var failure = false
	  	if (!item.isLatest) {
	  		var stop = false
	  		var k = item.next
	  		while (!stop && !failure) {
	  			println("ciclo con k = " + k.printCouple)
	  			revise(item, k)
	  			if (this.domains.get(k).isEmpty) failure = true
	  			if (!k.isLatest) k = k.next
	  			else stop = true
	  		}
	  		println("Finite le revise con failure = " + failure)
	  		if (!failure) {
	  			println("Parte l'algoritmo darc")
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
				println("Darc con item = " + item.printCouple)
				this.domains = this.problem.getConstraints.makeDirectionalConsistencyFrom(i, this.domains)
				println("Verifico se ci sono domini vuoti...")
				if (this.domains.checkEmptyDomains) failure = true
				println("Failure vale " + failure)
			}
			else println("La casella è già piena... non faccio darc!")
			i = i.next
		}
		failure
	}
}
