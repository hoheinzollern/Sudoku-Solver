package sudokusolver.solvers

class ForwardCheckingOnly(sudoku : sudokusolver.Sudoku) extends GenericSolver {
	setProblem(sudoku)
	setPropagationAlgorithm(new ForwardChecking)
	setSearchAlgorithm(new NoSearch)
}