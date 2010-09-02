package sudokusolver.solvers

class PartialLookAheadOnly(sudoku : sudokusolver.Sudoku) extends GenericSolver {
	setProblem(sudoku)
	setPropagationAlgorithm(new PartialLookAhead)
	setSearchAlgorithm(new NoSearch)
}