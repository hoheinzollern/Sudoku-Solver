package sudokusolver.utilities

class Couple (private var x : Int = 0, private var y : Int = 0) {
	
	def getX() = this.x
 
	def getY() = this.y
	
	def next() = {
		var output = new Couple()
		if (x != 9 && y != 9) {
			if (y < 9) {
				output = new Couple(x, y+1)
			}
			else output = new Couple(x+1, 1)
		}
		output
	}
	
	def isValid() = {
		!(x == y == 0)
	}
}
