package sudokusolver.solvers

import sudokusolver.utilities

abstract class PropagationAlgorithm {
	private val domains : Array[utilities.Domain] = new Array[utilities.Domain](81)
 
	/**
	* This is the "revise" method that is the basic idea of arc consistency.
	* 
	* The idea is to check if between every couple of variables bounded by a
	* constraint, for every value from the first variable exists almost 
	* one value from the second variable that doesn't brake the constraint. 
	*/
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
 
 	/**
 	* This is the main method of the propagation mechanism.
 	* 
 	* The idea is to get the variable to load and the universe of the domains.
 	*/
	def prop(itemNumber: Int, domains: Array[utilities.Domain]) {
	  //TODO
	}
 
 	/**
 	* This return the specific domain of a variable 
 	*/
 	def getDomain(index : Int) = {
 	  this.domains(index)
 	}
}
