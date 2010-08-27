package sudokusolver.utilities

import sudokusolver.exceptions

class Couple (private var x : Int = 0, private var y : Int = 0) {
	def getX() = this.x
 
	def getY() = this.y
	
	def next() = {
		var output = new Couple()
		if (this.y < 8) {
			output.set(this.x, this.y+1)
		}
		else {
			if (this.x < 8) {
				output.set(this.x+1, 0)
			}
			else throw new exceptions.NextElementDoesntExists
		}
		output
	}
	
	def previous() = {
		var output = new Couple()
		if (this.y > 0) {
			output.set(this.x, this.y-1)
		}
		else {
			if (this.x > 0) {
				output.set(this.x-1, 8)
			}
			else throw new exceptions.PreviousElementDoesntExists
		}
		output
	}
	
	def isValid() = {
		!((x == 0) && (y == 0))
	}
	
	def isLatest() = {
		(x == 8) && (y == 8)
	}
	
	def set(x : Int, y : Int) {
		if (x >= 0 && x <= 8 && y >= 0 && y <= 8) {
			this.x = x
			this.y = y
		}
		else throw new exceptions.InvalidCouple
	}
	
	def setX(x : Int) {
		if (x >= 0 && x <= 8) this.x = x
		else throw new exceptions.InvalidCouple
	}
	
	def setY(y : Int) {
		if (y >= 0 && y <= 8) this.y = y
		else throw new exceptions.InvalidCouple
	}
	
	def equals(couple : Couple) = {
		this.x == couple.getX && this.y == couple.getY
	}
}
