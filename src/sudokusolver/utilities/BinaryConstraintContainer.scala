package sudokusolver.utilities

class BinaryConstraintContainer {
	private var constraintMatrix : Array[Array[Array[BinaryConstraint]]] = new Array[Array[Array[BinaryConstraint]]](9,9,20)
	
	// For every row
	for (i <- 0 to 8) {
		// For every column
		for (j <- 0 to 8) {
			// Create a Constraint with all the previous fields on the same row
			for (z <- 0 to i-1) {
				constraintMatrix(i)(j)(z) = new BinaryConstraint(new Couple(i,j), new Couple(z, j))
			}
			// Create a Constraint with all the next fields on the same row
			for (z <- i+1 to 8) {
				constraintMatrix(i)(j)(z-1) = new BinaryConstraint(new Couple(i,j), new Couple(z, j))
			}
			// Create a Constraint with all the previous fields on the same column
			for (z <- 0 to j-1) {
				constraintMatrix(i)(j)(z+8) = new BinaryConstraint(new Couple(i,j), new Couple(i, z))
			}
			// Create a Constraint with all the next fields on the same column
			for (z <- j+1 to 8) {
				constraintMatrix(i)(j)(z+8-1) = new BinaryConstraint(new Couple(i,j), new Couple(i, z))
			}
			// Create a Constraint with the four remaining variables
			val panelFirstX = i - i%3
			val panelFirstY = j - j%3
			for (k <- 0 to 1) {
				for (h <- 0 to 1) {
					val nextCouple = new Couple(panelFirstX + (i+1+k)%3, panelFirstY + (j+1+h)%3)
					constraintMatrix(i)(j)(16+2*k+h) = new BinaryConstraint(new Couple(i,j), nextCouple) 
				}
			}
		}
	}
	
	def getConstraintsOf(x : Int, y : Int) : Array[BinaryConstraint]= {
		constraintMatrix(x)(y)
	}
	
	def getConstraintsOf(item : Couple) : Array[BinaryConstraint] = {
		getConstraintsOf(item.getX, item. getY)
	}
	
	def makeDirectionalConsistencyFrom(x : Int, y : Int, domains : DomainContainer) : DomainContainer = {
		println("Genera consistenza direzionale a partire da (" + x + "," + y + ")")
		var itemConstraints = getConstraintsOf(x, y)
		var coupleToCheck = new Couple(x, y)
		var domainOfTheCoupleToCheck = domains.get(coupleToCheck)
		println("Ho caricato le caratteristiche...")
		println("Elenco vincoli:")
		for (i <- 0 to 19) {
			itemConstraints(i).printConstraint
		}
		println("Dominio della cella:")
		println(domainOfTheCoupleToCheck.getStatus)
		for (i <- 0 to 19) {
			println("Calcolo la consistenza con il vincolo = " + i)
			var secondCouple = itemConstraints(i).getJ
			var j = 0
			println("La seconda casella è la " + secondCouple.printCouple)
			println("Il dominio della prima casella ha " + domainOfTheCoupleToCheck.cardinality + " elementi")
			while (j < domainOfTheCoupleToCheck.cardinality) {
				println("Ciclo sugli elementi del primo field: j = " + j)
				var k = 0
				var allConsistent = true
				var elementToDelete = -1
				println("Verifico per tutti gli elementi della seconda casella... che sono " + domains.get(secondCouple).cardinality)
				while (k < domains.get(secondCouple).cardinality && allConsistent) {
					println("Ciclo interno con k = " + k)
					if (domainOfTheCoupleToCheck.getValue(j) == domains.get(secondCouple).getValue(k)) {
						allConsistent = false
						elementToDelete = domainOfTheCoupleToCheck.getValue(j) 
					}
					k = k + 1
				}
				if (!allConsistent) {
					domainOfTheCoupleToCheck.deleteValue(elementToDelete)
				}
				j = j+1
			}
			domains.set(domainOfTheCoupleToCheck, coupleToCheck)
		}
		domains
	}
	
	def makeDirectionalConsistencyFrom(item : Couple, domains : DomainContainer) : DomainContainer = {
		makeDirectionalConsistencyFrom(item.getX, item.getY, domains)
	}
	
	def makeArcConsistencyFrom(x : Int, y : Int, domains : DomainContainer) : DomainContainer = {
		var itemConstraints = getConstraintsOf(x, y)
		var coupleToCheck = new Couple(x, y)
		var domainOfTheCoupleToCheck = domains.get(coupleToCheck)
		for (i <- 0 to 19) {
			var secondCouple = itemConstraints(i).getJ
			// From first couple to second
			var j = 0
			while (j < domainOfTheCoupleToCheck.cardinality) {
				var k = 0
				var allConsistent = true
				var elementToDelete = -1
				while (k < domains.get(secondCouple).cardinality && allConsistent) {
					if (domainOfTheCoupleToCheck.getValue(j) == domains.get(secondCouple).getValue(k)) {
						allConsistent = false
						elementToDelete = domainOfTheCoupleToCheck.getValue(j) 
					}
				}
				if (!allConsistent) {
					domainOfTheCoupleToCheck.deleteValue(elementToDelete)
				}
				j = j+1
			}
			// From second couple to first
			var h = 0
			var domainOfTheSecondCouple = domains.get(secondCouple)
			while (h < domainOfTheSecondCouple.cardinality) {
				var k = 0
				var allConsistent = true
				var elementToDelete = 0
				while (k < domainOfTheCoupleToCheck.cardinality && allConsistent) {
					if (domainOfTheSecondCouple.getValue(h) == domainOfTheCoupleToCheck.getValue(k)) {
						allConsistent = false
						elementToDelete = domainOfTheSecondCouple.getValue(j) 
					}
					k = k + 1
				}
				if (!allConsistent) {
					domainOfTheSecondCouple.deleteValue(elementToDelete)
				}
				h = h + 1
			}
			domains.set(domainOfTheCoupleToCheck, coupleToCheck)
			domains.set(domainOfTheSecondCouple, secondCouple)
		}
		domains
	}
	
	def makeArcConsistencyFrom(item : Couple, domains : DomainContainer) : DomainContainer = {
		makeArcConsistencyFrom(item.getX, item.getY, domains)
	}
	
	def checkConstraintMatrix() {
		println("Verifico la corretta generazione dei vincoli")
		for (i <- 0 to 8) {
			for (j <- 0 to 8) {
				println("La casella (" + i + "," + j + ") ha i seguenti vincoli:")
				for (z <- 0 to 19) {
					this.constraintMatrix(i)(j)(z).printConstraint
				}
			}
			println("")
		}
	}
}