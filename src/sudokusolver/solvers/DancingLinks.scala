package sudokusolver.solvers

class DummySearch extends SearchAlgorithm {
	override def solve() = {
		//TODO Insert your code here
		//TODO and them return a sudokusolver.Sudoku
		getProblem
	}
}

class DancingLinks(sudoku : sudokusolver.Sudoku) extends GenericSolver {
	setProblem(sudoku)
	setPropagationAlgorithm(new NoPropagation)
	setSearchAlgorithm(new DummySearch)
}