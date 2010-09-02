package sudokusolver.solvers

class MacOnly(sudoku : sudokusolver.Sudoku) extends GenericSolver {
	setProblem(sudoku)
	setPropagationAlgorithm(new Mac)
	setSearchAlgorithm(new NoSearch)
}