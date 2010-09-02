package sudokusolver.solvers

class ForwardCheckingOnly(sudoku : sudokusolver.Sudoku) extends GenericSolver {
	println("ForwardCheckingOnly started!")
	setProblem(sudoku)
	setPropagationAlgorithm(new ForwardChecking)
	setSearchAlgorithm(new NoSearch)
}