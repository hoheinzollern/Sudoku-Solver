package sudokusolver.solvers

class BacktrackingSearchAndForwardChecking(sudoku : sudokusolver.Sudoku) extends GenericSolver {
	setProblem(sudoku)
	var propagationAlgorithm = new propagation.ForwardChecking 
	setPropagationAlgorithm(propagationAlgorithm)
	var searchAlgorithm = new search.MyBacktrackAndPropagate
	var domainPropagator = new propagation.DomainPropagators
	domainPropagator.setPropagationAlgorithm(propagationAlgorithm)
	searchAlgorithm.setDomainPropagator(domainPropagator.forwardCheckingDomainPropagator)
	setSearchAlgorithm(searchAlgorithm)
}