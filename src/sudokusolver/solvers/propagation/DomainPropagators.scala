package sudokusolver.solvers.propagation

class DomainPropagators  {
	var propagationAlgorithm : PropagationAlgorithm = null
	
	def setPropagationAlgorithm(propagationAlgorithm : PropagationAlgorithm) {
		this.propagationAlgorithm = propagationAlgorithm
	}
	
	def forwardCheckingDomainPropagator(field : Int, domains : Array[Array[Set[Int]]]) : Array[Array[Set[Int]]] = {
		this.propagationAlgorithm.setDomains(convertToDomains(domains))
		this.propagationAlgorithm.prop(new sudokusolver.utilities.Couple(field/9, field%9))
		convertToSets(this.propagationAlgorithm.getDomains)
	}
	
	def partialLookAheadDomainPropagator(field : Int, domains : Array[Array[Set[Int]]]) : Array[Array[Set[Int]]] = {
		this.propagationAlgorithm.setDomains(convertToDomains(domains))
		this.propagationAlgorithm.prop(new sudokusolver.utilities.Couple(field/9, field%9))
		convertToSets(this.propagationAlgorithm.getDomains)
	}
	
	def macDomainPropagator(field : Int, domains : Array[Array[Set[Int]]]) : Array[Array[Set[Int]]] = {
		this.propagationAlgorithm.setDomains(convertToDomains(domains))
		this.propagationAlgorithm.prop(new sudokusolver.utilities.Couple(field/9, field%9))
		convertToSets(this.propagationAlgorithm.getDomains)
	}
	
	def convertToDomains(domains : Array[Array[Set[Int]]]) : sudokusolver.utilities.DomainContainer = {
		var outputDomain = new sudokusolver.utilities.DomainContainer
		for (i <- 0 to 8) {
			for (j <- 0 to 8) {
				var newDomain = new sudokusolver.utilities.Domain("(" + i + "," + j + ")")
				newDomain.setValues(domains(i)(j).toList)
				outputDomain.set(newDomain, i, j)
			}
		}
		outputDomain.printStatus
		outputDomain
	}
	
	def convertToSets(domains : sudokusolver.utilities.DomainContainer) : Array[Array[Set[Int]]] = {
		var outputDomain = new Array[Array[Set[Int]]](9,9)
		for (i <- 0 to 8) {
			for (j <- 0 to 8) {
				outputDomain(i)(j) = domains.get(i, j).getValues.toSet
			}
		}
		outputDomain
	}
}