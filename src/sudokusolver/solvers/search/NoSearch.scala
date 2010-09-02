package sudokusolver.solvers.search

class NoSearch extends SearchAlgorithm {
	override def solve() = {
		var item = new sudokusolver.utilities.Couple(0,0)
		var failure = false
		var stop = false
		while(!stop && !failure) {
			failure = prop(item)
			if (!item.isLatest) item = item.next
			else stop = true
		}
		if (failure) throw new sudokusolver.exceptions.SolutionNotFoundException
		var sudoku = getProblem
    	//sudoku.printDomainStatus
    	item = new sudokusolver.utilities.Couple(0,0)
		stop = false
    	while(!stop) {
    		if (!sudoku.getBoard.isNotNull(item) && sudoku.getDomain(item).cardinality == 1) {
    			sudoku.set(item, sudoku.getDomain(item).getValue(0), "Domain has only 1 element, setting it into board.")
    		}
    		if (!item.isLatest) item = item.next
    		else stop = true
    	}
		getProblem
	}
}
