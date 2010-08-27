package sudokusolver.utilities

class BinaryConstraint(private var firstItem : Couple, private var secondItem : Couple) {
	def getI() = {
		this.firstItem
	}
	
	def getJ() = {
		this.secondItem
	}
	
}