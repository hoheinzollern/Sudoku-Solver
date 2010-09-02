package sudokusolver.solvers

class BacktrackingSearchOnly(sudoku : sudokusolver.Sudoku) extends GenericSolver {
	println("BacktrackingSearchOnly algorithm started!")
	setProblem(sudoku)
	setPropagationAlgorithm(new NoPropagation)
	setSearchAlgorithm(new BacktrackingSearch)
}