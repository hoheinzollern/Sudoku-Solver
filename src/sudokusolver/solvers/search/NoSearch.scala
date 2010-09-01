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
		getProblem
	}
}
