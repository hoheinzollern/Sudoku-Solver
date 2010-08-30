package sudokusolver

import scala.collection.mutable.ArrayStack
class DSudoku (var b: Int) {

	class Node {
		var left: Node = null
		var right: Node = null
		var up: Node = null
		var down: Node = null
		var header: Header = null
		var row: Int = 0
	}
	
	class Header extends Node {
		var length: Int = 0
		var info: HeaderInfo = null
	}
	
	object HeaderInfo {
		val TYPE_ROW = 0
		val TYPE_COL = 1
		val TYPE_BLOCK = 2
		val TYPE_CELL = 3
	}
	class HeaderInfo {
		var type_ = -1
		var digit = -1
		var pos = -1
	}

	var root: Header = null
	var solution = new ArrayStack[Node]
	var countinue = true
	var solutionCounter = 0
	var n = b*b
	var threeN = 2*n
	var twoN = 3*n
	var nSquare = n*n
	var threeNsquare = 3*nSquare
	var fourNsquare = 4*nSquare
	var random = new java.util.Random
	var handleSolution: (Array[Array[Int]]) => Boolean = null
	var searchCount = 0
	var puzzle: Array[Array[Int]] = null
	
	def run(initialMatrix: Array[Array[Int]]) {
		solutionCounter = 0
		searchCount = 0
		var matrix = createMatrix(initialMatrix)
		createDoubleLinkedLists(matrix)
		search(0)
	}
	
	private def createMatrix(initialMatrix: Array[Array[Int]]): java.util.List[Array[Int]] = {
		var prefill = createPrefillArray(initialMatrix)
		
		var matrix = new java.util.ArrayList[Array[Int]]
		
		for (d <- 0 to n-1)
			for (r <- 0 to n-1)
				for (c <- 0 to n-1) {
					if (!cellIsFilled(d,r,c,prefill)) {
						var blockIndex = ((c / b) + ((r / b) * b))
						var colIndexRow = threeN*d+r
						var colIndexCol = threeN*d+n+c
						var colIndexBlock = threeN*d+twoN+blockIndex
						var colIndexSimple = threeNsquare+(c+n*r)
						matrix.add(Array[Int](colIndexRow,colIndexCol,colIndexBlock,colIndexSimple))
					}
				}
		matrix
	}
	
	private def cellIsFilled(digit: Int, row: Int, col: Int, prefill: Array[Array[Int]]): Boolean = {
		var isFilled = false
		if (prefill != null) {
			for (i <- 0 to prefill.length - 1) {
				var d = prefill(i)(0)-1
				var r = prefill(i)(1)
				var c = prefill(i)(2)
				// calculate the block indices
				var blockStartIndexCol = (c/b)*b
				var blockEndIndexCol = blockStartIndexCol + b
				var blockStartIndexRow = (r/b)*b
				var blockEndIndexRow = blockStartIndexRow + b
				if(d != digit && row == r && col == c) {
					isFilled = true
				} else if((d == digit) && (row == r || col == c) && !(row == r && col == c)) {
					isFilled = true
				} else if((d == digit) && (row > blockStartIndexRow) && (row < blockEndIndexRow) && (col > blockStartIndexCol) && (col < blockEndIndexCol) && !(row == r && col == c)) {
					isFilled = true
				}
			}
		}
		isFilled
	}
	
	private def createPrefillArray(initialMatrix: Array[Array[Int]]): Array[Array[Int]] = {
		var prefill: Array[Array[Int]] = null
		if (initialMatrix != null) {
			if (initialMatrix.length != n)
				throw new RuntimeException("wrong dimensions of initial matrix")
			var prefillList = new java.util.ArrayList[Array[Int]]
			for (r <- 0 to n-1)
				for (c <- 0 to n-1) {
					if (initialMatrix(r)(c) > 0) {
						prefillList.add(Array[Int](initialMatrix(r)(c), r, c))
					}
				}
			prefill = new Array[Array[Int]](prefillList.size)
			for (i <- 0 to prefill.length-1) {
				prefill(i) = prefillList.get(i)
			}
		}
		prefill
	}
	
