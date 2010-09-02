package sudokusolver.solvers

class BacktrackingSearchAndMac(sudoku : sudokusolver.Sudoku) extends GenericSolver {
	setProblem(sudoku)
	setPropagationAlgorithm(new Mac)
	setSearchAlgorithm(new BacktrackingSearch)
}