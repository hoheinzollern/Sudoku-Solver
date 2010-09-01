package sudokusolver

import scala.collection.immutable.Stack
object Dance {
	
	class Node (head: Header, u: Node, d: Node, l: Node, r: Node) {
		def this(head: Header) = this(head, null, null, null, null)
		def this(head: Header, row: Int) = {
			this(head, null, null, null, null)
			head.append(this)
			this.row = row
		}
		
		var up: Node = u
		var down: Node = d
		var left: Node = l
		var right: Node = r
		var header: Header = head
		var row: Int = 0
	}
	
	class Header (l: Node, r: Node, i: Info) extends Node(null, null, null, l, r) {
		def this() = this (null, null, null)
		
		header = this
		up = this
		down = this
		
		def append(node: Node) {
			node.up = this.up
			this.up.down = node
			node.down = this
			this.up = node
			if (size == 0)
				this.down = node
			size += 1
		}

		var info: Info = i
		var size = 0
	}
	
	class Info(var row: Int, var col: Int, var digit: Int) {}
	
	val TYPE_ROW = 0
	val TYPE_COL = 1
	val TYPE_BLOCK = 2
	val TYPE_CELL = 3
	
	val encodeConstraint = Array[(Int, Int, Int) => Int](
		(row: Int, col: Int, digit: Int) => row * 9 + (digit-1),
		(row: Int, col: Int, digit: Int) => col * 9 + (digit-1),
		(row: Int, col: Int, digit: Int) => (row / 3 * 3 + col / 3) * 9 + (digit-1),
		(row: Int, col: Int, digit: Int) => row * 9 + col
	)
	
	val decodeConstraint = Array[(Int) => (Int, Int, Int)](
		(code: Int) => (code / 9, 0, code % 9 + 1),
		(code: Int) => (0, code / 9, code % 9 + 1),
		// Note that this decodes the block, not the actual row and col:
		(code: Int) => (code / 9 / 3, code / 9 % 3, code % 9 + 1),
		(code: Int) => (code / 9, code % 9, 0)
	)
	
	def decodeRow(row: Int): (Int, Int, Int) =
		(row / 9 / 9, row / 9 % 9, row % 9 + 1)
	def encodeRow(row: Int, col: Int, digit: Int): Int = (row * 9 * 9) + col * 9 + digit-1
	
	def makeHeaders: Header = {
		val root = new Header()
		var curHead: Header = root
		for (i <- 0 until 81 * 4) {
			var (row, col, digit) = 
				if (i < 81) decodeConstraint(TYPE_ROW)(i)
				else if (i < 81 * 2) decodeConstraint(TYPE_COL)(i-81)
				else if (i < 81 * 3) decodeConstraint(TYPE_BLOCK)(i-81*2)
				else decodeConstraint(TYPE_CELL)(i-81*3)
			var info = new Info(row, col, digit)
			curHead.right = new Header(curHead, root, info)
			curHead.right.left = curHead
			curHead = curHead.right.asInstanceOf[Header]
		}
		root.left = curHead
		root
	}
	
	private def addConstraint(headers: Array[Header], row: Int, col: Int, digit: Int) {
		val header1 = headers(encodeConstraint(TYPE_ROW)(row, col, digit))
		val header2 = headers(encodeConstraint(TYPE_COL)(row, col, digit) + 81)
		val header3 = headers(encodeConstraint(TYPE_BLOCK)(row, col, digit) + 81 * 2)
		val header4 = headers(encodeConstraint(TYPE_CELL)(row, col, digit) + 81 * 3)
		val r = encodeRow(row, col, digit)
		val node1 = new Node(header1, r)
		val node2 = new Node(header2, r)
		val node3 = new Node(header3, r)
		val node4 = new Node(header4, r)
		node2.left = node1
		node1.right = node2
		
		node3.left = node2
		node2.right = node3
		
		node4.left = node3
		node3.right = node4
		
		node1.left = node4
		node4.right = node1
	}
	
