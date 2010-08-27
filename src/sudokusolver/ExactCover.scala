package sudokusolver

import java.util.Random

class Node (var header: ColumnHeader,
		var left: Node = null, var up: Node = null,
		var info: Int = 0) {
	
	var right: Node = this
	var down: Node = this
	
	if (header.first == null)
		header.first = this
	header.length += 1
	if (left != null)
		left.right = this
	if (up != null)
		up.down = this
	
	def cover {
		if (left != null)
			left.right = right
		if (right != null)
			right.left = left
		if (up != null)
			up.down = down
		if (down != null)
			down.up = up
		header.length -= 1
	}
	
	def uncover { // Needed for backtracking purposes, not for sudoku generation
		if (left != null)
			left.right = this
		if (right != null)
			right.left = this
		if (up != null)
			up.down = this
		if (down != null)
			down.up = this
		header.length += 1
	}
}

class ColumnHeader (var info: Int, var left: ColumnHeader) {
	var first: Node = null
	var right: ColumnHeader = null
	var length: Int = 0
	
	if (left != null)
		left.right = this
		
	def cover {
		if (left != null)
			left.right = right
		if (right != null)
			right.left = left
	}
}

object ExactCover {
	def getMCol(t: Int, row: Int, col: Int) = t * 81 + row * 9 + col 
	def getMRow(num: Int, row: Int, col: Int) = ((num-1) * 81 + row * 9 + col)
	
	def getType(mcol: Int) = mcol / 81
	
	def getNum(mrow: Int) = mrow / 81 + 1
	
	def getRow(mrow: Int) = (mrow / 9) % 9
	def getCol(mrow: Int) = mrow % 9

	def makeSudoku: Sudoku = {
		val rand = new Random
		var header = new Array[ColumnHeader](324)
		var matrix = new Array[Array[Node]](730, 324)
		// Build header and matrix
		var leftHeader: ColumnHeader = null
		for (i <- 0 to 323) {
			leftHeader = new ColumnHeader(i, leftHeader)
			header(i) = leftHeader
		}
		leftHeader = header(0)
		var up = new Array[Node](324)
		var left = new Array[Node](730)
		for (n <- 1 to 9) {
			for (i <- 0 to 8) {
				for (j <- 0 to 8) {
					val row = ExactCover.getMRow(n, i, j)
					val col1 = ExactCover.getMCol(0, i, j)
					val col2 = ExactCover.getMCol(1, i, j)
					val col3 = ExactCover.getMCol(2, i, j)
					val col4 = ExactCover.getMCol(3, i, j)
					
					val node1 = new Node(header(col1), null, up(col1), row)
					val node2 = new Node(header(col2), node1, up(col2), row)
					val node3 = new Node(header(col3), node2, up(col3), row)
					val node4 = new Node(header(col4), node3, up(col4), row)
					
					left(row) = node1
					matrix(row)(col1) = node1
					matrix(row)(col2) = node2
					matrix(row)(col3) = node3
					matrix(row)(col4) = node4
				}
			}
		}
		var currentHeader = leftHeader 
		while (leftHeader != null) {
			// Chose the column with the least number of rows set to 1
			var nextHeader = leftHeader
			while (nextHeader != null) {
				if (currentHeader.length > nextHeader.length)
					currentHeader = nextHeader
				nextHeader = nextHeader.right
			}
			val length = currentHeader.length
			// Chose the row to be kept
			val row = rand.nextInt(length)
			var node = currentHeader.first
			for (j <- 0 to length-1) {
				if (j != row) {
					// Cover the entire row
					var x = node.left
					while (x != null) {
						x.cover
						if (x.header.length == 0) {
							if (x.header == leftHeader)
								leftHeader = x.header.right
							x.header.cover
						}
						x = x.left
					}
					x = node.right
					while (x != null) {
						x.cover
						x = x.right
					}
				} else {
					currentHeader.first = node
				}
				node = node.down
			}

			if (currentHeader.length != 1)
				throw new Exception("Constraint did not get an unique solution")
			if (currentHeader == leftHeader)
				leftHeader = currentHeader.right
			currentHeader.cover
		}
		// Exact Cover of constraints has been done: build sudoku
		val sudoku = new Sudoku
		val board = new utilities.Board
		for (i <- 0 to 323) {
			val h = header(i)
			if (h.length == 1) {
				val row = ExactCover.getRow(h.first.info)
				val col = ExactCover.getCol(h.first.info)
				val num = ExactCover.getNum(h.first.info)
				board.setValue(row, col, num)
			}
		}
		sudoku.setBoard(board)
		return sudoku
	}
}