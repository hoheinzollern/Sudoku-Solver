package sudokusolver.solvers

class BacktrackingSearchOnly(sudoku : sudokusolver.Sudoku) extends GenericSolver {
	setProblem(sudoku)
	setPropagationAlgorithm(new propagation.NoPropagation)
	setSearchAlgorithm(new search.MyBacktrack)
}