	def buildMatrix(root: Header, board: Array[Array[Int]]) {
		var headers = new Array[Header](81*4)
		var curHead = root.right.asInstanceOf[Header]
		for (i <- 0 until 81*4) {
			headers(i) = curHead
			curHead = curHead.right.asInstanceOf[Header]
		}
		for (i <- 0 until 81) {
			val row = i / 9
			val col = i % 9
			if (board(row)(col) == 0) {
				for (j <- 1 to 9) {
					addConstraint(headers, row, col, j)
				}
			} else {
				addConstraint(headers, row, col, board(row)(col))
			}
		}
	}
	
	
	def coverColumn(column: Header, root: Header) {
		column.right.left = column.left
		column.left.right = column.right
		var i = column.down
		while (i != column) {
			var j = i.right
			while (i != j) {
				j.down.up = j.up
				j.up.down = j.down
				j.header.size -= 1
				j = j.right
			}
			i = i.down
		}
	}
	
	def uncoverColumn(column: Header) {
		var i = column.up
		while (i != column) {
			var j = i.left;
			while (i != j) {
				j.header.size += 1
				j.down.up = j
				j.up.down = j
				j = j.left
			}
			i = i.up
		}
		column.right.left = column
		column.left.right = column
	}
	
	def chooseColumn(root: Header): Header = {
		var current: Header = root.right.asInstanceOf[Header]
		var next: Header = current.right.asInstanceOf[Header]
		while (next != root) {
			if (next.size < current.size)
				current = next
			next = next.right.asInstanceOf[Header]
		}
		current
	}
	
	var solutionCount = 0
	
	def search(root: Header, solution: Stack[Node], k: Int, tryAll: Boolean): Stack[Node] = {
		if (root.right == root) {
			solutionCount += 1
			return solution
		}
		val c = chooseColumn(root)
		if (c.size == 0)
			return null
		coverColumn(c, root)
		var r = c.down
		var count = 0
		while (r != c) {
			if (count > 0 ) println (k)
			var j = r.right
			while (j != r) {
				coverColumn(j.header, root)
				j = j.right
			}
			
			var sol = search(root, solution :+ r, k+1, tryAll)
			if (sol != null && !tryAll)
				return sol
			
			j = r.right
			while (j != r) {
				uncoverColumn(j.header)
				j = j.right
			}
			r = r.down
			count += 1
		}
		uncoverColumn(c)
		null
	}
	
	def solve(board: Array[Array[Int]]): Array[Array[Int]] = {
		var root = makeHeaders
		buildMatrix(root, board)
		var solution = search(root, new Stack[Node](), 0, false);
		solutionCount = 0
		var solvedBoard = new Array[Array[Int]](9,9)
		while (!solution.isEmpty) {
			var r = solution.top
			solution = solution.pop
			var (row, col, digit) = decodeRow(r.row)
			solvedBoard(row)(col) = digit
		}
		solvedBoard
	}
	
	def fullCoverageMatrix: Array[Array[Int]] = {
		val random = new java.util.Random
		var randomMatrix = new Array[Array[Int]](9,9)
		var fields = new Array[Int](81)
		for (d <- 1 to 9) {
			var field = random.nextInt(81)
			while (fields(field) > 0)
				field = (field + 1) % 81
			randomMatrix(field / 9)(field % 9) = d
			fields(field) = d
		}
		solve(randomMatrix)
	}
	
	def randomSudoku = {
		var matrix = fullCoverageMatrix
		var random = new java.util.Random
		var trials = new Array[Int](81)
		var triedFields = 0
		var continue = true
		println ("here")
		println (solutionCount)
		while (triedFields < 81 && continue) {
			var field = random.nextInt(81)
			while (trials(field) > 0)
				field = (field + 1) % 81
			var r = field / 9
			var c = field % 9
			var d = matrix(r)(c)
			trials(field) = 1
			triedFields += 1
			matrix(r)(c) = 0
			var root = makeHeaders
			buildMatrix(root, matrix)
			search(root, new Stack[Node](), 0, true)
			println(solutionCount)
			if (solutionCount > 1) {
				matrix(r)(c) = d
				continue = false
			}
			solutionCount = 0
		}
		matrix
	}
}
