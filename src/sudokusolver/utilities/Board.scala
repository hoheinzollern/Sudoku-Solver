package sudokusolver.utilities

class Board {
	private var board : Array[Array[Int]] = new Array[Array[Int]](9,9)
	
	def getBoard() = board
	
	def getJavaBoard() = {
		var m = new JavaMatrix()
		for (i <- 0 until 9)
			for (j <- 0 until 9)
				m.set(i,j,board(i)(j))
		m
	}
	
	def setBoard(board: Array[Array[Int]]) {
		this.board = board
	}
	
	def setValue(x : Int, y : Int, value : Int) {
		board(x)(y) = value
	}
	
	def setNull(x : Int, y : Int) {
		board(x)(y) = 0
	}
	
	def setNull(item : Couple) {
		setNull(item.getX, item.getY)
	}
	
	def getValue(x : Int, y : Int) : Int = {
		board(x)(y)
	}
	
	def getValue(item : Couple) : Int = {
		getValue(item.getX, item.getY)
	}
	
	def getInternalArray(x : Int) = {
		board(x)
	}
	
	def setInternalArray(x : Int, value : Array[Int]) = {
		board(x) = value
	}
	
	def isNotNull(x : Int, y : Int) : Boolean = {
		board(x)(y) != 0
	}
	
	def isNotNull(item : Couple) : Boolean = {
		isNotNull(item.getX, item.getY)
	}
	
	def areEquals(xi : Int, yi : Int, xj : Int, yj : Int) : Boolean = {
		board(xi)(yi) == board(xj)(yj)
	}
	
	def areEquals(itemi : Couple, itemj : Couple) : Boolean = {
		areEquals(itemi.getX, itemi.getY, itemj.getX, itemj.getY)
	}
	
	def isConstraintSatisfied(constraint : BinaryConstraint) = {
		board(constraint.getI.getX)(constraint.getI.getY) != board(constraint.getJ.getX)(constraint.getJ.getY)
	}
}