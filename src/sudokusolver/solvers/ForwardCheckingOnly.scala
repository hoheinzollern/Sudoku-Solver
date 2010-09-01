package sudokusolver.solvers

class ForwardCheckingOnly(sudoku : sudokusolver.Sudoku) extends GenericSolver {
	println("Inizio ForwardCheckingOnly con domini...")
	//sudoku.printBoardStatus
	//sudoku.printDomainStatus
	setProblem(sudoku)
	setPropagationAlgorithm(new ForwardChecking)
	setSearchAlgorithm(new NoSearch)
}