	private def createDoubleLinkedLists(matrix: java.util.List[Array[Int]]): Header = {
		root = createLinkedHeaders
		var currentHeader: Header = null
		var it = matrix.iterator
		while (it.hasNext) {
			var record = it.next
			currentHeader = root.right.asInstanceOf[Header]
			var lastCreatedElement: Node = null
			var firstElement: Node = null
			for (col <- 0 to fourNsquare-1) {
				if (record(0)==col || record(1)==col || record(2)==col || record(3)==col) {
					// create a new data element and link it
					var colElement: Node = currentHeader
					while (colElement.down != null)
						colElement = colElement.down
					colElement.down = new Node
					if (firstElement == null)
						firstElement = colElement.down
					colElement.down.up = colElement
					colElement.down.left = lastCreatedElement
					colElement.down.header = currentHeader
					if (lastCreatedElement != null)
						colElement.down.left.right = colElement.down
					lastCreatedElement = colElement.down
					currentHeader.length += 1
				}
				currentHeader = currentHeader.right.asInstanceOf[Header]
			}
			// Link the first and the last element
			if (lastCreatedElement != null) {
				lastCreatedElement.right = firstElement
				firstElement.left = lastCreatedElement
			}
		}
		currentHeader = root.right.asInstanceOf[Header]
		
		// link the last column elements with the corresponding headers
		for (i <- 0 to fourNsquare-1) {
			var colElement = currentHeader.asInstanceOf[Node]
			while(colElement.down != null) 
				colElement = colElement.down
			colElement.down = currentHeader
			currentHeader.up = colElement
			currentHeader = currentHeader.right.asInstanceOf[Header]
		}
		return root
	}
	
	private def createLinkedHeaders: Header = {
		var headers = new Header
		var curHead = headers
		for (col <- 0 to fourNsquare-1) {
			var info = new HeaderInfo
			if (col < threeNsquare) {
				info.digit = (col/threeN) + 1
				var index = col-(info.digit-1)*threeN
				if (index < n)
					info.type_ = HeaderInfo.TYPE_ROW
				else if (index < twoN)
					info.type_ = HeaderInfo.TYPE_COL
				else
					info.type_ = HeaderInfo.TYPE_BLOCK
			} else {
				info.type_ = HeaderInfo.TYPE_CELL
				info.pos = col - threeNsquare
			}
			curHead.right = new Header
			curHead.right.left = curHead
			curHead = curHead.right.asInstanceOf[Header]
			curHead.info = info
			curHead.header = curHead
		}
		curHead.right = headers
		headers.left = curHead
		headers
	}
	
	private def coverColumn(column: Node) {
		column.right.left = column.left
		column.left.right = column.right
		var i = column.down
		while (i != column) {
			var j = i.right
			while (i != j) {
				j.down.up = j.up
				j.up.down = j.down
				j.header.length -= 1
				j = j.right
			}
			i = i.down
		}
	}
	
	private def uncoverColumn(column: Node) {
		var i = column.up
		while (i != column) {
			var j = i.left;
			while (i != j) {
				j.header.length += 1
				j.down.up = j
				j.up.down = j
				j = j.left
			}
			i = i.up
		}
		column.right.left = column
		column.left.right = column
	}
	
	private def search(k: Int) {
		if (root.right == root)
			return
		var c = chooseColumn
		coverColumn(c)
		var r = c.down
		while (r != c) {
			if (k < solution.size)
				solution.pop
			solution += r
			var j = r.right
			while (j != r) {
				coverColumn(j.header)
				j = j.right
			}
			if (countinue)
				search(k+1)
			
			var r2 = solution.top
			var j2 = r2.left
			while (j2 != r2) {
				uncoverColumn(j2.header)
				j2 = j2.left
			}
			r = r.down
			
			if (k == nSquare-1) {
				solutionCounter += 1
				countinue = handleSolution(createMatrixFromSolution(solution))
			}
		}
		uncoverColumn(c)
	}
	
