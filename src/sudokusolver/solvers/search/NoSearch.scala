package sudokusolver.solvers

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
		println("Calcolo la nuova board!")
    	println("I domini sono :")
    	var sudoku = getProblem
    	sudoku.printDomainStatus
    	item = new sudokusolver.utilities.Couple(0,0)
		stop = false
    	while(!stop) {
    		if (!sudoku.getBoard.isNotNull(item) && sudoku.getDomain(item).cardinality == 1) {
    			println()
    			sudoku.set(item, sudoku.getDomain(item).getValue(0), "Dominio con 1 solo elemento! YEAH!")
    			println("Impostato " + sudoku.getDomain(item).getValue(0) + " in " + item.printCouple)
    		}
    		if (!item.isLatest) item = item.next
    		else stop = true
    	}
		getProblem
	}
}
