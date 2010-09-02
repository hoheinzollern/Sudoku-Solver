package sudokusolver.solvers

class PartialLookAheadOnly(sudoku : sudokusolver.Sudoku) extends GenericSolver {
	setProblem(sudoku)
	setPropagationAlgorithm(new propagation.PartialLookAhead)
	setSearchAlgorithm(new search.NoSearch)
}