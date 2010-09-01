package sudokusolver.solvers

class DummySearch extends SearchAlgorithm {
	def solve() = {
		//TODO Insert your code here
		//TODO and them return a sudokusolver.Sudoku
		getProblem
	}
}

class DummyPropagation extends PropagationAlgorithm {
	def prop(item : sudokusolver.utilities.Couple) : Boolean = false
}

class DancingLinks(sudoku : sudokusolver.Sudoku) extends GenericSolver {
	setProblem(sudoku)
	setPropagationAlgorithm(new DummyPropagation)
	setSearchAlgorithm(new DummySearch)
}