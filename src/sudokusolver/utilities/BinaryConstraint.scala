package sudokusolver.utilities

class BinaryConstraint(private var firstItem : Couple, private var secondItem : Couple) {
	println("Creato vincolo tra " + firstItem.printCouple() + " e " + secondItem.printCouple())
	
	def getI() = {
		this.firstItem
	}
	
	def getJ() = {
		this.secondItem
	}
	
	def printConstraint() {
		println("Vincolo tra " + firstItem.printCouple + " e " + secondItem.printCouple)
	}
	
}