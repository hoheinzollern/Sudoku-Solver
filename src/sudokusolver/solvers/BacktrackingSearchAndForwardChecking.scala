package sudokusolver.solvers

class BacktrackingSearchAndForwardChecking(sudoku : sudokusolver.Sudoku) extends GenericSolver {
	setProblem(sudoku)
	setPropagationAlgorithm(new ForwardChecking)
	setSearchAlgorithm(new BacktrackingSearch)
}