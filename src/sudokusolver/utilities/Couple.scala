package sudokusolver.utilities

class Couple {
	private var x : Int = 0
	private var y : Int = 0
 
	def convertCouple() = {
	  9*(this.y-1)+(this.x-1)
	}
}
