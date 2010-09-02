package sudokusolver.solvers

class BacktrackingSearchAndPartialLookAhead(sudoku : sudokusolver.Sudoku) extends GenericSolver {
	setProblem(sudoku)
	setPropagationAlgorithm(new PartialLookAhead)
	setSearchAlgorithm(new BacktrackingSearch)
}