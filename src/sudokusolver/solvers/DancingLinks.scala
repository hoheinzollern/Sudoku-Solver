package sudokusolver.solvers

class DummySearch extends SearchAlgorithm {
	def solve() = {
		var sudoku = getProblem
		println("using problem " + sudoku)
		val board = sudoku.getBoard.getBoard
		var solution = sudokusolver.Dance.getSolution(board, (Unit) => { nodeVisited })
		for (node <- solution) {
			var (row, col, digit) = sudokusolver.Dance.decodeRow(node.row)
			if (board(row)(col)==0)
				sudoku.set(row, col, digit, "Yehaa")
		}
		sudoku
	}
}

class DancingLinks(sudoku : sudokusolver.Sudoku) extends GenericSolver {
	setProblem(sudoku)
	setPropagationAlgorithm(new NoPropagation)
	setSearchAlgorithm(new DummySearch)
}