	private def chooseColumn: Header = {
		var h:Header = root.right.asInstanceOf[Header]
		var smallest = h
		while (h.right != root) {
			h = h.right.asInstanceOf[Header]
			if (h.length < smallest.length)
				smallest = h
		}
		return smallest
	}
	
	def randomSudoku(randomMatrix: Array[Array[Int]]): Array[Array[Int]] = {
		for (i <- 0 to randomMatrix.length - 1) {
			for (j <- 0 to randomMatrix(i).length - 1)
				print(randomMatrix(i)(j))
			println
		}
		var sudoku = new Array[Array[Int]](n,n)
		var nonZeros = 0
		
		var trialTracker = new Array[Short](nSquare)
		for (row <- 0 to n-1) {
			for (col <- 0 to n-1) {
				sudoku(row)(col) = randomMatrix(row)(col)
				if (sudoku(row)(col) > 0) {
					nonZeros += 1
				} else {
					trialTracker(n * row + col) = 1
				}
			}
		}
		
		var triedFields = 0
		for (i <- 0 to trialTracker.length - 1) {
			if (trialTracker(i) == 1) {
				triedFields += 1
			}
		}
		
		var solver = new DSudoku(b)
		var continue = true
		println(nSquare)
			println(triedFields)
		while (triedFields < nSquare && continue) {
			var field = random.nextInt(nSquare)
			
			while (trialTracker(field) > 0)
				field = (field + 1) % nSquare
			
			var r = field / n
			var c = field % n
			var d = sudoku(r)(c)
			trialTracker(field) = 1
			triedFields += 1
			sudoku(r)(c) = 0
			nonZeros -= 1
			
			var sudokuCopy = copyMatrix(sudoku)

			if (handleSolution != null)
				continue = handleSolution(sudokuCopy)
			solver.run(sudokuCopy)
			if (handleSolution != null)
				continue = handleSolution(sudokuCopy)

			if (solver.solutionCounter > 1) {
				sudoku(r)(c) = d
				nonZeros += 1
			}
		}
		return sudoku
	}
	
	def fullCoverageMatrix: Array[Array[Int]] = {
		var matrix = new Array[Array[Int]](n, n)
		var solver = new DSudoku(b)
		handleSolution = (solMatrix: Array[Array[Int]]) => {
			for (row <- 0 to n-1)
				for (col <- 0 to n-1)
					matrix(row)(col) = solMatrix(row)(col)
			false
		}
		var randomMatrix = new Array[Array[Int]](n,n)
		var fields = new Array[Int](nSquare)
		for (d <- 1 to n-1) {
			var field = random.nextInt(nSquare)
			while (fields(field) > 0)
				field = (field + 1) % nSquare
			randomMatrix(field / n)(field % n) = d
			fields(field) = d
		}
		solver.run(randomMatrix)
		return matrix
	}
	
	def createMatrixFromSolution(solution: ArrayStack[Node]): Array[Array[Int]] = {
		var result = new Array[Array[Int]](n, n)
		for (element <- solution) {
			var digit = -1
			var cell = -1
			var next = element
			do {
				if (next.header.info.type_ == HeaderInfo.TYPE_ROW)
					digit = next.header.info.digit
				else if (next.header.info.type_ == HeaderInfo.TYPE_CELL)
					cell = next.header.info.pos
				next = next.right
			} while (element != next)
			var row = cell / n
			var col = cell % n
			result(row)(col) = digit
		}
		return result
	}
	
	def copyMatrix(original: Array[Array[Int]]): Array[Array[Int]] = {
		var length = original.length
		var copy = new Array[Array[Int]](length, length)
		for (row <- 0 to length-1) {
			for (col <- 0 to original(row).length - 1)
				copy(col)(row) = original(col)(row)
		}
		return copy
	}
}
