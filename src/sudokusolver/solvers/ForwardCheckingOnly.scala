package sudokusolver.solvers

class ForwardCheckingOnly(sudoku : sudokusolver.Sudoku) extends GenericSolver {
	setProblem(sudoku)
	setPropagationAlgorithm(new propagation.ForwardChecking)
	setSearchAlgorithm(new search.NoSearch)
}