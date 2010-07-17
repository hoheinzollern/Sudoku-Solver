package sudokusolver.solvers

import sudokusolver.utilities

class Mac extends PropagationAlgorithm {
	def revise(xi : utilities.Couple, xj : utilities.Couple) = {
	  var domainXi = getDomain(xi.convertCouple())
	  var domainXj = getDomain(xj.convertCouple())
   
	  // Costraint is all_different
	  var listDomainXi = domainXi.getValues()
	  var listDomainXj = domainXj.getValues()
   
	  var i = 0
	  while(listDomainXi.length<i) {
	    var xiElement = (listDomainXi.drop(i)).head
	    var j = 0
     	var stopCycle = false
	    while (!stopCycle && listDomainXj.length<j) {
	      var xjElement = (listDomainXi.drop(j)).head
	      if (xjElement != xiElement) stopCycle = true
	      j = j+1
	    }
	    if (stopCycle != true) domainXi.deleteValue(xiElement)
	    i=i+1
	  }
	}
}
