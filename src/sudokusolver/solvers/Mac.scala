package sudokusolver.solvers

import sudokusolver.utilities

class Mac extends PropagationAlgorithm {
	def revise(xi : utilities.Couple, xj : utilities.Couple) {
	  var domainXi = getDomain(xi.convertCouple())
	  var domainXj = getDomain(xj.convertCouple())
   
	  // Costraint is all_different
   
	  
	  List(domainXi, domainXj)
	}
}
