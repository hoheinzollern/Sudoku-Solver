package sudokusolver.solvers.search

import scala.collection.immutable.Set

class MyBacktrackAndPropagate extends SearchAlgorithm {

	var board: Array[Array[Int]] = new Array[Array[Int]](9,9)
	var domainPropagator: (Int, Array[Array[Set[Int]]]) => Array[Array[Set[Int]]] = null
	
	def setDomainPropagator(propagator: (Int, Array[Array[Set[Int]]]) => Array[Array[Set[Int]]]) {
		domainPropagator = propagator
	}
	
	override def solve = {
		var domains = new Array[Array[Set[Int]]](9,9)
		for (i <- 0 until 9) {
			for (j <- 0 until 9) {
				val digit = getProblem.get(i,j)
				board(i)(j) = digit
				if (digit == 0)
					domains(i)(j) = Set(1,2,3,4,5,6,7,8,9)
				else
					domains(i)(j) = Set(digit)
			}
		}
		val success = recursiveBacktrack(0, domainPropagator(0, domains), domainPropagator)
		if (! success)
			throw new sudokusolver.exceptions.SolutionNotFoundException
		getProblem
	}
	
	def cloneMatrix(input: Array[Array[Set[Int]]]): Array[Array[Set[Int]]] = {
		var output = new Array[Array[Set[Int]]](9,9)
		for (i <- 0 until 9)
			for (j <- 0 until 9)
				output(i)(j) = input(i)(j)
		output
	}
	
	def recursiveBacktrack(_i: Int, _domains: Array[Array[Set[Int]]],
			domainPropagator: (Int, Array[Array[Set[Int]]]) => Array[Array[Set[Int]]]): Boolean = {
		var i = _i
		var domains = _domains
		nodeVisited
		while (board(i/9)(i%9) != 0 && i < 80)
			i += 1
		val row = i / 9
		val col = i % 9
		var success = false
		if (i == 80 && board(8)(8) != 0)
			success = true
		println(domains(col)(row).isEmpty)
		println (i)
		var elementDomain = domains(row)(col)
		while (!success && !elementDomain.isEmpty) {
			var element = elementDomain.head
			elementDomain = elementDomain.tail
			if (consistent(row, col, element)) {
				board(row)(col) = element
				getProblem.set(row, col, element, "set")
				if (i == 80)
					success = true
				else {
					try {
						var newdomains = domainPropagator(i+1, domains)
						success = recursiveBacktrack(i+1, newdomains, domainPropagator)
					}
				}
				if (!success) {
					board(row)(col) = 0
					sudokusolver.Core.log("backtrack")
				}
			}
		}
		success
	}
	
	def consistent(row: Int, col: Int, value: Int): Boolean = {
		for (i <- 0 until 9)
			if (row != i && value == board(i)(col))
				return false
		
		for (j <- 0 until 9)
			if (col != j && value == board(row)(j))
				return false
		
		val rowSquareStart = row / 3*3
		var colSquareStart = col / 3*3
		for (i <- rowSquareStart until rowSquareStart + 3)
			for (j <- colSquareStart until colSquareStart + 3)
				if ((row != i || col != j) && value == board(i)(j))
					return false
		true
	}
}