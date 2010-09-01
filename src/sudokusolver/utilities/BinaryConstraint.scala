package sudokusolver.utilities

class BinaryConstraint(private var firstItem : Couple, private var secondItem : Couple) {
	def getI() = {
		this.firstItem
	}
	
	def getJ() = {
		this.secondItem
	}
	
	def printConstraint() {
		println("Constraint between " + firstItem.printCouple + " and " + secondItem.printCouple)
	}
	
}