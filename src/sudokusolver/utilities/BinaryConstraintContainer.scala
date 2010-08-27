package sudokusolver.utilities

class BinaryConstraintContainer {
	private var constraintMatrix : Array[Array[Array[BinaryConstraint]]] = new Array[Array[Array[BinaryConstraint]]](9,9,20)
	
	println("Generazione dei vincoli...")
	// For every row
	for (i <- 0 to 8) {
		println("Riga " + i)
		// For every column
		for (j <- 0 to 8) {
			// Create a Constraint with all the previous fields on the same row
			println("Primo set")
			for (z <- 0 to i-1) {
				constraintMatrix(i)(j)(z) = new BinaryConstraint(new Couple(i,j), new Couple(z, j))
			}
			// Create a Constraint with all the next fields on the same row
			println("Secondo set")
			for (z <- i+1 to 8) {
				constraintMatrix(i)(j)(z-1) = new BinaryConstraint(new Couple(i,j), new Couple(z, j))
			}
			// Create a Constraint with all the previous fields on the same column
			println("Terzo set")
			for (z <- 0 to j-1) {
				constraintMatrix(i)(j)(z+9) = new BinaryConstraint(new Couple(i,j), new Couple(i, z))
			}
			// Create a Constraint with all the next fields on the same column
			println("Quarto set")
			for (z <- j+1 to 8) {
				constraintMatrix(i)(j)(z+9-1) = new BinaryConstraint(new Couple(i,j), new Couple(i, z))
			}
			// Create a Constraint with the four remaining variables
			println("Caselle centrali")
			for (k <- 0 to 1) {
				for (h <- 0 to 1) {
					val panelFirstX = i - i%3
					val panelFirstY = j - j%3
					val nextCouple = new Couple(panelFirstX + (i+1+k)%3, panelFirstY + (j+1+h)%3)
					constraintMatrix(i)(j)(16+2*k+h) = new BinaryConstraint(new Couple(i,j), nextCouple) 
				}
			}
		}
		println("")
	}
	
	def getConstraintsOf(x : Int, y : Int) : Array[BinaryConstraint]= {
		constraintMatrix(x)(y)
	}
	
	def getConstraintsOf(item : Couple) : Array[BinaryConstraint] = {
		getConstraintsOf(item.getX, item. getY)
	}
	
	def makeDirectionalConsistencyFrom(x : Int, y : Int, domains : DomainContainer) : DomainContainer = {
		var itemConstraints = getConstraintsOf(x, y)
		var i = 0
		var coupleToCheck = new Couple(x, y)
		var domainOfTheCoupleToCheck = domains.get(coupleToCheck)
		for (i <- 0 to 19) {
			var secondCouple = itemConstraints(i).getJ
			var j = 0
			while (j < domainOfTheCoupleToCheck.cardinality) {
				var k = 0
				var allConsistent = true
				var elementToDelete = 0
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
			domains.set(domainOfTheCoupleToCheck, coupleToCheck)
		}
		domains
	}
	
	def makeDirectionalConsistencyFrom(item : Couple, domains : DomainContainer) : DomainContainer = {
		makeDirectionalConsistencyFrom(item.getX, item.getY, domains)
	}
	
	def makeArcConsistencyFrom(x : Int, y : Int, domains : DomainContainer) : DomainContainer = {
		var itemConstraints = getConstraintsOf(x, y)
		var i = 0
		var coupleToCheck = new Couple(x, y)
		var domainOfTheCoupleToCheck = domains.get(coupleToCheck)
		for (i <- 0 to 19) {
			var secondCouple = itemConstraints(i).getJ
			// From first couple to second
			var j = 0
			while (j < domainOfTheCoupleToCheck.cardinality) {
				var k = 0
				var allConsistent = true
				var elementToDelete = 0
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
				}
				if (!allConsistent) {
					domainOfTheSecondCouple.deleteValue(elementToDelete)
				}
				h = h+1
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
			println("Riga " + i)
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