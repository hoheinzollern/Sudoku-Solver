package sudokusolver.solvers

class DummySearch extends search.SearchAlgorithm {
	def solve() = {
		var sudoku = getProblem
		val board = sudoku.getBoard.getBoard
		var solution = sudokusolver.Dance.getSolution(board,
				(Unit) => { nodeVisited },
				(Unit) => { sudokusolver.Core.log("backtrack") })
		for (node <- solution) {
			var (row, col, digit) = sudokusolver.Dance.decodeRow(node.row)
			if (board(row)(col)==0)
				sudoku.set(row, col, digit, "No backtracking so set")
		}
		sudoku
	}
}

class DancingLinks(sudoku : sudokusolver.Sudoku) extends GenericSolver {
	setProblem(sudoku)
	setPropagationAlgorithm(new propagation.NoPropagation)
	setSearchAlgorithm(new DummySearch)
}