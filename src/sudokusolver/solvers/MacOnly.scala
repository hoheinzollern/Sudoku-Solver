package sudokusolver.solvers

class MacOnly(sudoku : sudokusolver.Sudoku) extends GenericSolver {
	setProblem(sudoku)
	setPropagationAlgorithm(new propagation.Mac)
	setSearchAlgorithm(new search.NoSearch)
}